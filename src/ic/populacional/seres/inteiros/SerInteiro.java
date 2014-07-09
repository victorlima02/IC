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

import ic.populacional.Caracteristica;
import ic.populacional.seres.SerFixo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor de Lima Soares
 * 
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerInteiro<G extends Number & Comparable<G>> extends SerFixo<G> {

    protected final int limiteInferior;
    protected final int limiteSuperior;

    /**
     * Construtor.
     *
     * @since 1.0
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, exclusive.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite superior for menor que o limite inferior.
     * </li>
     * </ul>
     */
    public SerInteiro(int ncaracteristicas, int limiteInferior, int limiteSuperior) {
        super(ncaracteristicas);
        if(limiteSuperior<limiteInferior) throw new IllegalArgumentException("Limite superior é menor que o limite inferior");
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior-1;
    }

    /**
     * Recupera o limite inferior do ser.
     *
     * @since 1.0
     * @return Limite inferior, inclusive.
     */
    public int getLimiteInferior() {
        return limiteInferior;
    }

    /**
     * Recupera o limite superior do ser.
     *
     * @since 1.0
     * @return Limite superior, inclusive.
     */
    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public static List<Caracteristica<Integer>> integerListToLocusList(List<Integer> caracteristicas) {

        List<Caracteristica<Integer>> locus = new ArrayList(caracteristicas.size());

        caracteristicas.stream().forEach((inteiro) -> {
            locus.add(new LocusInteiro(inteiro));
        });

        return locus;
    }
}
