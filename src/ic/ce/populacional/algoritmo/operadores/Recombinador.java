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
package ic.ce.populacional.algoritmo.operadores;

import ic.ce.populacional.Caracteristica;
import ic.ce.populacional.Ser;
import ic.ce.populacional.utilidades.Aleatorios;
import ic.ce.populacional.utilidades.IndiceAleatorio;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Operador de recombinação.
 *
 * <p>
 * Classe base para todos os operadores de recombinação.
 * </p>
 * <p>
 * Por meio deste operador, será realizada a recombinação de seres no decorrer
 * das iterações dos algoritmos evolucionários. Devendo, em suas derivações,
 * prover meios de reprodução para listas de seres, tendo uma lista de filhos
 * como retorno.
 * </p>
 * <p>
 * O uso de geradores é aconselhado para instanciação de novos seres, mesmo que
 * ainda sem características.
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
 */
public abstract class Recombinador<G extends Number & Comparable<G>, S extends Ser<G>> extends Operador<G, S> implements Function<List<S>, List<S>> {

    private double probabilidadeDeRecombinacao;
    private int nParceiros;

    /**
     * Construtor.
     *
     * @since 1.0
     *
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
    public Recombinador(double probabilidadeDeRecombinacao, int nParceiros) {
        setNParceiros(nParceiros);
        setProbabilidadeDeRecombinacao(probabilidadeDeRecombinacao);
    }

    /**
     * Realiza a operação de recombinação.
     *
     * <p>
     * Operação n-ária.
     * </p>
     * <p>
     * Define como será feita a reprodução e deve ser definida para adaptação ao
     * algoritmo desejado, provendo uma função que forneça uma lista de novos
     * seres, criados a partir da recombinação dos indivíduos de entrada.
     * </p>
     *
     * @since 1.0
     * @param pares Parceiros para cruzamento.
     * @return Novos seres.
     */
    protected abstract List<S> recombina(List<S> pares);

    /**
     * Alternativa:{@link #recombinaTodos(java.util.List) }.
     *
     * <p>
     * Método alternativo a {@link #recombinaTodos(java.util.List) }. Realiza a
     * chamada do método, para satisfazer a interface <code>Function</code>.
     * </p>
     *
     * @since 1.0
     * @param pares Parceiros para cruzamento.
     * @return Novos seres.
     *
     * @see #recombinaTodos(java.util.List)
     */
    @Override
    public List<S> apply(List<S> pares) {
        return recombinaTodos(pares);
    }

    /**
     * Realiza a operação de recombinação: coleção divisível.
     *
     * <p>
     * Aplica a operação de reprodução a cada sub conjunto de seres da coleção
     * passada como parâmetro. Cada sub conjunto deverá ter tamanho igual ao
     * número de elementos necessários para a recombinação nesse operador.
     * Estando tais conjuntos ordenados em uma lista, pois esse método
     * percorrerá a lista aplicando sub-listas a função {@link #recombinaTodos(java.util.List)
     * }.
     * </p>
     * <p>
     * Para cada sub conjunto, um sorteio será realizado pela função {@link #sorteiaSeRecombina()
     * },em caso de valor verdadeiro, a operação de reprodução é aplicada.
     * </p>
     *
     *
     * @since 1.0
     * @param pares Seres para recombinação.
     * @return Novos seres.
     *
     * @throws IllegalStateException
     * <ul>
     * <li>Se o número de seres para reprodução não for divisível pelo número de
     * parceiros necessários para recombinação.</li>
     * </ul>
     */
    public List<S> recombinaTodos(List<S> pares) {
        int nPais = getNParceiros();

        if (pares.size() % nPais != 0) {
            throw new IllegalStateException("Número de seres para reprodução não é divisível pelo número de parceiros necessários para recombinação.");
        }

        List<S> filhos = new ArrayList<>(pares.size());

        for (int i = 0; i < pares.size(); i += nPais) {
            if (sorteiaSeRecombina()) {
                filhos.addAll(recombina(pares.subList(i, i + nPais)));
            }
        }
        return filhos;
    }

