package com.luzinho.sbuar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
public class AircraftController {

    @NonNull
    private final AircraftRepository aircraftRepository;
    private final WebClient webClient = WebClient.create("http://localhost:7634/planes");

    @GetMapping("aircraft")
    public String getAircraftPositions(Model model){
        this.aircraftRepository.deleteAll();

        this.webClient.get()
                .retrieve()
                .bodyToFlux(AircraftModel.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(this.aircraftRepository::save);

        model.addAttribute("currentPositions", this.aircraftRepository.findAll());
        return "positions";
    }
}
