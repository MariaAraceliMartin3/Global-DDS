package org.example.mutantes.controller;

import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.service.MutantService;
import org.example.mutantes.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequiredArgsConstructor
@Tag(name = "Mutant Controller", description = "Endpoints para detección de mutantes")
public class MutantController {

        private final MutantService mutantService;
        private final StatsService statsService;

        @PostMapping("/mutant")
        @Operation(
                summary = "Verificar si es mutante",
                description = "Analiza una secuencia de ADN para determinar si es mutante"
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Es mutante"),
                @ApiResponse(responseCode = "403", description = "No es mutante"),
                @ApiResponse(responseCode = "400", description = "ADN inválido")
        })
        public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
                boolean esMutante = mutantService.analyzeDna(request.getDna());

                if (esMutante) {
                        return ResponseEntity.ok().build(); // 200 OK - Es mutante
                } else {
                        return ResponseEntity.status(403).build(); // 403 Forbidden - No es mutante
                }
        }

        @GetMapping("/stats")
        @Operation(
                summary = "Obtener estadísticas",
                description = "Retorna estadísticas de verificaciones"
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
        })
        public ResponseEntity<StatsResponse> getStats() {
                StatsResponse estadisticas = statsService.getStats();
                return ResponseEntity.ok(estadisticas);
        }
}