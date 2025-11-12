package com.luzinho.sbuar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class PlaneFinderPoller {

    @NonNull
    private final AircraftRepository aircraftRepository;
    private final WebClient webClient = WebClient.create("http://localhost:7634/planes");

    @Scheduled(fixedRate = 5000)
    public void pollingPlanes(){
        // aircraftRepository.deleteAll();

        webClient.get()
                .retrieve()
                .bodyToFlux(AircraftPolled.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraft -> {
                  aircraft.setId(null);
                  aircraftRepository.save(aircraft);
                });

        aircraftRepository.findAll().forEach(System.out::println);
    }
}
