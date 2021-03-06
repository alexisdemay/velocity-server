package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.velocity.deserializer.TimestampDeserialization;
import fr.velocity.serializer.LocalDateTimeSerializer;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Station {

    /**
     * Le numéro de la station qui n'est unique qu'au sein d'un contrat.
     */
    @JsonProperty("number")
    private Integer number;

    /**
     * Le nom du contrat de cette station
     */
    @JsonProperty("contractName")
    private String contractName;

    /**
     * Le nom de la station
     */
    @JsonProperty("name")
    private String name;

    /**
     * Adresse indicative de la station.
     */
    @JsonProperty("address")
    private String address;

    /**
     * Les coordonnées au format WGS84.
     */
    @JsonProperty("position")
    private Position position;

    /**
     * Indique la présence d'un terminal de paiement
     */
    @JsonProperty("banking")
    private Boolean banking;

    /**
     * Indique s'il s'agit d'une station bonus.
     */
    @JsonProperty("bonus")
    private Boolean bonus;

    /**
     * Indique l'état de la station, peut être CLOSED ou OPEN.
     */
    @JsonProperty("status")
    private Status status;

    /**
     * Indique le moment de la dernière mise à jour.
     */
    @JsonProperty("lastUpdate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserialization.class)
    private Instant lastUpdate;

    /**
     * Indique si la station est connectée au système central.
     */
    @JsonProperty("connected")
    private Boolean connected;

    /**
     * Indique si la station accepte le repose de vélos en overflow.
     */
    @JsonProperty("overflow")
    private Boolean overflow;

    /**
     * Indique la capacité totale d'accueil de vélos, le nombre d'emplacements libres et le nombre
     * total de vélos présents
     */
    @JsonProperty("totalStands")
    private Stands totalStands;

    /**
     * Indique la capacité d'accueil de vélos en accroche physique, le nombre de points d'attache
     * libres et le nombre total de vélos accrochés.
     */
    @JsonProperty("mainStands")
    private Stands mainStands;

    /**
     * Indique la capacité d'accueil de vélos en overflow, le nombre d'emplacements overflow libres
     * et le nombre de vélos présents en overflow.
     */
    @JsonProperty("overflowStands")
    private Stands overflowStands;



}
