package fr.velocity.client;

import fr.velocity.config.AppProperties;
import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class JCDecauxClient {

    private static final String PARAM_API_KEY = "apiKey={apiKey}";
    private static final String URI_LIST_CONTRACTS = "/contracts?" + PARAM_API_KEY;
    private static final String URI_LIST_STATIONS = "/stations?" + PARAM_API_KEY;
    private static final String URI_LIST_STATIONS_BY_CONTRACT = URI_LIST_STATIONS + "&contract={contractName}";

    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public JCDecauxClient(AppProperties appProperties) {
        this.webClient = WebClient
                .builder()
                .baseUrl(appProperties.getJcdecaux().getBaseUrl())
                .filter(logRequest())
                .build();
        this.apiKey = appProperties.getJcdecaux().getApiKey();
    }

    public Flux<Contract> listContracts() {
        return webClient.get()
                .uri(URI_LIST_CONTRACTS, apiKey)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Contract.class));
    }

    public Flux<Station> listStations() {
        return webClient.get()
                .uri(URI_LIST_STATIONS, apiKey)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Station.class));
    }

    public Flux<Station> listStationsByContract(String contractName) {
        return webClient.get()
                .uri(URI_LIST_STATIONS_BY_CONTRACT, apiKey, contractName)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Station.class));
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }

}
