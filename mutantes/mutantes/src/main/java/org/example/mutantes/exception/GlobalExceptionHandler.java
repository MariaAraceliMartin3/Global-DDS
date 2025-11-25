package org.example.mutantes.exception;

import org.example.mutantes.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion() {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, "DNA inválido", "DNA no puede ser nulo o vacío", "/mutant"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarBodyVacio() {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, "Request inválido", "El body de la request está vacío o es inválido", "/mutant"));
    }

    @ExceptionHandler(DnaHashCalculationException.class)
    public ResponseEntity<ErrorResponse> manejarErrorHashDna(DnaHashCalculationException error) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Error procesando DNA", "No se pudo procesar el DNA debido a un error interno", "/mutant"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarError(
            Exception error, HttpServletRequest request) {

        ErrorResponse respuestaError = new ErrorResponse(
                500, "Error interno", error.getMessage(), request.getRequestURI()
        );

        return ResponseEntity.status(500).body(respuestaError);
    }
}