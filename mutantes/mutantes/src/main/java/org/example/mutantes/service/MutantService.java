package org.example.mutantes.service;

import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.exception.DnaHashCalculationException;
import org.example.mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MutantService {
    private final MutantDetector detector;
    private final DnaRecordRepository repository;

    public boolean analyzeDna(String[] dna) {

        String dnaHash = calcularHashDna(dna);

        // Buscar en BD por hash
        var existe = repository.findByDnaHash(dnaHash);
        if (existe.isPresent()) {
            return existe.get().isMutant();
        }

        // Analizar y guardar
        boolean esMutante = detector.isMutant(dna);
        DnaRecord registro = new DnaRecord(dnaHash, esMutante);
        repository.save(registro);

        return esMutante;
    }

    private String calcularHashDna(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error calculando hash del DNA", e);
        }
    }
}