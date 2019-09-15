package fr.velocity.repository;

import fr.velocity.document.AbstractDocument;
import fr.velocity.helper.DocumentHelper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticApiManager {

    public static final Integer INDEX_REQUEST_TIMEOUT_IN_MIN = 1;

    public static final Integer BULK_REQUEST_TIMEOUT_IN_MIN = 1;

    public static final ByteSizeValue BULK_SIZE = new ByteSizeValue(1L, ByteSizeUnit.MB);

    public static final Integer BULK_ACTIONS = 250;

    @Autowired
    private RestHighLevelClient client;

    public <T extends AbstractDocument> void indexAsync(T document) {
        IndexRequest indexRequest = createIndexRequest(document);
        client.indexAsync(indexRequest, RequestOptions.DEFAULT,
                new ActionListener<IndexResponse>() {
                    @Override
                    public void onResponse(IndexResponse indexResponse) {
                        String index = indexResponse.getIndex();
                        String id = indexResponse.getId();
                        if (indexResponse.getResult() == Result.CREATED) {
                            log.debug("The document {} has been successfuly created in {} indice",
                                    id, index);
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {
                        log.error("An error has occurred during the indexing:", e);
                    }
                });
    }

    public <T extends AbstractDocument> void bulkAsync(List<T> listOfDocuments) {
        if (CollectionUtils.isNotEmpty(listOfDocuments)) {
            BulkProcessor.Listener listener = getBulkProcessorListener();
            BulkProcessor bulkProcessor = BulkProcessor
                    .builder((request, bulkListener) -> {
                        request.timeout(TimeValue.timeValueMinutes(BULK_REQUEST_TIMEOUT_IN_MIN));
                        request.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
                    }, listener)
                    .setBulkActions(BULK_ACTIONS) // Number of actions before flush a new bulk request
                    .setBulkSize(BULK_SIZE) // Size of actions before flush a new bulk request
                    .setConcurrentRequests(1) // Allow more than one concurrent request to be exectued
                    .setFlushInterval(TimeValue.timeValueSeconds(1))
                    .build();
            listOfDocuments.stream()
                    .forEach(document -> bulkProcessor.add(createIndexRequest(document)));
        }
    }

    private Listener getBulkProcessorListener() {
        return new Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk executionId={} with {} requests",
                        executionId, numberOfActions);
            }
            @Override
            public void afterBulk(long executionId, BulkRequest request,
                    BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk executionId={} executed with failures", executionId);
                    for (BulkItemResponse item : response.getItems()) {
                        if (item.getFailureMessage() != null) {
                            log.error("An error has occurred during indexing item id={}: {}", item.getId(), item.getFailureMessage());
                        }
                    }
                } else {
                    log.debug("Bulk executionId={} completed in {} milliseconds",
                            executionId, response.getTook().getMillis());
                }
            }
            @Override
            public void afterBulk(long executionId, BulkRequest request,
                    Throwable failure) {
                log.error("Failed to execute bulk executionId={}", executionId, failure);
            }
        };
    }

    private <T extends AbstractDocument> IndexRequest createIndexRequest(T document) {

        IndexRequest indexRequest = new IndexRequest();

        indexRequest.id(UUID.randomUUID().toString());
        indexRequest.index(DocumentHelper.getAliasWrite(document));
        indexRequest.source(document.toJson(), XContentType.JSON);
        indexRequest.timeout(TimeValue.timeValueMinutes(ElasticApiManager.INDEX_REQUEST_TIMEOUT_IN_MIN));
        indexRequest.versionType(VersionType.INTERNAL);
        indexRequest.opType(OpType.INDEX);

        return indexRequest;

    }

}
