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
package ic.ce.populacional.utilidades;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class Aleatorios {

    /**
     * Retorna um numero de ponto flutuante em [0-1).
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
     * @return Número escolhido aleatoriamente.
     */
    public final static double getUniformeDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * Retorna um numero de ponto flutuante em [a-b).
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
     * @param a Limite inferior, inclusive.
     * @param b Limite superior, exclusive.
     * @return Número escolhido aleatoriamente.
     */
    public final static double getUniformeDouble(Double a, Double b) {
        return ThreadLocalRandom.current().nextDouble(a, b);
    }

    /**
     * Realiza um sorteio aleatório.
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
     * @param probabilidadeDeSucesso Probabilidade de sucesso.
     * @return<ul>
     * <li>true: se o número sorteado for menor ou igual ao parâmetro;</li>
     * <li>false: se o número sorteado for menor ou igual ao parâmetro;</li>
     * </ul>
     */
    public static boolean sorteioUniforme(double probabilidadeDeSucesso) {
        return (getUniformeDouble() <= probabilidadeDeSucesso);
    }
}
