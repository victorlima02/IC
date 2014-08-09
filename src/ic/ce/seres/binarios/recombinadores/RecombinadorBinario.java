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
package ic.ce.seres.binarios.recombinadores;

import ic.ce.populacional.algoritmo.operadores.Recombinador;
import ic.ce.seres.binarios.SerBinario;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 */
public abstract class RecombinadorBinario<G extends Number & Comparable<G>,S extends SerBinario<G>> extends Recombinador<G,S> {

    /**
     * Construtor.
     *
     * @since 1.0
     * @param probabilidadeDeRecombinacao Probabilidade de recombinação, entre
     * [0-1].
     * @param nParceiros Número de parceiros necessários para cada operação de
     * reprodução.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Caso a probabilidade de recombinação não esteja em [0,1];</li>
     * <li>Caso o número de parceiros for menor ou igual a 0.</li>
     * </ul>
     */
    public RecombinadorBinario(double probabilidadeDeRecombinacao, int nParceiros) {
        super(probabilidadeDeRecombinacao, nParceiros);
    }

}
