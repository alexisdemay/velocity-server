package fr.velocity.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.velocity.annotation.Document;
import fr.velocity.deserializer.TimestampDeserialization;
import fr.velocity.exception.DocumentException;
import fr.velocity.serializer.LocalDateTimeSerializer;
import java.time.Instant;
import lombok.Data;

@Document(
        indexName = Statistics.INDEX_NAME,
        alias = Statistics.ALIAS,
        aliasRead = Statistics.ALIAS_READ,
        aliasWrite = Statistics.ALIAS_WRITE,
        type = Statistics.TYPE
)
@Data
@JsonInclude(Include.NON_NULL)
public class Statistics extends AbstractDocument {

    public final static String INDEX_NAME = "velocity-statistics";

    public final static String ALIAS = "velocity-statistics-alias";

    public final static String ALIAS_READ = "velocity-statistics-alias-read";

    public final static String ALIAS_WRITE = "velocity-statistics-alias-write";

    public final static String TYPE = "_doc";

    @JsonProperty("contract")
    private Contract contract;

    @JsonProperty("total_stations")
    private Integer totalStations;

    @JsonProperty("total_open_stations")
    private Integer totalOpenStations;

    @JsonProperty("total_closed_stations")
    private Integer totalClosedStations;

    @JsonProperty("total_bikes_capacity")
    private Integer totalBikesCapacity;

    @JsonProperty("total_bikes_available")
    private Integer totalBikesAvailable;

    @JsonProperty("total_bikes_used")
    private Integer totalBikesUsed;

    @JsonProperty("total_stands_available")
    private Integer totalStandsAvailable;

    @JsonProperty("last_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserialization.class)
    private Instant lastUpdate;

}
