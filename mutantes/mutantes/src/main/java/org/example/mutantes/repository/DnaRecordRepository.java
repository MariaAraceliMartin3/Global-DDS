package org.example.mutantes.repository;

import org.example.mutantes.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {
    Optional<DnaRecord> findByDnaHash(String dnaHash); // Buscar por hash
    long countByIsMutant(boolean isMutant); // Contar mutantes/humanos
}