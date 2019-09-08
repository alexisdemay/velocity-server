package fr.velocity.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.velocity.annotation.Document;
import fr.velocity.exception.DocumentException;
import java.util.List;
import lombok.Data;

@Document(
        indexName = Contract.INDEX_NAME,
        alias = Contract.ALIAS,
        aliasRead = Contract.ALIAS_READ,
        aliasWrite = Contract.ALIAS_WRITE,
        type = Contract.TYPE
)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contract extends AbstractDocument {

    public final static String INDEX_NAME = "velocity-contracts";

    public final static String ALIAS = "velocity-contracts-alias";

    public final static String ALIAS_READ = "velocity-contracts-alias-read";

    public final static String ALIAS_WRITE = "velocity-contracts-alias-write";

    public final static String TYPE = "_doc";

    @JsonProperty("name")
    private String name;

    @JsonProperty("commercial_name")
    private String commercialName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("cities")
    private List<String> cities;

}
