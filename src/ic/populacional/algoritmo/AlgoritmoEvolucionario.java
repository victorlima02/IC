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
package ic.populacional.algoritmo;

import ic.populacional.Ambiente;
import ic.populacional.Ser;
import ic.populacional.algoritmo.operadores.Gerador;
import ic.populacional.algoritmo.operadores.Mutador;
import java.time.Duration;
import java.time.Instant;

/**
 * Classe base para Algoritmos Evolucionários.
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
    private Gerador<S> gerador;
    private Mutador<S> mutador;

    private Ambiente<G, S> ambiente;

    private S melhorSer;

    private Instant inicio, fim;

    private Integer contadorDeIteracoes = 0;
    private Integer contadorSemMelhoras = 0;
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
        setMaxIteracoes(Integer.MAX_VALUE);
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
    public AlgoritmoEvolucionario(Ambiente<G, S> ambiente) {
        super();
        setAmbiente(ambiente);
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
    public abstract void run();

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
     * Verifica as condições necessárias para execução do algoritmo.
     *
     * <h3>Condições:</h3>
     * <ul>
     * <li>Ambiente definido.</li>
     * </ul>
     *
     * @since 1.0
     */
    protected void verificacaoInicial() throws IllegalStateException {
        if (getAmbiente() == null) {
            throw new IllegalStateException("Ambiente não definido: referencia nula.");
        }
    }

    /**
     * Grava melhor ser encontrado.
     *
     * @param melhorSer
     * @since 1.0
     */
    protected final void setMelhorSer(S melhorSer) {
        this.melhorSer = melhorSer;
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
    public final S getMelhorSer() {
        return melhorSer;
    }

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
     * ocorre, por esse método, todos os seres - em uma população ou não - devem
     * ser avaliados pelo novo ambiente.
     *
     * @since 1.0
     * @param ambiente
     *
     * @see #setAmbiente(inteligenciaComputacional.evolucioria.Ambiente)
     */
    protected void switchAmbiente(Ambiente<G, S> ambiente) {
        this.ambiente = ambiente;
    }

    /**
     * Atribui um número máximo para iterações.
     *
     * Valor padrão: Integer.MAX_VALUE
     *
     * @since 1.0
     * @param maxIteracoes Número máximo de iterações.
     */
    public final void setMaxIteracoes(Integer maxIteracoes) {
        if (maxIteracoes < 0) {
            throw new IllegalArgumentException("Número máximo de iterações menor que zero.");
        }
        this.maxIteracoes = maxIteracoes;
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
     * Incrementa o valor do contador de iterações.
     *
     * @since 1.0
     * @return Número de iterações executadas(valor atualizado).
     */
    public Integer incrementaContadorDeIteracoes() {
        return ++contadorDeIteracoes;
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
     * Incrementa o valor do contador de iterações sem melhoras.
     *
     * @since 1.0
     * @return Número de iterações executadas sem melhorias(valor atualizado).
     */
    protected Integer incrementaContadorSemMelhoras() {
        return ++contadorSemMelhoras;
    }

    /**
     * Zera o valor do contador de iterações sem melhoras.
     *
     * @since 1.0
     * @return Número de iterações executadas sem melhorias(valor atualizado).
     */
    protected Integer zeraContadorSemMelhoras() {
        return contadorSemMelhoras = 0;
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
     * Inicial contador do tempo de execução.
     *
     * @since 1.0
     */
    protected final void timerStart() {
        inicio = Instant.now();
    }

    /**
     * Finaliza contador do tempo de execução.
     *
     * @since 1.0
     */
    protected final void timerStop() {
        fim = Instant.now();
    }

    /**
     * Acesso ao contador do tempo.
     *
     * @since 1.0
     * @return Duração da execução.
     */
    public final Duration getTempoDeExcucao() {
        return Duration.between(inicio, fim);
    }

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
        return getTempoDeExcucao().toNanos();
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
        return getTempoDeExcucao().toNanos() / 1000000000.0;
    }

}
