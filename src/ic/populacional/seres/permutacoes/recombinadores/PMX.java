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
package ic.populacional.seres.permutacoes.recombinadores;

import ic.populacional.Caracteristica;
import ic.populacional.Ser;
import ic.populacional.seres.permutacoes.SerPermutacao;
import ic.populacional.utilidades.IndiceAleatorio;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Partially Mapped Crossover (PXM).
 *
 * <p>
 * Recombinação para representações baseadas em permutações.
 * </p>
 * <h3>Passos:</h3>
 * <ol>
 * <li>Escolhe dois pontos de crossover aleatoriamente;</li>
 * <li>Copia todos os elementos de P1 entre os pontos no filho, nas mesmas
 * posições ocupadas em P1;</li>
 * <li>Para cada elemento entre os pontos de crossover em P2 ainda não
 * adicionado, adiciona-o ao filho, na posição ocupada pelo ponto proveniente de
 * P1 em P2:
 * <ul>
 * <li>
 * Para o elemento na posição <i>i</i> em P2, busca no filho o elemento que
 * ocupa a mesma posição <i>i</i>, de valor <i>n</i>;
 * </li>
 * <li>
 * A posição do elemento com valor <i>n</i> será então <i>j</i>, definido pela
 * posição <i>j</i> em P2, cujo valor também seja <i>n</i>;
 * </li>
 * <li>
 * Em caso de conflito, quando <i>j</i> indica uma posição já ocupada, repete-se
 * o processo até que uma posição vazia seja encontrada.
 * </li>
 * </ul>
 * <li>Copia-se o restante dos elementos provenientes de P2;</li>
 * <li>Realiza-se o mesmo procedimento para a obtenção de um segundo filho,
 * invertendo os papeis de P1 e P2.</li>
 * </ol>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 */
public class PMX<G extends Number & Comparable<G>,S extends SerPermutacao<G>> extends RecombinadorPermutacao<G,S> {

    private boolean limitado = false;
    private boolean fixado = false;
    private boolean limitesAtivos = false;
    private int limiteInferiorCross;
    private int limiteSuperiorCross;

    public PMX(double probabilidadeDeRecombinacao) {
        super(probabilidadeDeRecombinacao, 2);
    }

    /**
     * @since 1.0
     */
    @Override
    public List<S> recombina(List<S> pares) {
        List<S> filhos;

        filhos = new ArrayList<>(2);
        S par1 = pares.get(0);
        S par2 = pares.get(1);

        List<Integer> crossover = posicoesDeCrossover(par1);
        
        filhos.add(pmxParcial(par1, par2, crossover.get(0), crossover.get(1)));
        filhos.add(pmxParcial(par2, par1, crossover.get(0), crossover.get(1)));

        return filhos;
    }

    /**
     * Atribui limites as posições de crossover, e os ativa.
     *
     * @since 1.0
     * @param limiteInferiorCross Limite inferior, maior que zero.
     * @param limiteSuperiorCross Limite superior, maior que zero.
     * @param fixaPosicoes Define se as posições de corte devem ser aleatórias
     * ou fixadas nos limites.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite inferior for menor que zero;
     * </li>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * </ul>
     */
    public void setLimites(int limiteInferiorCross, int limiteSuperiorCross, boolean fixaPosicoes) {

        if (limiteInferiorCross < 0) {
            throw new IllegalArgumentException("Limite inferior deve ser maior que zero.");
        }
        if (limiteInferiorCross > limiteSuperiorCross) {
            throw new IllegalArgumentException("Limite superior deve ser maior que o limite inferior.");
        }

        this.limiteInferiorCross = limiteInferiorCross;
        this.limiteSuperiorCross = limiteSuperiorCross;
        this.limitado = true;
        this.fixado = fixaPosicoes;
        this.ativaLimites();
    }

    /**
     * Desativa, sem apagar, os limites impostos as posições de crossover.
     *
     * @since 1.0
     */
    public void desativaLimites() {
        this.limitesAtivos = false;
    }

    /**
     * Ativa, os limites impostos as posições de crossover.
     *
     * @since 1.0
     *
     * @throws IllegalStateException
     * <ul>
     * <li>Se Limites não estiverem sido atribuídos.</li>
     * </ul>
     */
    public void ativaLimites() {
        if (!limitado) {
            throw new IllegalStateException("Limites não existentes.");
        }
        this.limitesAtivos = true;
    }

