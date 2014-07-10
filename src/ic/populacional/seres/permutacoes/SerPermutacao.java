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
/**
 * @author Victor de Lima Soares
 */
package ic.populacional.seres.permutacoes;

import ic.populacional.Caracteristica;
import ic.populacional.seres.inteiros.SerInteiro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerPermutacao<G extends Number & Comparable<G>> extends SerInteiro<G> {

    /**
     * Construtor.
     *
     * @since 1.0
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, exclusive.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * </ul>
     */
    public SerPermutacao(int limiteInferior, int limiteSuperior) {
        super(limiteSuperior - limiteInferior, limiteInferior, limiteSuperior);
    }

    /**
     * Transforma uma lista de inteiros em uma lista de características.
     *
     * @since 1.0
     * @param caracteristicas
     * @return
     */
    public static List<Caracteristica<Integer>> integerListToLocusList(List<Integer> caracteristicas) {

        List<Caracteristica<Integer>> locus = new ArrayList(caracteristicas.size());

        caracteristicas.stream().forEach((inteiro) -> {
            locus.add(new LocusPermutacao(inteiro));
        });

        return locus;
    }
}
