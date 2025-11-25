package org.example.mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.example.mutantes.validation.ValidDnaSequence;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Request para verificar ADN")

public class DnaRequest {
    @Schema(
            description = "Secuencia de ADN representada como matriz NxN",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true
    )

    @NotNull(message = "DNA no puede ser null")
    @NotEmpty(message = "DNA no puede ser vacío")
    @Size(min=4)
    @ValidDnaSequence
    private String[] dna;
}