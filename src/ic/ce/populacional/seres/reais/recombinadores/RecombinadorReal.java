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
package ic.ce.populacional.seres.reais.recombinadores;

import ic.ce.populacional.algoritmo.operadores.Recombinador;
import ic.ce.populacional.seres.reais.LocusReal;
import ic.ce.populacional.seres.reais.SerReal;
import ic.ce.populacional.utilidades.IndiceAleatorio;
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
 * @param <S> Classe dos Seres.
 */
public abstract class RecombinadorReal<G extends Number & Comparable<G>,S extends SerReal<G>> extends Recombinador<G,S> {

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
    public RecombinadorReal( Double probabilidadeDeRecombinacao, int nParceiros) {
        super(probabilidadeDeRecombinacao, nParceiros);
    }

    /**
     * Operação de recombinação: Simple Recombination / Simple Arithmetic
     * Recombination.
     *
     * <p>
     * Sorteia uma ponto de recombinação <i>k</i> aleatoriamente.
     * </p>
     * <p>
     * Copia os <i>k</i> primeiros locus do par1 no filho1 e os <i>k</i>
     * primeiros do par2 no filho2.
     * </p>
     * <p>
     * Para cada locus restante, o locus do filho será a média ponderada pelo
     * parâmetro <i>alfa</i>. Sendo para o primeiro filho,
     * f1=alfa(par2)+(1-alfa)(par1) e para o segundo
     * f2=alfa(par1)+(1-alfa)(par2).
     * </p>
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
     * @param alfa Parâmetro para o calculo dos valores médios.
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     *
     * @see #simpleRecombination(int, double,
     * ic.populacional.seres.reais.SerReal, ic.populacional.seres.reais.SerReal)
     */
    public final List<S> simpleRecombination(double alfa, S par1, S par2) {
        int k = IndiceAleatorio.getUniforme(par1);
        return simpleRecombination(k, alfa, par1, par2);
    }

    /**
     * Operação de recombinação: Simple Recombination.
     *
     *
     * <p>
     * Copia os <i>k</i> primeiros locus do par1 no filho1 e os <i>k</i>
     * primeiros do par2 no filho2.
     * </p>
     * <p>
     * Para cada locus restante, o locus do filho será a média ponderada pelo
     * parâmetro <i>alfa</i>. Sendo para o primeiro filho,
     * f1=alfa(par2)+(1-alfa)(par1) e para o segundo
     * f2=alfa(par1)+(1-alfa)(par2).
     * </p>
     * <p>
     * Os limites dos locus são mantidos nos locus resultantes, sendo a cada
     * locus do filho 1 atribuído o limite do locus do par1 e a cada locus do
     * filho 2 o limite do locus proveniente do par2.
     * </p>
     *
     *
     * @since 1.0
     * @param k Ponto de recombinação : ponto inicial dos valores médios.
     * @param alfa Parâmetro para o calculo dos valores médios.
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     */
    public List<S> simpleRecombination(int k, double alfa, S par1, S par2) {
        List<S> filhos = new ArrayList<>(2);

        S f1 = getAlgoritmo().getGerador().get();
        S f2 = getAlgoritmo().getGerador().get();

        filhos.add(f1);
        filhos.add(f2);

        for (int i = 0; i < k; i++) {
            f1.setCaracteristicaCopia(i, par1.getCaracteristica(i));
            f2.setCaracteristicaCopia(i, par2.getCaracteristica(i));
        }

        for (int i = k; i < par1.getSize(); i++) {
            LocusReal locus1 = (LocusReal) par1.getCaracteristica(i);
            LocusReal locus2 = (LocusReal) par2.getCaracteristica(i);

            double locusPar1 = (locus1).getValor();
            double locusPar2 = (locus2).getValor();

            Double media1 = alfa * locusPar2 + (1 - alfa) * locusPar1;
            LocusReal novoLocus1 = new LocusReal(media1, locus1.getLimiteInferior(), locus1.getLimiteSuperior());
            f1.setCaracteristica(i, novoLocus1);

            Double media2 = alfa * locusPar1 + (1 - alfa) * locusPar2;
            LocusReal novoLocus2 = new LocusReal(media2, locus2.getLimiteInferior(), locus2.getLimiteSuperior());
            f2.setCaracteristicaCopia(i, novoLocus2);
        }
        return filhos;
    }

    /**
     * Operação de recombinação: Whole Recombination.
     *
     *
     * <p>
     * Para cada locus, o locus do filho será a média ponderada pelo parâmetro
     * <i>alfa</i>. Sendo para o primeiro filho, f1=alfa(par2)+(1-alfa)(par1) e
     * para o segundo f2=alfa(par1)+(1-alfa)(par2).
     * </p>
     *
     *
     * @since 1.0
     * @param alfa Parâmetro para o calculo dos valores médios.
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     */
    public final List<S> wholeArithmeticRecombination(double alfa, S par1, S par2) {
        return simpleRecombination(0, alfa, par1, par2);
    }
}
