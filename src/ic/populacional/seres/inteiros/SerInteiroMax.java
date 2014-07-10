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

/**
 * Implementação da classe SerInteiro com limites atribuídos ao máximo.
 *
 * <p>
 * Essa classe estende a classe <code>SerInteiro</code>, atribuindo como limites
 * aos valores dos locus os limites da representação para inteiros:
 * <code>Integer.MIN_VALUE</code>, <code>Integer.MAX_VALUE-1</code>.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerInteiroMax<G extends Number & Comparable<G>> extends SerInteiro<G> {

    /**
     * Construtor.
     * 
     * @param ncaracteristicas Número de características.
     * @since 1.0
     */
    public SerInteiroMax(int ncaracteristicas) {
        super(ncaracteristicas,Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
