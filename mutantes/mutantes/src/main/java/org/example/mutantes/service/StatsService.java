package org.example.mutantes.service;

import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long mutantes = dnaRecordRepository.countByIsMutant(true);
        long humanos = dnaRecordRepository.countByIsMutant(false);
        double ratio = calcularRatio(mutantes, humanos);
        return new StatsResponse(mutantes, humanos, ratio);
    }

    //Lógica para calcular ratio
    private double calcularRatio(long mutantes, long humanos) {
        if (humanos == 0) {
            return mutantes > 0 ? mutantes : 0.0;
        }
        return (double) mutantes / humanos;
    }
}