package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("OPEN")
    OPEN,
    @JsonProperty("CLOSED")
    CLOSED;
}
