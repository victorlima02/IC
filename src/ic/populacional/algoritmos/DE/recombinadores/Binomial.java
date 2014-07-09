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
import ic.populacional.utilidades.IndiceAleatorio;
import java.util.ArrayList;
import java.util.List;

/**
 * Operador de recombinação para DE: Binomial
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 */
public class Binomial<G extends Number & Comparable<G>, S extends SerReal<G>> extends RecombinadorDE<G, S> {

    public Binomial(Double probabilidadeDeCrossover) {
        super(probabilidadeDeCrossover);
    }

    @Override
    protected List<S> recombina(List<S> pares) {
        S doador = pares.get(0);
        S alvo = getPopulacao().get(IndiceAleatorio.getUniforme(getPopulacao()));

        S experimental = discriteRecombination(doador, alvo, 1, getProbabilidadeDeCrossover()).get(0);
        experimental.setGrauDeAdaptacao(getAmbiente());

        List<S> filhos = new ArrayList<>(1);

        getPopulacao().remove(alvo);

        if (getAmbiente().compare(experimental, alvo) > 0) {
            filhos.add(experimental);
        } else {
            filhos.add(alvo);
        }

        return filhos;
    }

}
