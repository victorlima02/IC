/*
 * The MIT License
 *
 * Copyright 2014 Victor de Lima Soares.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ic.ce.populacional.seres.permutacoes;

import ic.ce.populacional.algoritmo.operadores.Gerador;
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
