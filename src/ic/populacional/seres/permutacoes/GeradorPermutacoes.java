/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.permutacoes;

import ic.populacional.algoritmo.operadores.Gerador;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerador de seres baseados em permutações.
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <S> Classe dos Seres.
 */
public abstract class GeradorPermutacoes<S extends SerPermutacao> extends Gerador<S> {

    /**
     * Retorna n números aleatórios em uma permutação de inteiros.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param inicio Menor número na permutação.
     * @param fim Maior número na permutação.
     * @return Índices escolhidos aleatoriamente, em uma permutação de inteiros.
     */
    public final static List<Integer> getPermutacao(int inicio, int fim) {

        List<Integer> lista = new ArrayList<>();
        for (int i = inicio; i <= fim; i++) {
            lista.add(i);
        }
        java.util.Collections.shuffle(lista);
        return lista;
    }

}
