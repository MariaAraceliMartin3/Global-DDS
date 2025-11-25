package org.example.mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response con estadísticas de ADN analizados")

public class StatsResponse {
    @Schema(description = "Cantidad de ADN mutante", example = "40")
    private long count_mutant_dna;

    @Schema(description = "Cantidad de ADN humano", example = "100")
    private long count_human_dna;

    @Schema(description = "Ratio de mutantes vs humanos", example = "0.4")
    private double ratio;
}