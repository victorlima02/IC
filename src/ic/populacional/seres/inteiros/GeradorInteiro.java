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
package ic.populacional.seres.inteiros;

import ic.populacional.algoritmo.operadores.Gerador;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Gerador de seres baseados em números inteiros.
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 * @param <S> Classe dos Seres.
 */
public abstract class GeradorInteiro<S extends SerInteiro> extends Gerador<S> {

    /**
     * Retorna n números inteiros aleatórios.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Geração segura para multithread.
     * </p>
     *
     * @since 1.0
     * @param n Número de inteiros requeridos.
     * @return Lista de inteiros.
     */
    public final static List<Integer> getRandomIntegers(int n) {
        return ThreadLocalRandom.current().ints(n).mapToObj(Integer::new).collect(Collectors.toList());
    }

    /**
     * Retorna n números inteiros aleatórios.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Geração segura para multithread.
     * </p>
     *
     * @since 1.0
     * @param n Número de inteiros na requeridos.
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, inclusive.
     * @return Lista de inteiros.
     */
    public final static List<Integer> getRandomIntegers(int n,int limiteInferior,int limiteSuperior) {
        return ThreadLocalRandom.current().ints(n, limiteInferior, limiteSuperior+1).mapToObj(Integer::new).collect(Collectors.toList());
    }

}