    /**
     * Calcula as posições de crossover.
     *
     * @since 1.0
     * @param par Ser base para cálculo dos índices: usado apenas para delimitar
     * os limites de tamanho.
     * @return Lista com as posições de crossover.
     */
    private List<Integer> posicoesDeCrossover(Ser par) {
        List<Integer> posicoes = new ArrayList<>(2);

        if (limitado && limitesAtivos) {
            if (fixado || (limiteInferiorCross == limiteSuperiorCross)) {
                posicoes.add(limiteInferiorCross);
                posicoes.add(limiteSuperiorCross);
            } else {
                List<Integer> crossover = IndiceAleatorio.getUniforme(par, 2, limiteInferiorCross, limiteSuperiorCross);
                crossover.sort(null);
                posicoes.add(crossover.get(0));
                posicoes.add(crossover.get(1));
            }
        } else {
            List<Integer> crossover = IndiceAleatorio.getUniforme(par, 2);
            crossover.sort(null);
            posicoes.add(crossover.get(0));
            posicoes.add(crossover.get(1));
        }

        return posicoes;
    }

    /**
     * Realiza cruzamento, gerando um filho.
     *
     * <h3>Passos:</h3>
     * <ol>
     * <li>Escolhe dois pontos de crossover aleatoriamente;</li>
     * <li>Copia todos os elementos de P1 entre os pontos no filho, nas mesmas
     * posições ocupadas em P1;</li>
     * <li>Para cada elemento entre os pontos de crossover em P2 ainda não
     * adicionado, adiciona-o ao filho, na posição ocupada pelo ponto
     * proveniente de P1 em P2:
     * <ul>
     * <li>
     * Para o elemento na posição <i>i</i> em P2, busca no filho o elemento que
     * ocupa a mesma posição <i>i</i>, de valor <i>n</i>;
     * </li>
     * <li>
     * A posição do elemento com valor <i>n</i> será então <i>j</i>, definido
     * pela posição <i>j</i> em P2, cujo valor também seja <i>n</i>;
     * </li>
     * <li>
     * Em caso de conflito, quando <i>j</i> indica uma posição já ocupada,
     * repete-se o processo até que uma posição vazia seja encontrada.
     * </li>
     * </ul>
     * <li>Copia-se o restante dos elementos provenientes de P2.</li>
     * </ol>
     *
     * <b>A ordem dos progenitores altera o resultado.</b>
     *
     * @param par1 Primeiro progenitor.
     * @param par2 Segundo progenitor.
     * @param crossover1 Posição de corte: posição inicial.
     * @param crossover2 Posição de corte: posição final.
     * @return filho gerado.
     */
    private S pmxParcial(S par1, S par2, int crossover1, int crossover2) {
        S filho = getAlgoritmo().getGerador().get();

        List<Caracteristica> cromossomol = par1.getCaracteristicasCopia();
        List<Caracteristica> cromossomo2 = par2.getCaracteristicasCopia();

        List<Caracteristica> cromossomoMeio1 = cromossomol.subList(crossover1, crossover2 + 1);
        List<Caracteristica> cromossomoMeio2 = cromossomo2.subList(crossover1, crossover2 + 1);

        BitSet usados = new BitSet(cromossomol.size());

        filho.setCaracteristicas(crossover1, cromossomoMeio1);
        usados.set(crossover1, crossover2 + 1);

        int index;
        for (int i = 0; i < cromossomoMeio1.size(); i++) {

            Caracteristica locus = cromossomoMeio2.get(i);
            if (!cromossomoMeio1.contains(locus)) {

                index = crossover1 + i;
                while (usados.get(index)) {
                    Caracteristica locus1 = cromossomol.get(index);
                    index = cromossomo2.indexOf(locus1);
                }

                filho.setCaracteristica(index, locus);
                usados.set(index);
            }
        }

        while (usados.nextClearBit(0) < cromossomo2.size()) {
            index = usados.nextClearBit(0);
            filho.setCaracteristica(index, cromossomo2.get(index));
            usados.set(index);
        }

        return filho;
    }

}
