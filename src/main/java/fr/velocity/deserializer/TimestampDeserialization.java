package fr.velocity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TimestampDeserialization extends StdDeserializer<LocalDateTime> {

    private static final long serialVersionUID = -8470889780234660047L;

    public TimestampDeserialization() {
        this(null);
    }

    public TimestampDeserialization(Class t) {
        super(t);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(jsonParser.getText())),
                TimeZone.getDefault().toZoneId());
    }

}
