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
package ic.ce.base.algoritmo.operadores;

import ic.ce.base.Ambiente;
import ic.ce.populacional.Populacao;
import ic.ce.base.Ser;
import ic.ce.base.algoritmo.AlgoritmoEvolucionario;
import ic.ce.populacional.algoritmo.AlgoritmoPopulacional;

/**
 * Classe base aos operadores evolucionários.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 */
public abstract class Operador<G extends Number & Comparable<G>, S extends Ser<G>> {

    private AlgoritmoEvolucionario<G, S> algoritmo;

    /**
     * Método de acesso ao algoritmos que utiliza desse operador.
     *
     * @since 1.0
     * @return Algoritmo.
     */
    public final AlgoritmoEvolucionario<G, S> getAlgoritmo() {
        return algoritmo;
    }

    /**
     * Atribui ao operador um algoritmo.
     *
     * Por esse método os operadores podem ter acesso as informações vindas dos
     * algoritmos, como o número de iterações passadas, e se adaptarem a elas se
     * necessário.
     *
     * @since 1.0
     *
     * @param algoritmo
     */
    public final void setAlgoritmo(AlgoritmoEvolucionario<G, S> algoritmo) {
        this.algoritmo = algoritmo;
    }

    /**
     * Método de acesso a população.
     *
     * @since 1.0
     * @return população.
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se o algoritmo que utiliza o operador não for um algoritmo
     * populacional.</li>
     * </ul>
     */
    public final Populacao<G, S> getPopulacao() throws ClassCastException {
        return ((AlgoritmoPopulacional) getAlgoritmo()).getPopulacao();
    }

    /**
     * Método de acesso ao ambiente.
     *
     * @since 1.0
     * @return ambiente.
     */
    public final Ambiente<G, S> getAmbiente() {
        return getAlgoritmo().getAmbiente();
    }
}