    /**
     * Sorteia se aplica a recombinação.
     *
     * <p>
     * Esse método deve ser usado antes da aplicação da operação de
     * recombinação, {@link #recombina(java.util.List)
     * }. Desse modo, um sorteio será realizado para decidir se a mesma deve ou
     * não ocorrer.
     * </p>
     * <p>
     * Por padrão, essa função utiliza de uma distribuição uniforme, onde a
     * probabilidade de sucesso é definida pelo construtor ou pelo método {@link #setProbabilidadeDeRecombinacao(double)
     * }.
     * </p>
     *
     * <p>
     * Se sobrescrito, deve usar um gerador de números aleatórios seguro para
     * múltiplas Threads.
     * </p>
     *
     * <p>
     * O ser recebido como parâmetro, não é utilizado - por padrão, sendo essa
     * sobrecarga implementada para utilização em streams.
     * </p>
     *
     * @since 1.0
     * @param ser Ser a ser recombinado.
     * @return
     * <ul>
     * <li>true: se deve recombinar;</li>
     * <li>false: caso contrario;</li>
     * </ul>
     *
     * @see #Recombinador(double, int)
     * @see #setProbabilidadeDeRecombinacao(double)
     * @see Aleatorios#sorteioUniforme(double) 
     */
    public Boolean sorteiaSeRecombina(Ser ser) {
        return sorteiaSeRecombina();
    }

    /**
     * Sorteia se aplica a recombinação.
     *
     * <p>
     * Esse método deve ser usado antes da aplicação da operação de
     * recombinação, {@link #recombina(java.util.List)
     * }. Desse modo, um sorteio será realizado para decidir se a mesma deve ou
     * não ocorrer.
     * </p>
     * <p>
     * Por padrão, essa função utiliza de uma distribuição uniforme, onde a
     * probabilidade de sucesso é definida pelo construtor ou pelo método {@link #setProbabilidadeDeRecombinacao(double)
     * }.
     * </p>
     *
     * <p>
     * Se sobrescrito, deve usar um gerador de números aleatórios seguro para
     * múltiplas Threads.
     * </p>
     *
     *
     * @since 1.0
     * @return
     * <ul>
     * <li>true: se deve recombinar;</li>
     * <li>false: caso contrario;</li>
     * </ul>
     *
     * @see #Recombinador(double, int)
     * @see #setProbabilidadeDeRecombinacao(double)
     * @see Aleatorios#sorteioUniforme(double) 
     */
    public Boolean sorteiaSeRecombina() {
        return Aleatorios.sorteioUniforme(getProbabilidadeDeRecombinacao());
    }

    /**
     * Atribui a probabilidade de reprodução.
     *
     * Atribui uma probabilidade para a ocorrência de cada operação de
     * recombinação invocada por {@link #recombinaTodos(java.util.List) }.
     *
     * @since 1.0
     * @param probabilidadeDeRecombinacao Probabilidade de recombinação, entre
     * [0-1].
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Caso a probabilidade de recombinação não esteja em [0,1].</li>
     * </ul>
     *
     * @see #recombinaTodos(java.util.List)
     * @see #apply(java.util.List)
     */
    public final void setProbabilidadeDeRecombinacao(double probabilidadeDeRecombinacao) {

        if (probabilidadeDeRecombinacao > 1 || probabilidadeDeRecombinacao < 0) {
            throw new IllegalArgumentException(
                    "Probabilidade de recombinação deve estar em [0,1].");
        }

        this.probabilidadeDeRecombinacao = probabilidadeDeRecombinacao;
    }

    /**
     * Retorna a probabilidade de cruzamento.
     *
     * @since 1.0
     * @return a probabilidade de cruzamento.
     */
    public final double getProbabilidadeDeRecombinacao() {
        return probabilidadeDeRecombinacao;
    }

    /**
     * Retorna o número de parceiros necessários para cada operação de
     * reprodução.
     *
     * @since 1.0
     * @return Número de parceiros necessários para cada operação de reprodução.
     */
    public final int getNParceiros() {
        return nParceiros;
    }

