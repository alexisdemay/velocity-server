package fr.velocity.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.velocity.exception.DocumentException;
import lombok.Data;

@Data
public abstract class AbstractDocument {

    private String id;

    public String toJson() {
        try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new DocumentException("An error has occurred during the parsing of " + this.toString());
        }
    }

}
