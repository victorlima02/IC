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
import ic.populacional.algoritmo.operadores.Recombinador;
import ic.populacional.algoritmo.operadores.Seletor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe base para Algoritmo Populacionais.
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
public abstract class AlgoritmoPopulacional<G extends Number & Comparable<G>, S extends Ser<G>> extends AlgoritmoEvolucionario<G, S> {

    private Recombinador<G, S> recombinador;
    private Seletor<G, S> seletor;
    private Populacao<G, S> populacao;

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
    public AlgoritmoPopulacional() {
        super();
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
    public AlgoritmoPopulacional(Ambiente<G, S> ambiente, Populacao<G, S> populacao) {
        super(ambiente);
        setPopulacao(populacao);
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
        getAmbiente().avalia(populacao);

        timerStart();
        {
            inicia();
            setMelhorSer(getPopulacao().getMelhor());
            melhorDaIteracaoPassada = getMelhorSer();

            while (!terminou() && getContadorDeIteracoes() < getMaxIteracoes()) {

                iteracao();
                incrementaContadorDeIteracoes();

                melhorDaIteracao = getPopulacao().getMelhor();

                if (getAmbiente().compare(melhorDaIteracaoPassada.getGrauDeAdaptacao(), melhorDaIteracao.getGrauDeAdaptacao()) >= 0) {

                    incrementaContadorDeIteracoes();

                } else {

                    zeraContadorSemMelhoras();

                    if (getAmbiente().compare(melhorDaIteracao.getGrauDeAdaptacao(), getMelhorSer().getGrauDeAdaptacao()) > 0) {
                        setMelhorSer(melhorDaIteracao);
                    }
                }

                melhorDaIteracaoPassada = melhorDaIteracao;

                getPopulacao().stream().forEach(ser -> ser.envelhece());
            }
            finaliza();
        }
        timerStop();
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
    @Override
    protected void verificacaoInicial() throws IllegalStateException {
        super.verificacaoInicial();

        if (getPopulacao() == null) {
            throw new IllegalStateException("População não definida: referencia nula.");
        }
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
    @Override
    public final void switchAmbiente(Ambiente<G, S> ambiente) {
        super.switchAmbiente(ambiente);
        getAmbiente().avalia(populacao);
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
     * Método deve ser usado apenas para atribuição inicial da população.
     *
     * @since 1.0
     * @param populacao População a ser atribuída.
     *
     * @throws IllegalStateException Caso uma população já esteja definida.
     *
     */
    public final void setPopulacao(Populacao<G, S> populacao) throws IllegalStateException {
        if (this.populacao != null) {
            throw new IllegalStateException("População já definida.");
        }
        this.populacao = populacao;
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
        relatorio.append("\tProbabilidade de Mutação:\t" + getMutador().getProbabilidadeMutacao() + "\n");
        relatorio.append("\tProbabilidade de Recombinação:\t" + getRecombinador().getProbabilidadeDeRecombinacao() + "\n");

        relatorio.append("\tTempo (seg):\t" + getTempoDeExecucaoSeg() + "\n");
        relatorio.append("\tIterações:\t" + getContadorDeIteracoes() + "\n");
        relatorio.append("\tIterações sem melhora:\t" + getContadorSemMelhoras() + "\n");

        /* Padrão = Inicio da linha = modo (?m) */
        Pattern pattern = Pattern.compile("(?m)(^)");
        Matcher matcher = pattern.matcher(getPopulacao().estatisticas());

        relatorio.append("População:\n");
        relatorio.append(matcher.replaceAll("\t") + "\n");

        relatorio.append("Melhor Solução:\t" + getMelhorSer() + "\n");

        return relatorio.toString();
    }
}
