package fr.velocity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<Instant> {

    private static final long serialVersionUID = -667426971210725656L;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (instant != null) {
            jsonGenerator.writeString(formatter.format(instant));
        }
    }
}