    /**
     * Atribui o número de parceiros necessários para cada operação de
     * reprodução.
     *
     * @since 1.0
     * @param nParceiros Número de parceiros necessários para cada operação de
     * reprodução.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Caso o número de parceiros for menor ou igual a 0.</li>
     * </ul>
     */
    public final void setNParceiros(int nParceiros) {
        if (nParceiros <= 0) {
            throw new IllegalArgumentException(
                    "Número de parceiros deve ser maior do que 0.");
        }
        this.nParceiros = nParceiros;
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     *
     * <ol>
     * <li>Seleciona aleatoriamente a posição do crossover: aleatoriamente.</li>
     * <li><b>Copia</b> o 1o segmento do par1 para o filho1 e o 1o segmento do
     * par2 para o filho2.</li>
     * <li><b>Copia</b> o 2o segmento do par1 para o filho2 e o 2o segmento do
     * par2 para o filho2.</li>
     * </ol>
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
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     */
    public final List<S> onePointCrossover(S par1, S par2) {
        int crossover = IndiceAleatorio.getUniforme(par1);
        return onePointCrossover(par1, par2, crossover);
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     *
     * <ol>
     * <li>Utiliza a posição de crossover,pré-definida, para o corte.</li>
     * <li><b>Copia</b> o 1o segmento do par1 para o filho1 e o 1o segmento do
     * par2 para o filho2.</li>
     * <li><b>Copia</b> o 2o segmento do par1 para o filho2 e o 2o segmento do
     * par2 para o filho2.</li>
     * </ol>
     *
     * @since 1.0
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento
     * @param crossoverPosicao Posição de corte.
     * @return Lista contendo os dois elementos filhos.
     */
    public final List<S> onePointCrossover(S par1, S par2, int crossoverPosicao) {
        List<S> filhos = new ArrayList<>(2);

        S f1 = getAlgoritmo().getGerador().get();
        S f2 = getAlgoritmo().getGerador().get();

        filhos.add(f1);
        filhos.add(f2);

        List<Caracteristica> cromossomol = par1.getCaracteristicas();
        List<Caracteristica> cromossomo2 = par2.getCaracteristicas();

        List<Caracteristica> cromossomo1p1 = cromossomol.subList(0, crossoverPosicao);
        List<Caracteristica> cromossomo1p2 = cromossomol.subList(crossoverPosicao, cromossomol.size());

        List<Caracteristica> cromossomo2p1 = cromossomo2.subList(0, crossoverPosicao);
        List<Caracteristica> cromossomo2p2 = cromossomo2.subList(crossoverPosicao, cromossomo2.size());

        f1.setCaracteristicasCopia(0, cromossomo1p1);
        f2.setCaracteristicasCopia(0, cromossomo2p1);

        f1.setCaracteristicasCopia(crossoverPosicao, cromossomo2p2);
        f2.setCaracteristicasCopia(crossoverPosicao, cromossomo1p2);

        return filhos;
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     * <ol>
     * <li>Seleciona aleatoriamente a posição do crossover: aleatoriamente.</li>
     * <li><b>Copia</b> o 1o segmento do par1 para o filho1 e o 1o segmento do
     * par2 para o filho2.</li>
     * <li><b>Copia</b> o 2o segmento do par1 para o filho2 e o 2o segmento do
     * par2 para o filho2.</li>
     * </ol>
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
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     */
    public static final List<List<Caracteristica>> onePointCrossover(List<Caracteristica> par1, List<Caracteristica> par2) {
        int crossoverPosicao = IndiceAleatorio.getUniforme(par1);
        return onePointCrossover(par1, par2, crossoverPosicao);
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     *
     * <ol>
     * <li>Utiliza a posição de crossover,pré-definida, para o corte.</li>
     * <li><b>Copia</b> o 1o segmento do par1 para o filho1 e o 1o segmento do
     * par2 para o filho2.</li>
     * <li><b>Copia</b> o 2o segmento do par1 para o filho2 e o 2o segmento do
     * par2 para o filho2.</li>
     * </ol>
     *
     * @since 1.0
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento
     * @param crossoverPosicao Posição de corte.
     * @return Lista contendo os dois elementos filhos.
     */
    public static final List<List<Caracteristica>> onePointCrossover(List<Caracteristica> par1, List<Caracteristica> par2, int crossoverPosicao) {
        List<List<Caracteristica>> filhos = new ArrayList<>(2);

        List<Caracteristica> f1 = new ArrayList<>(par1.size());
        List<Caracteristica> f2 = new ArrayList<>(par2.size());

        filhos.add(f1);
        filhos.add(f2);

        List<Caracteristica> cromossomol = new ArrayList<>(par1.size());
        List<Caracteristica> cromossomo2 = new ArrayList<>(par2.size());

        par1.stream().forEach((locus) -> {
            cromossomol.add(locus.copia());
        });
        par2.stream().forEach((locus) -> {
            cromossomo2.add(locus.copia());
        });

        List<Caracteristica> cromossomo1p1 = cromossomol.subList(0, crossoverPosicao);
        List<Caracteristica> cromossomo1p2 = cromossomol.subList(crossoverPosicao, cromossomol.size());

        List<Caracteristica> cromossomo2p1 = cromossomo2.subList(0, crossoverPosicao);
        List<Caracteristica> cromossomo2p2 = cromossomo2.subList(crossoverPosicao, cromossomo2.size());

        f1.addAll(cromossomo1p1);
        f2.addAll(cromossomo2p1);

        f1.addAll(cromossomo2p2);
        f2.addAll(cromossomo1p2);

        return filhos;
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     * <ul>
     * <li>Seleciona aleatoriamente a posição do crossover.</li>
     * <li>Copia o 1o segmento do par1 para o filho1 e o 1o segmento do par2
     * para o filho2.</li>
     * <li>A partir da posição do crossover, escaneia o par2 da esquerda para a
     * direita e preenche o 2o segmento do filho1 com os valores do par2,
     * pulando os valores repetidos. Repete processo envolvendo par2 e filho2.
     * </li>
     * <li>Completa os genes faltantes dos filhos com genes não-repetidos do
     * pais originais.</li>
     * </ul>
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
     * @param par1 Parceiros para cruzamento.
     * @param par2 Parceiros para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     */
    public final List<S> onePointCrossoverSemRepeticao(S par1, S par2) {
        int crossoverPosicao = IndiceAleatorio.getUniforme(par1);

        return onePointCrossoverSemRepeticao(par1, par2, crossoverPosicao);
    }

    /**
     * Operação de recombinação: One-point crossover.
     *
     * <ul>
     * <li>Utiliza a posição de crossover,pré-definida, para o corte.</li>
     * <li>Copia o 1o segmento do par1 para o filho1 e o 1o segmento do par2
     * para o filho2.</li>
     * <li>A partir da posição do crossover, escaneia o par2 da esquerda para a
     * direita e preenche o 2o segmento do filho1 com os valores do par2,
     * pulando os valores repetidos. Repete processo envolvendo par2 e filho2.
     * </li>
     * <li>Completa os genes faltantes dos filhos com genes não-repetidos do
     * pais originais.</li>
     * </ul>
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
     * @param par1 Parceiros para cruzamento.
     * @param par2 Parceiros para cruzamento.
     * @param crossoverPosicao Posição de corte.
     * @return Lista contendo os dois elementos filhos.
     */
    public final List<S> onePointCrossoverSemRepeticao(S par1, S par2, int crossoverPosicao) {
        List<S> filhos = new ArrayList<>(2);

        S f1 = getAlgoritmo().getGerador().get();
        S f2 = getAlgoritmo().getGerador().get();

        filhos.add((S) f1);
        filhos.add((S) f2);

        List<Caracteristica> cromossomol = par1.getCaracteristicasCopia();
        List<Caracteristica> cromossomo2 = par2.getCaracteristicasCopia();

        List<Caracteristica> cromossomo1p1 = new ArrayList(cromossomol.subList(0, crossoverPosicao));
        List<Caracteristica> cromossomo1p2 = new ArrayList(cromossomol.subList(crossoverPosicao, cromossomol.size()));

        List<Caracteristica> cromossomo2p1 = new ArrayList(cromossomo2.subList(0, crossoverPosicao));
        List<Caracteristica> cromossomo2p2 = new ArrayList(cromossomo2.subList(crossoverPosicao, cromossomo2.size()));

        cromossomo1p2.addAll(cromossomo1p1);
        cromossomo1p2.removeAll(cromossomo2p1);

        cromossomo2p2.addAll(cromossomo2p1);
        cromossomo2p2.removeAll(cromossomo1p1);

        f1.setCaracteristicas(0, cromossomo1p1);
        f2.setCaracteristicas(0, cromossomo2p1);

        f1.setCaracteristicas(crossoverPosicao, cromossomo2p2);
        f2.setCaracteristicas(crossoverPosicao, cromossomo1p2);

        return filhos;
    }

    /**
     * Operação de recombinação: Uniform Crossover/Discrite recombination.
     *
     *
     * <p>
     * Para cada locus dos filhos, sorteia - com chances iguais - a origem do
     * locus como sendo do par1 ou 2, então a copia.
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
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @return Lista contendo os dois elementos filhos.
     *
     * @see #discriteRecombination(ic.populacional.Ser, ic.populacional.Ser, java.lang.Integer, java.lang.Double) 
     */
    public final List<S> uniformCrossover(S par1, S par2) {
        return discriteRecombination(par1, par2, 2, 0.5);
    }

    /**
     * Operação de recombinação: Discrite recombination.
     *
     *
     * <p>
     * Para cada locus dos filhos, sorteia - com probabilidade P - a origem do
     * locus como sendo do par1 ou 2, então a copia.
     * </p>
     *
     * <p>
     * Distribuição uniforme para cada sorteio.
     * </p>
     *
     * <p>
     * Geração segura para multithread.
     * </p>
     *
     * @since 1.0
     * @param par1 Parceiro para cruzamento.
     * @param par2 Parceiro para cruzamento.
     * @param nFilhos Número de filhos desejados.
     * @param probabilidade Probabilidade do gene prover do par1.
     * @return Lista contendo os filhos.
     */
    public final List<S> discriteRecombination(final S par1, final S par2, Integer nFilhos, Double probabilidade) {
        List<S> filhos = getAlgoritmo().getGerador().getN(nFilhos);

        for (int i = 0; i < par1.getSize(); i++) {
            for (S filho : filhos) {
                filho.setCaracteristicaCopia(i, (Aleatorios.sorteioUniforme(probabilidade) ? par1.getCaracteristica(i) : par2.getCaracteristica(i)));
            }
        }
        return filhos;
    }

}
