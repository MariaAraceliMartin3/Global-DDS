package org.example.mutantes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class MutantDetector {
    //Establecemos la secuencia en el valor "4"
    private static final int SECUENCIA_NECESARIA = 4;
    private static final Set<Character> BASES_VALIDAS = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        //Verificamos que no sea nulo o menor que 4
        if (dna == null || dna.length < SECUENCIA_NECESARIA) {
            return false;
        }
        int n = dna.length;

        //Verificamos que sea matriz cuadrada y tenga solo A,T,C,G
        for (String fila : dna) {
            if (fila == null || fila.length() != n) {
                return false;
            }
            for (char c : fila.toCharArray()) {
                if (!BASES_VALIDAS.contains(c)) {
                    return false;
                }
            }
        }

        //Convertimos a matriz de caracteres
        char[][] matriz = new char[n][n];
        for (int i = 0; i < n; i++) {
            matriz[i] = dna[i].toCharArray();
        }
        int secuenciasEncontradas = 0;

        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {

                //Buscamos de forma horizontal
                if (col <= n - SECUENCIA_NECESARIA) {
                    if (checkHorizontal(matriz, fila, col)) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return true; // Early termination
                    }
                }

                //Buscamos de forma vertical
                if (fila <= n - SECUENCIA_NECESARIA) {
                    if (checkVertical(matriz, fila, col)) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return true;
                    }
                }

                //Buscamos de forma diagonal descendente
                if (fila <= n - SECUENCIA_NECESARIA && col <= n - SECUENCIA_NECESARIA) {
                    if (checkDiagonalDescendente(matriz, fila, col)) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return true;
                    }
                }

                //Buscamos de forma diagonal ascendente
                if (fila >= SECUENCIA_NECESARIA - 1 && col <= n - SECUENCIA_NECESARIA) {
                    if (checkDiagonalAscendente(matriz, fila, col)) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return true;
                    }
                }
            }
        }

        return secuenciasEncontradas > 1;
    }

    //Buscamos que hayan 4 letras iguales de forma horizontal
    private boolean checkHorizontal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila][col + 1] == base &&
                matriz[fila][col + 2] == base &&
                matriz[fila][col + 3] == base;
    }

    //Buscamos que hayan 4 letras iguales de forma vertical
    private boolean checkVertical(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila + 1][col] == base &&
                matriz[fila + 2][col] == base &&
                matriz[fila + 3][col] == base;
    }

    //Buscamos que hayan 4 letras iguales de forma diagonal descendente
    private boolean checkDiagonalDescendente(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila + 1][col + 1] == base &&
                matriz[fila + 2][col + 2] == base &&
                matriz[fila + 3][col + 3] == base;
    }

    //Buscamos que hayan 4 letras iguales de forma diagonal ascendente
    private boolean checkDiagonalAscendente(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila - 1][col + 1] == base &&
                matriz[fila - 2][col + 2] == base &&
                matriz[fila - 3][col + 3] == base;
    }
}