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
package ic.ce.populacional;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * População ordenada.
 *
 * <p>
 * População mantém os seres ordenados de acordo com o ambiente usado para
 * construção.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 *
 * @see Populacao
 */
public class PopulacaoOrdenada<G extends Number & Comparable<G>, S extends Ser<G>> extends Populacao<G, S> {

    /**
     * Construtor.
     *
     * @since 1.0
     *
     * @param ambiente Ambiente avaliador.
     * @param maxIndividuos Número máximo de indivíduos na população. Esse
     * parâmetro pode ser usado para controlar barreiras impostas por algoritmos
     * com finalidade algorítmica ou por escassez de recursos.
     * <ul>
     * <li>Deve ser um número natural maior que zero;</li>
     * <li>O valor zero indica a ausência de limite. </li>
     * </ul>
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>O ambiente for uma referência nula;</li>
     * <li>Se o número máximo de indivíduos for menor que zero.</li>
     * </ul>
     *
     * @see Populacao#setMaxIndividuos(int)
     */
    public PopulacaoOrdenada(Ambiente<G, S> ambiente, int maxIndividuos) {
        super(ambiente, maxIndividuos);
        seres = new TreeSet<>(ambiente);
    }

    @Override
    public S getMelhor() {
        return ((TreeSet<S>) seres).last();
    }

    @Override
    public List<S> getNMelhores(int n) {
        return ((TreeSet<S>) seres).descendingSet().stream().limit(n).collect(Collectors.toList());
    }

}
