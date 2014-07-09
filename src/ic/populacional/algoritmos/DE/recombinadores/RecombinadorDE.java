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
package ic.populacional.algoritmos.DE.recombinadores;

import ic.populacional.seres.reais.SerReal;
import ic.populacional.seres.reais.recombinadores.RecombinadorReal;

/**
 * Operador de recombinação para DE.
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 */
public abstract class RecombinadorDE<G extends Number & Comparable<G>,S extends SerReal<G>> extends RecombinadorReal<G,S> {

    private Double probabilidadeDeCrossover;

    public RecombinadorDE(Double probabilidadeDeCrossover) {
        super( 1d, 1);
        setProbabilidadeDeCrossover(probabilidadeDeCrossover);
    }

    /**
     * Recupera a probabilidade de crossover.
     *
     * <p>
     * OBS: A probabilidade que define se a operação de recombinação ocorre ou
     * não é a probabilidade de recombinação.
     * </p>
     *
     * @since 1.0
     * @return A probabilidade de crossover atual.
     * 
     * @see #getProbabilidadeDeRecombinacao() 
     */
    public final Double getProbabilidadeDeCrossover() {
        return probabilidadeDeCrossover;
    }

    /**
     * Atribuí o a probabilidade de crossover para recombinação.
     *
     * <p>
     * Essa probabilidade impactará nas operações de recombinação, ditando a
     * origem dos genes providos ao "Vetor experimental", nos algoritmos DE. </p
     *
     * <p>
     * OBS: A probabilidade que define se a operação de recombinação ocorre ou
     * não é a probabilidade de recombinação.
     * </p>
     *
     * @since 1.0
     * @param probabilidadeDeCrossover Nova probabilidade de crossover.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Caso a probabilidade de crossover não esteja em [0,1].</li>
     * </ul>
     *
     * @see #setProbabilidadeDeRecombinacao(double)
     */
    public final void setProbabilidadeDeCrossover(Double probabilidadeDeCrossover) {
        if (probabilidadeDeCrossover > 1 || probabilidadeDeCrossover < 0) {
            throw new IllegalArgumentException(
                    "Probabilidade de crossover deve estar em [0,1].");
        }
        this.probabilidadeDeCrossover = probabilidadeDeCrossover;
    }

}
