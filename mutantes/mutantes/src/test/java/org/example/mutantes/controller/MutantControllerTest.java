package org.example.mutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.service.MutantService;
import org.example.mutantes.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Simula requests HTTP

    @Autowired
    private ObjectMapper objectMapper;  // Convierte objetos a JSON

    @MockBean  // Mock en contexto de Spring
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    private String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private String[] humanDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    //Test 1: POST /mutant - Retorna 200 para Mutante
    @Test
    @DisplayName("POST /mutant debe retornar 200 OK para ADN mutante")
    void testCheckMutantReturns200ForMutant() throws Exception {
        System.out.println("========== Test 1 ==========");
        System.out.println("POST /mutant debe retornar 200 OK para mutante");

        // ARRANGE
        String[] mutantDna = {
                "ATGCGA", "CAGTGC", "TTATGT",
                "AGAAGG", "CCCCTA", "TCACTG"
        };
        DnaRequest request = new DnaRequest(mutantDna);

        when(mutantService.analyzeDna(any(String[].class)))
                .thenReturn(true);  // Mock: es mutante

        // ACT & ASSERT
        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());  // 200 OK
        System.out.println("Mutante detectado exitosamente");
    }

    //Test 2: POST /mutant - Retorna 403 para Humano
    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden para ADN humano")
    void testCheckMutantReturns403ForHuman() throws Exception {
        System.out.println("========== Test 2 ==========");
        System.out.println("POST /mutant debe retornar 403 Forbidden para humano");

        String[] humanDna = {
                "ATGCGA", "CAGTGC", "TTATTT",
                "AGACGG", "GCGTCA", "TCACTG"
        };
        DnaRequest request = new DnaRequest(humanDna);

        when(mutantService.analyzeDna(any(String[].class)))
                .thenReturn(false);  // Mock: es humano

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isForbidden());  // 403 Forbidden
        System.out.println("Humano detectado exitosamente");
    }

    //Test 3: POST /mutant - Retorna 400 para DNA Nulo
    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request para ADN nulo")
    void testCheckMutantReturns400ForNullDna() throws Exception {
        System.out.println("========== Test 3 ==========");
        System.out.println("POST /mutant debe retornar 400 Bad Request para ADN nulo");

        DnaRequest request = new DnaRequest(null);  // DNA nulo

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());  // 400 Bad Request
        System.out.println("ADN nulo detectado exitosamente");
    }

    //Test 4: POST /mutant - Retorna 400 para DNA Vacío
    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request para ADN vacío")
    void testCheckMutantReturns400ForEmptyDna() throws Exception {
        System.out.println("========== Test 4 ==========");
        System.out.println("POST /mutant debe retornar 400 Bad Request para ADN vacío");

        DnaRequest request = new DnaRequest(new String[]{});  // Array vacío

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());  // 400 Bad Request
        System.out.println("ADN vacío detectado exitosamente");
    }

    //Test 5: GET /stats - Retorna Estadísticas
    @Test
    @DisplayName("GET /stats debe retornar estadísticas correctamente")
    void testGetStatsReturnsCorrectData() throws Exception {
        System.out.println("========== Test 5 ==========");
        System.out.println("GET /stats debe retornar estadísticas correctamente");

        // ARRANGE
        StatsResponse statsResponse = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(statsResponse);

        // ACT & ASSERT
        mockMvc.perform(
                        get("/stats")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
        System.out.println("Estadísticas correctas");
    }

    //Test 6: GET /stats - Sin Datos
    @Test
    @DisplayName("GET /stats debe retornar 200 OK incluso sin datos")
    void testGetStatsReturns200WithNoData() throws Exception {
        System.out.println("========== Test 6 ==========");
        System.out.println("GET /stats debe retornar 200 OK incluso sin datos");

        StatsResponse statsResponse = new StatsResponse(0, 0, 0.0);
        when(statsService.getStats()).thenReturn(statsResponse);

        mockMvc.perform(
                        get("/stats")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
        System.out.println("Funcionamiento sin datos correcto");
    }

    //Test 7: POST /mutant - Rechaza Body Vacío
    @Test
    @DisplayName("POST /mutant debe rechazar request sin body")
    void testCheckMutantRejectsEmptyBody() throws Exception {
        System.out.println("========== Test 7 ==========");
        System.out.println("POST /mutant debe rechazar request sin body");

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                        // NO se incluye .content() → body vacío
                )
                .andExpect(status().isBadRequest());  // 400 Bad Request
        System.out.println("Funcionamiento correcto ");
    }

    //Test 8: POST /mutant - Acepta JSON
    @Test
    @DisplayName("POST /mutant debe aceptar Content-Type application/json")
    void testCheckMutantAcceptsJsonContentType() throws Exception {
        System.out.println("========== Test 8 ==========");
        System.out.println("POST /mutant debe aceptar Content-Type application/json");

        String[] mutantDna = {
                "ATGCGA", "CAGTGC", "TTATGT",
                "AGAAGG", "CCCCTA", "TCACTG"
        };
        DnaRequest request = new DnaRequest(mutantDna);

        when(mutantService.analyzeDna(any(String[].class)))
                .thenReturn(true);

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)  // ← Importante
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        System.out.println("Funcionamiento correcto ");
    }
}
