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
package ic.populacional.algoritmo;

import ic.populacional.Ambiente;
import ic.populacional.Populacao;
import ic.populacional.Ser;
import ic.populacional.algoritmo.operadores.Gerador;
import ic.populacional.algoritmo.operadores.Mutador;
import ic.populacional.algoritmo.operadores.Recombinador;
import ic.populacional.algoritmo.operadores.Seletor;

/**
 * Classe base para os Algoritmo Evolucionários.
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
public abstract class AlgoritmoEvolucionario<G extends Number & Comparable<G>, S extends Ser<G>> implements Runnable {

    protected String nome;
    protected Gerador<S> gerador;
    protected Mutador<S> mutador;
    protected Recombinador<G, S> recombinador;
    protected Seletor<G, S> seletor;

    protected Ambiente<G, S> ambiente;
    protected Populacao<G, S> populacao;

    protected S melhorSer;

    protected Long tempoDeExecucao;

    protected Integer contadorDeIteracoes = 0;
    protected Integer contadorSemMelhoras = 0;
    private Integer maxIteracoes;

    /**
     * Construtor padrão.
     *
     * Construtor padrão, para montagem do algoritmo por estágios – através do
     * uso das funções <i>set</i>.
     *
     * <p>
     * Cada componente do algoritmo deve ser atribuído antes da execução do
     * mesmo, sendo obrigatórios uma população inicial e um ambiente. Os demais
     * componentes (operadores), devem ser incluídos se exigidos pela função
     * {@link #iteracao()}.
     * </p>
     *
     * @since 1.0
     *
     * @see #setPopulacao(inteligenciaComputacional.evolucioria.Populacao)
     * @see #setAmbiente(inteligenciaComputacional.evolucioria.Ambiente)
     * @see
     * #setGerador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Gerador)
     * @see
     * #setRecombinador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Recombinador)
     * @see
     * #setMutador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Mutador)
     * @see
     * #setSeletor(inteligenciaComputacional.evolucioria.algoritmo.operadores.Seletor)
     */
    public AlgoritmoEvolucionario() {
        this.maxIteracoes = Integer.MAX_VALUE;
        this.nome = "Não atribuido";
    }

    /**
     * Construtor.
     *
     * <p>
     * Construtor para fazer a atribuição inicial do ambiente e da população.
     * </p>
     *
     * <p>
     * Cada componente do algoritmo deve ser atribuído antes da execução do
     * mesmo, sendo obrigatórios uma população inicial e um ambiente. Os demais
     * componentes (operadores), devem ser incluídos se exigidos pela função
     * {@link #iteracao()}.
     * </p>
     *
     * @since 1.0
     * @param ambiente Ambiente para o algoritmo.
     * @param populacao População inicial.
     *
     * @see #setPopulacao(inteligenciaComputacional.evolucioria.Populacao)
     * @see #setAmbiente(inteligenciaComputacional.evolucioria.Ambiente)
     * @see
     * #setGerador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Gerador)
     * @see
     * #setRecombinador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Recombinador)
     * @see
     * #setMutador(inteligenciaComputacional.evolucioria.algoritmo.operadores.Mutador)
     * @see
     * #setSeletor(inteligenciaComputacional.evolucioria.algoritmo.operadores.Seletor)
     */
    public AlgoritmoEvolucionario(Ambiente<G, S> ambiente, Populacao<G, S> populacao) {
        this.maxIteracoes = Integer.MAX_VALUE;
        this.nome = "Não atribuido";
        this.ambiente = ambiente;
        this.populacao = populacao;
    }

    /**
     * Define uma iteração.
     *
     * <p>
     * Algoritmos serão baseados em iterações, executando-as até que a condição
     * de parada seja atingida.
     * </p>
     * <p>
     * Dessa forma, para cada algoritmo a ser definido, essa função deve ser
     * implementada, pois nela será descrito o comportamento esperado, bem como
     * o uso e relacionamento entre os operadores.
     * </p>
     *
     * @since 1.0
     *
     * @see #run()
     * @see #terminou()
     */
    public abstract void iteracao();

    /**
     * Condição de parada.
     *
     * Determina quando o algoritmo atinge sua iteração final, devendo tal
     * condição ser definida para cada algoritmo que se pretende implementar, se
     * o número de iterações não for uma condição suficiente.
     *
     * O número máximo de iterações já é uma condição levada em conta, logo não
     * precisa ser reavaliada aqui.
     *
     * @since 1.0
     * @return
     * <ul>
     * <li>true: se o algoritmo atingiu a condição de parada;</li>
     * <li>false: caso contrário.</li>
     * </ul>
     *
     */
    public Boolean terminou() {
        return false;
    }

    /**
     * Executa o algoritmo.
     *
     * <p>
     * Realiza o processamento do algoritmo, verificando a condição de parada e
     * realizando a coleta dos dados fornecidos pelos métodos de acesso e
     * geração de relatórios.
     * </p>
     * <p>
     * Devido a coleta de dados, esse método implica em um tempo de execução
     * adicional, possivelmente significativo. Especialmente, no controle do
     * número de iterações sem melhora e memorização do melhor ser encontrado.
     * </p>
     * <p>
     * Tais informações dependem fortemente da estrutura de dados usada na
     * implementação da população – pela exigência de buscas pelo mais adapto. O
     * que pode ser insignificante em casos de estruturas ordenadas, como
     * arvores, tamanho reduzido da população e/ou custo elevado de avaliação.
     * </p>
     *
     * @since 1.0
     *
     * @see Populacao#getMelhor()
     * @see #iteracao()
     * @see #terminou()
     * @see #relatorio()
     */
    @Override
    public void run() {

        S melhorDaIteracao;
        S melhorDaIteracaoPassada;

        verificacaoInicial();
        ambiente.avalia(populacao);

        tempoDeExecucao = System.nanoTime();
        {
            inicia();
            melhorSer = populacao.getMelhor();
            melhorDaIteracaoPassada = melhorSer;

            while (!terminou() && getContadorDeIteracoes() < getMaxIteracoes()) {
                ++contadorDeIteracoes;
                iteracao();

                melhorDaIteracao = populacao.getMelhor();

                if (ambiente.compare(melhorDaIteracaoPassada, melhorDaIteracao) >= 0) {

                    contadorSemMelhoras++;

                } else {

                    contadorSemMelhoras = 0;

                    if (ambiente.compare(melhorDaIteracao, melhorSer) == 1) {
                        melhorSer = melhorDaIteracao;
                    }
                }

                melhorDaIteracaoPassada = melhorDaIteracao;

                populacao.stream().forEach(ser -> ser.envelhece());
            }
            finaliza();
        }
        tempoDeExecucao = System.nanoTime() - tempoDeExecucao;
    }

    /**
     * Verifica as condições necessárias para execução do algoritmo.
     *
     * <h3>Condições:</h3>
     * <ul>
     * <li>Ambiente definido;</li>
     * <li>População definida.</li>
     * </ul>
     *
     * @since 1.0
     */
    protected void verificacaoInicial() throws IllegalStateException {
        if (ambiente == null) {
            throw new IllegalStateException("Ambiente não definido: referencia nula.");
        }

        if (populacao == null) {
            throw new IllegalStateException("População não definida: referencia nula.");
        }
    }

    /**
     * Função a ser executada após o termino das iterações.
     *
     * <p>
     * A realização de uma busca local pode ser um exemplo de aplicação.
     * </p>
     * <p>
     * Essa função é considerada no calculo do tempo de execução.
     * </p>
     *
     * @since 1.0
     */
    protected void finaliza() {
    }

    /**
     * Função a ser executada antes das iterações.
     *
     * <p>
     * Essa função é considerada no calculo do tempo de execução.
     * </p>
     * <p>
     * Por padrão, essa função não possui instruções: devendo ser sobrescrita se
     * necessária.
     * </p>
     *
     * @since 1.0
     */
    protected void inicia() {
    }

    //Gets e set padrão---------------------------------------------------------
    /**
     * Retorna Tempo de execução (nSeg).
     *
     * Ao executar o algoritmo manterá o controle do tempo decorrido desde a
     * primeira iteração até que a condição de parada seja satisfeita.
     *
     * @since 1.0
     * @return Tempo de execução em nano segundos.
     *
     * @see #run()
     * @see #iteracao()
     * @see #terminou()
     */
    public Long getTempoDeExecucaoNano() {
        return tempoDeExecucao;
    }

    /**
     * Retorna Tempo de execução (Seg).
     *
     * Ao executar o algoritmo manterá o controle do tempo decorrido desde a
     * primeira iteração até que a condição de parada seja satisfeita.
     *
     * @since 1.0
     * @return Tempo de execução em segundos.
     *
     * @see #run()
     * @see #iteracao()
     * @see #terminou()
     */
    public Double getTempoDeExecucaoSeg() {
        return tempoDeExecucao / 1000000000.0;
    }

    /**
     * Retorna o melhor ser encontrado em todas as iterações.
     *
     * Ao executar o algoritmo manterá o controle do melhor ser desde a primeira
     * iteração até que a condição de parada seja satisfeita.
     *
     * @since 1.0
     * @return Ser mais adaptado.
     * @see #run()
     */
    public S getMelhorSer() {
        return melhorSer;
    }

    /**
     * Retorna o valor do contador de iterações.
     *
     * @since 1.0
     * @return Número de iterações executadas.
     */
    public Integer getContadorDeIteracoes() {
        return contadorDeIteracoes;
    }

    /**
     * Retorna o valor do contador de iterações sem melhoras.
     *
     * <p>
     * Durante a execução o algoritmo mantém o controle do número de iterações
     * realizadas desde a última melhoria.
     * </p>
     * <p>
     * O contador é incrementado a cada iteração em que o melhor ser que compõe
     * a população não possuir um grau de adaptação superior ao do melhor ser da
     * geração passada. Sendo zerado, caso contrário.
     * </p>
     *
     * @since 1.0
     * @return Número de iterações executadas sem melhorias.
     */
    public Integer getContadorSemMelhoras() {
        return contadorSemMelhoras;
    }

    /**
     * Retorna o operador de geração utilizado.
     *
     * @since 1.0
     * @return O operador de geração do algoritmo.
     */
    public Gerador<S> getGerador() {
        return gerador;
    }

    /**
     * Atribui um novo operador de geração ao algoritmo.
     *
     * Operadores podem ser trocados no decorrer das iterações.
     *
     *
     * @since 1.0
     * @param gerador Operador a ser atribuído.
     */
    public void setGerador(Gerador<S> gerador) {
        this.gerador = gerador;
        gerador.setAlgoritmo(this);
    }

    /**
     * Retorna o operador de mutação utilizado.
     *
     * @since 1.0
     * @return O operador de mutação do algoritmo.
     */
    public Mutador<S> getMutador() {
        return mutador;
    }

    /**
     * Atribui um novo operador de mutação ao algoritmo.
     *
     * Operadores podem ser trocados no decorrer das iterações.
     *
     * @since 1.0
     * @param mutador Operador a ser atribuído.
     */
    public void setMutador(Mutador<S> mutador) {
        this.mutador = mutador;
        mutador.setAlgoritmo(this);
    }

    /**
     * Retorna o operador de recombinação utilizado.
     *
     * @since 1.0
     * @return O operador de recombinação do algoritmo.
     */
    public Recombinador<G, S> getRecombinador() {
        return recombinador;
    }

    /**
     * Atribui um novo operador de recombinação ao algoritmo.
     *
     * Operadores podem ser trocados no decorrer das iterações.
     *
     * @since 1.0
     * @param recombinador Operador a ser atribuído.
     */
    public void setRecombinador(Recombinador<G, S> recombinador) {
        this.recombinador = recombinador;
        recombinador.setAlgoritmo(this);
    }

    /**
     * Retorna o operador de seleção utilizado.
     *
     * @since 1.0
     * @return O operador de seleção do algoritmo.
     */
    public Seletor<G, S> getSeletor() {
        return seletor;
    }

    /**
     * Atribui um novo operador de seleção ao algoritmo.
     *
     * Operadores podem ser trocados no decorrer das iterações.
     *
     * @since 1.0
     * @param seletor Operador a ser atribuído.
     */
    public void setSeletor(Seletor<G, S> seletor) {
        this.seletor = seletor;
        seletor.setAlgoritmo(this);
    }

    /**
     * Retorna o ambiente do algoritmo.
     *
     * Operadores podem ser trocados no decorrer das operações.
     *
     * @since 1.0
     * @return Ambiente.
     */
    public Ambiente<G, S> getAmbiente() {
        return ambiente;
    }

    /**
     * Atribui um novo ambiente ao algoritmo, sem avaliação dos seres.
     *
     * Método deve ser usado apenas para atribuição inicial do ambiente. Para
     * troca de ambientes use:
     * {@link #switchAmbiente(inteligenciaComputacional.evolucioria.Ambiente)}
     *
     * @since 1.0
     * @param ambiente
     *
     * @throws IllegalStateException Caso um ambiente já esteja definido.
     *
     * @see #switchAmbiente(inteligenciaComputacional.evolucioria.Ambiente)
     */
    public final void setAmbiente(Ambiente<G, S> ambiente) {
        if (this.ambiente != null) {
            throw new IllegalStateException("Ambiente já definido.");
        }
        this.ambiente = ambiente;
    }

    /**
     * Atribui um novo ambiente ao algoritmo, com reavaliação.
     *
     * O ambiente pode ser trocado no decorrer das iterações. Quando tal troca
     * ocorre, por esse método, todos os seres da população serão avaliados pelo
     * novo ambiente.
     *
     * @since 1.0
     * @param ambiente
     *
     * @see #setAmbiente(inteligenciaComputacional.evolucioria.Ambiente)
     */
    public final void switchAmbiente(Ambiente<G, S> ambiente) {
        this.ambiente = ambiente;
        ambiente.avalia(populacao);
    }

    /**
     * Retorna a população do algoritmo.
     *
     * @since 1.0
     * @return População do algoritmo.
     */
    public Populacao<G, S> getPopulacao() {
        return populacao;
    }

    /**
     * Atribui uma nova população ao algoritmo, sem avaliação dos seres.
     *
     * Método deve ser usado apenas para atribuição inicial da população. Para
     * troca de populações use:
     * {@link #switchPopulacao(inteligenciaComputacional.evolucioria.Populacao)}
     *
     * @since 1.0
     * @param populacao População a ser atribuída.
     *
     * @throws IllegalStateException Caso uma população já esteja definida.
     *
     * @see #switchPopulacao(inteligenciaComputacional.evolucioria.Populacao)
     */
    public void setPopulacao(Populacao<G, S> populacao) {
        if (this.populacao != null) {
            throw new IllegalStateException("População já definida.");
        }
        this.populacao = populacao;
    }

    /**
     * Recupera o número máximo de iterações.
     *
     * Valor padrão: Integer.MAX_VALUE
     *
     * @since 1.0
     * @return Número máximo de iterações.
     */
    public Integer getMaxIteracoes() {
        return maxIteracoes;
    }

    /**
     * Atribui um número máximo para iterações.
     *
     * Valor padrão: Integer.MAX_VALUE
     *
     * @since 1.0
     * @param maxIteracoes Número máximo de iterações.
     */
    public void setMaxIteracoes(Integer maxIteracoes) {
        if (maxIteracoes < 0) {
            throw new IllegalArgumentException("Número máximo de iterações menor que zero.");
        }
        this.maxIteracoes = maxIteracoes;
    }

    //Relatórios----------------------------------------------------------------
    /**
     * Realiza a compilação dos dados coletados durante a execução do algoritmo.
     *
     * Nessa coletânea serão retornados os seguintes dados(em formato textual):
     * <ul>
     * <li>Nome do algoritmo.</li>
     * <li>Numero de indivíduos na população.</li>
     * <li>Probabilidade de Mutação.</li>
     * <li>Probabilidade de Recombinação.</li>
     * <li>Tempo de execução, em segundos.</li>
     * <li>Número de Iterações.</li>
     * <li> Número de Iterações sem melhora.</li>
     * <li> Melhor solução.</li>
     * </ul>
     *
     * @since 1.0
     * @return Relatório básico de execução.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Algoritmo:\t" + this.nome + "\n");
        relatorio.append("População:\t" + populacao.size() + "\n");
        relatorio.append("Probabilidade de Mutação:\t" + getMutador().getProbabilidadeMutacao() + "\n");
        relatorio.append("Probabilidade de Recombinação:\t" + getRecombinador().getProbabilidadeDeRecombinacao() + "\n");

        relatorio.append("Tempo (seg):\t" + getTempoDeExecucaoSeg() + "\n");
        relatorio.append("Iterações:\t" + getContadorDeIteracoes() + "\n");
        relatorio.append("Iterações sem melhora:\t" + getContadorSemMelhoras() + "\n");

        relatorio.append("Média dos graus de adaptação:\t" + populacao.getMediaGraus() + "\n");
        relatorio.append("Número de seres distintos:\t" + populacao.getNdistintos() + "\n");

        relatorio.append("Melhor Solução:\t" + getMelhorSer() + "\n");

        return relatorio.toString();
    }
}
