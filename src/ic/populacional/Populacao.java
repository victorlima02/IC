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
package ic.populacional;

import static java.lang.Math.pow;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Abstração do conceito: “população”; conjunto de seres.
 *
 * <p>
 * Uma população agrupa um conjunto de seres que se relacionam entre si por
 * manter características compatíveis para reprodução e competição (comparação),
 * em um dado ambiente. No modelo proposto, uma população constitui-se de uma
 * estrutura de dados que compõe um dos blocos fundamentais para composição de
 * um algoritmo, que a mantém e evolui – em uma relação de agregação.
 * </p>
 *
 * <p>
 * Dessa forma, busca-se nessa classe uma abstração ao conceito de população
 * como um conjunto de indivíduos de uma mesma espécie, derivada de Ser. Para
 * tal objetivo, a classe abstrata população, derivada de
 * <code>Collection&lt;Ser&gt;</code>, prove uma interface padrão para acesso e
 * manipulação de seres.
 * </p>
 *
 * <p>
 * A classe foi concebida como abstrata pois constata-se que algoritmos
 * diferentes possuem necessidades próprias em relação a estrutura de dados
 * contendo os seres; v.g., algoritmos que requerem buscas frequentes pelos
 * seres melhor qualificados podem ter um ganho de desempenho considerável ao
 * utilizar uma população contida em um estrutura ordenada, i.e., árvores
 * binárias.
 * </p>
 * <p>
 * A classe foi implementada como genérica para garantir a existência de seres
 * (instâncias de classes derivadas de <code>Ser</code>) de um único tipo; uma
 * única classe, e suas derivadas, serão usadas em uma população. Deste modo,
 * interpreta-se que seres de uma única espécie, e suas subespécies, conviverão
 * em uma população.
 * </p>
 *
 * <h3>Suposições básicas:</h3>
 * <ul>
 * <li>Seres de uma população pertencem a uma única família de classes derivadas
 * hierarquicamente de <code>Ser</code>; mantendo, portanto, a característica de
 * espécie. </li>
 * <li>Uma <code>população</code> não pode se auto avaliar, bem como seus
 * indivíduos. Uma vez que indivíduos terão um grau de adaptação variado,
 * flutuante, em função do ambiente em que se encontrarão; </li>
 * <li>O ambiente avaliará os seres ao inseri-los, no interior de cada método de
 * inserção;</li>
 * <li><b>Seres são imutáveis; uma vez avaliados o grau de adaptação não deve
 * mudar.</b> Quando necessário, o ser deve ser removido e inserido
 * novamente,facilitando a implementação de estruturas ordenadas;</li>
 * <li><b>Trocas de ambiente devem requerer uma reavaliação dos seres.</b></li>
 * <li><b>Número máximo de seres é controlado pelo algoritmo, sendo a população
 * encarregada de armazenar o valor – flexibilizando a atuação dos operadores.
 * Suposição que pode ser removida em derivações mais rígidas, dada a estrutura
 * de dados apropriada.</b></li>
 * </ul>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 * @param <S> Classe dos Seres.
 *
 * @see Ser
 * @see Ambiente
 */
public abstract class Populacao<G extends Number & Comparable<G>, S extends Ser<G>> implements Collection<S> {

    protected Ambiente<G, S> ambiente;
    private Integer maxIndividuos;
    protected Collection<S> seres;

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
     * @see #setMaxIndividuos(int)
     */
    public Populacao(Ambiente<G, S> ambiente, int maxIndividuos) {
        if (ambiente == null) {
            throw new IllegalArgumentException("Ambiente não pode ser uma referência nula.");
        }
        this.ambiente = ambiente;
        setMaxIndividuos(maxIndividuos);
    }

    /**
     * Registra o número máximo de indivíduos nessa população.
     *
     * <p>
     * Esse parâmetro pode ser usado para controlar barreiras impostas a
     * algoritmos com finalidade algorítmica ou por escassez de recursos.
     * </p>
     * <p>
     * Admitem-se violações temporárias.
     * </p>
     * <p>
     * <b>O número máximo de seres é controlado pelo algoritmo, sendo a
     * população encarregada de armazenar o valor – flexibilizando a atuação dos
     * operadores. Suposição que pode ser removida em derivações mais rígidas,
     * dada a estrutura de dados apropriada.</b>
     * </p>
     *
     * @param maxIndividuos Número máximo de indivíduos na população.
     * <ul>
     * <li>Deve ser um número natural maior que zero;</li>
     * <li>O valor zero indica a ausência de limite.</li>
     * </ul>
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o número máximo de indivíduos for menor que zero.</li>
     * </ul>
     */
    private void setMaxIndividuos(int maxIndividuos) throws IllegalArgumentException {
        if (maxIndividuos < 0) {
            throw new IllegalArgumentException("Número máximo de individuos menor que zero.");
        }
        this.maxIndividuos = maxIndividuos;
    }

    /**
     * Esvazia a população e adiciona a ela a coleção passada como parâmetro.
     *
     * <p>
     * Cada ser adicionado será avaliado pelo ambiente da população.
     * </p>
     *
     * @since 1.0
     * @param seres Novos seres da população.
     * @return
     * <ul>
     * <li>true: se a população foi modificada;</li>
     * <li>false: caso contrário.</li>
     * </ul>
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     * @see Ambiente#avalia(java.util.Collection)
     */
    public boolean setIndividuos(Collection<? extends S> seres) {
        this.seres.clear();
        return addAll(seres);
    }

    /**
     * Adiciona uma coleção de seres a população.
     *
     * <p>
     * Cada ser adicionado será avaliado pelo ambiente da população.
     * </p>
     *
     * @since 1.0
     * @param seres Novos seres a população.
     * @return
     * <ul>
     * <li>true: se a população foi modificada;</li>
     * <li>false: caso contrário.</li>
     * </ul>
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     * @see Ambiente#avalia(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends S> seres) {
        ambiente.avalia(seres);
        return this.seres.addAll(seres);
    }

    /**
     * Adiciona um ser a população.
     *
     * <p>
     * O ser adicionado será avaliado pelo ambiente da população.
     * </p>
     *
     * @since 1.0
     *
     * @param ser Novo ser a população.
     * @return
     * <ul>
     * <li>true: se a população foi modificada;</li>
     * <li>false: caso contrário.</li>
     * </ul>
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     * @see Ambiente#avalia(java.util.Collection)
     *
     * @throws NullPointerException
     * <ul>
     * <li>Se o elemento for uma referência nula.</li>
     * </ul>
     * @throws IllegalStateException
     * <ul>
     * <li>Se o elemento não puder ser inserido por restrições da estrutura de
     * dados: coleção.</li>
     * </ul>
     */
    @Override
    public boolean add(S ser) {
        ambiente.avalia(ser);
        return seres.add(ser);
    }

    /**
     * Retorna o número máximo de seres nessa população.
     *
     * <p>
     * Esse número pode ou não ser imposto pela estrutura de dados da população.
     * Por padrão, esse limite não é imposto para permitir violações ocasionais
     * em cada iteração, devendo o algoritmo impô-lo ao fim de cada ciclo – por
     * meio da lógica dos operadores. Usa-se esse método para acesso ao valor.
     * </p>
     *
     * @since 1.0
     *
     * @return
     * <ul>
     * <li>O número máximo de indivíduos, maior que zero;</li>
     * <li>Zero significa ausência de limite.</li>
     * </ul>
     */
    public int getMaxIndividuos() {
        return maxIndividuos;
    }

    /**
     * Método para acessos baseado em índices.
     *
     * <p>
     * Esse método, foi implementado para permitir acesso baseado em índices.
     * </p>
     * <p>
     * Esse método usa de <i>iterators</i> para percorrer a coleção até que o
     * índice seja atingido. Dessa forma, ele prove uma forma de acesso
     * sequencial, sem garantia de ordenação – baseando-se no retorno de {@link Collection#iterator()
     * }.
     * </p>
     * <p>
     * A sequência de acesso, ordenada ou não, será baseada na ordem provida
     * pelo iterator da estrutura de dados usada para implementação da coleção
     * de seres. Assim, se o iterador prover um acesso de forma ordenada, o
     * acesso baseado em índices também será.
     * </p>
     *
     * <p>
     * <b>
     * Caso a estrutura de dados utilizada também forneça acesso randômico,
     * recomenda-se sobrescrever esse método para ganho de desempenho, tirando
     * proveito dessa característica.
     * </b></p>
     *
     * @since 1.0
     * @param indice Posição de acesso.
     * @return Ser na posição "indice".
     *
     * @throws IndexOutOfBoundsException
     * <ul>
     * <li>Se o índice for negativo;</li>
     * <li>Se o índice ultrapassar o número de seres.</li>
     * </ul>
     *
     */
    public S get(int indice) {
        if (indice >= seres.size()) {
            throw new IndexOutOfBoundsException("Índice fora dos limites da população.");
        }
        S ser = null;
        Iterator<S> iterator = seres.iterator();
        int i;
        for (i = 0; i <= indice; i++) {
            ser = iterator.next();
        }
        return ser;
    }

    /**
     * Retorna o número de seres na população.
     *
     * <p>
     * Algumas coleções diferenciam o número de elementos da capacidade delas,
     * esse método retorna o número de elementos e deve ser sobrescrito em caso
     * de diferenciação do método {@link Collection#size() }
     * </p>
     *
     * @since 1.0
     *
     * @return O número de indivíduos.
     */
    @Override
    public int size() {
        return seres.size();
    }

    /**
     * Retorna o ser mais adaptado da população.
     *
     * <p>
     * Retorna um único ser com o maior grau de adaptação, ou menor grau caso o
     * ambiente esteja em modo de minimização.
     * </p>
     *
     * @since 1.0
     *
     * @return Ser mais apto.
     */
    public abstract S getMelhor();

    /**
     * Retorna o grau de adaptação do ser mais adaptado na população.
     *
     * @since 1.0
     *
     * @return Grau de adaptação do melhor ser.
     */
    public G getMelhorGrau() {
        return getMelhor().getGrauDeAdaptacao();
    }

    /**
     * Cria uma lista com os <i>n</i> melhores seres.
     *
     * <p>
     * Instancia uma nova lista contendo os <i>n</i> melhores seres, possuindo
     * esses os melhores níveis de adaptação, maiores graus quando o ambiente
     * estives em modo de maximização e menores em caso de minimização.
     * </p>
     *
     * @since 1.0
     *
     * @param n Número de seres desejados.
     * @return Lista com os n melhores seres seres.
     */
    public abstract List<S> getNMelhores(int n);

    /**
     * Recupera a soma dos graus de adaptação.
     *
     * @since 1.0
     * @return Soma dos graus de adaptação.
     */
    public Double getSomaGraus() {
        return seres.stream().mapToDouble((ser) -> ser.getGrauDeAdaptacao().doubleValue()).sum();
    }

    /**
     * Recupera a média dos graus de adaptação.
     *
     * @since 1.0
     * @return Média dos graus de adaptação.
     */
    public Double getMediaGraus() {
        return seres.stream().mapToDouble((ser) -> ser.getGrauDeAdaptacao().doubleValue()).average().getAsDouble();
    }

    /**
     * Recupera o desvio padrão amostral dos graus de adaptação.
     *
     * @since 1.0
     * @return Desvio padrão amostral.
     */
    public Double getDesvioPadraoGraus() {
        Integer n = size();
        Double media = getMediaGraus();
        Double desvio;

        Double sq = seres.stream().mapToDouble((ser) -> ser.getGrauDeAdaptacao().doubleValue())
                .reduce(0, (x, y) -> {
                    return x + pow((y - media), 2);
                });

        desvio = Math.sqrt(sq / (n - 1));
        return desvio;
    }

    /**
     * Recupera o número de elementos distintos.
     *
     * @since 1.0
     * @return Número de elementos distintos.
     *
     * @see Ser#equals(java.lang.Object)
     */
    public long getNdistintos() {
        return seres.stream().distinct().count();
    }

    /**
     * Verifica se a população está vazia.
     *
     * @since 1.0
     * @return
     * <ul>
     * <li>true: se a população estiver vazia: sem elementos;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     */
    @Override
    public boolean isEmpty() {
        return seres.isEmpty();
    }

    /**
     * Verifica se o ser existe na população.
     *
     * <p>
     * Lógica de casamento: (o==null ? e==null : o.equals(e))
     * </p>
     *
     * @since 1.0
     * @param ser Ser procurado.
     * @return
     * <ul>
     * <li>true: se o ser for encontrado;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se o objeto passado como parâmetro não for um ser compatível com a
     * população.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir elementos nulos e o
     * objeto for nulo.</li>
     * </ul>
     *
     * @see Ser#equals(java.lang.Object)
     * @see Collection#equals(java.lang.Object)
     */
    @Override
    public boolean contains(Object ser) {
        return seres.contains(ser);
    }

    /**
     * Verifica a existência de todos os elementos na coleção parâmetro.
     *
     * <p>
     * Lógica de casamento: (o==null ? e==null : o.equals(e))
     * </p>
     *
     * @since 1.0
     *
     * @param seres Seres procurados.
     * @return
     * <ul>
     * <li>true: se todos os seres forem encontrados;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se os objetos passados como parâmetro,na coleção, não forem seres
     * compatíveis com a população.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir elementos nulos e
     * algum objeto da coleção for nulo.</li>
     * </ul>
     *
     * @see Ser#equals(java.lang.Object)
     * @see #contains(java.lang.Object)
     */
    @Override
    public boolean containsAll(Collection<?> seres) {
        return this.seres.contains(seres);
    }

    /**
     * Remove um elemento igual ao parâmetro da população.
     *
     * <p>
     * Lógica de casamento: (o==null ? e==null : o.equals(e))
     * </p>
     *
     * @since 1.0
     * @param ser Ser para remoção.
     * @return
     * <ul>
     * <li>true: se o ser for encontrado e removido;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se o objeto passado como parâmetro não for um ser compatível com a
     * população.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir elementos nulos e o
     * objeto for nulo.</li>
     * </ul>
     * @throws UnsupportedOperationException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir tal operação.</li>
     * </ul>
     *
     * @see Ser#equals(java.lang.Object)
     */
    @Override
    public boolean remove(Object ser) {
        return seres.remove(ser);
    }

    /**
     * Remove todos elementos iguais a algum elemento na coleção parâmetro.
     *
     * <p>
     * Lógica de casamento: (o==null ? e==null : o.equals(e))
     * </p>
     *
     * <p>
     * Todos os elementos iguais a algum elemento na coleção passada como
     * parâmetro serão removidos da população, de modo que a coleção e a
     * população não terão mais interseções.
     * </p>
     *
     * @since 1.0
     * @param seres Seres para remoção.
     * @return
     * <ul>
     * <li>true: se algum ser for encontrado e removido;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se os objetos passados como parâmetro,na coleção, não forem seres
     * compatíveis com a população.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir elementos nulos e
     * algum objeto da coleção for nulo.</li>
     * </ul>
     * @throws UnsupportedOperationException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir tal operação.</li>
     * </ul>
     *
     * @see Ser#equals(java.lang.Object)
     * @see #remove(java.lang.Object)
     */
    @Override
    public boolean removeAll(Collection<?> seres) {
        return this.seres.removeAll(seres);
    }

    /**
     * Remove da população todos os elementos não pertencentes a coleção
     * parâmetro.
     *
     * <p>
     * Lógica de casamento: (o==null ? e==null : o.equals(e))
     * </p>
     *
     * @since 1.0
     * @param seres Seres excluídos da remoção.
     * @return
     * <ul>
     * <li>true: se a população for modificada;</li>
     * <li>false: caso contrário;</li>
     * </ul>
     *
     * @throws ClassCastException
     * <ul>
     * <li>Se os objetos passados como parâmetro,na coleção, não forem seres
     * compatíveis com a população.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir elementos nulos e
     * algum objeto da coleção for nulo.</li>
     * </ul>
     * @throws UnsupportedOperationException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir tal operação.</li>
     * </ul>
     *
     * @see Ser#equals(java.lang.Object)
     * @see #remove(java.lang.Object)
     */
    @Override
    public boolean retainAll(Collection<?> seres) {
        return this.seres.retainAll(seres);
    }

    /**
     * Exclui todos os seres da população.
     *
     * @since 1.0
     * @throws UnsupportedOperationException
     * <ul>
     * <li>Se a estrutura de dados da população não permitir tal operação.</li>
     * </ul>
     */
    @Override
    public void clear() {
        seres.clear();
    }

    /**
     * Retorna um iterator para os seres na população.
     *
     * @since 1.0
     * <p>
     * A sequência de acesso, ordenada ou não, será baseada na ordem provida
     * pelo iterator da estrutura de dados usada para implementação da coleção
     * de seres. Assim, se a coleção prover um iterador para acesso de forma
     * ordenada, esse método o proverá.
     * </p>
     * @return Iterator para os seres.
     *
     * @see Collection#iterator()
     * @see #get(int)
     */
    @Override
    public Iterator<S> iterator() {
        return seres.iterator();
    }

    /**
     * Converte a população em um array.
     *
     * <p>
     * Realiza a instanciação de um novo array contendo os seres da população.
     * </p>
     * <p>
     * A ordem é determinada pelo iterator provido pela população.
     * </p>
     *
     * @since 1.0
     * @return Array contendo os seres da população.
     *
     * @see #iterator()
     */
    @Override
    public S[] toArray() {
        return (S[]) seres.toArray();
    }

    /**
     * Converte a população em um array.
     *
     * <p>
     * Armazena os seres da população no array passado como parâmetro. Se a
     * capacidade do mesmo não for suficiente, realiza a instanciação de um novo
     * array contendo os seres da população.
     * </p>
     * <p>
     * A ordem é determinada pelo iterator provido pela população.
     * </p>
     *
     * @since 1.0
     * @param <S> Tipo dos seres no array desejado.
     *
     * @return Array contendo os seres da população.
     *
     * @throws NullPointerException
     * <ul>
     * <li>Se o array for uma referência nula.</li>
     * </ul>
     * @throws ArrayStoreException
     * <ul>
     * <li>Se o tipo,em tempo de execução, do array passado como parâmetro não
     * condizer com o tipo de todos os elementos da população.</li>
     * </ul>
     *
     * @see #iterator()
     * @see #toArray()
     */
    @Override
    public <S> S[] toArray(S[] a) {
        return seres.toArray(a);
    }

    /**
     * Fornece uma stream de seres.
     *
     * @since 1.0
     * @return Stream de seres na população.
     */
    @Override
    public Stream<S> stream() {
        return seres.stream();
    }

    /**
     * Fornece uma stream paralelizável de seres.
     *
     * @since 1.0
     * @return Stream de seres na população.
     */
    @Override
    public Stream<S> parallelStream() {
        return seres.parallelStream();
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();

        print.append("População:\n[\n");
        print.append(String.format("\tTamanho: %d Individuos\n", seres.size()));

        seres.stream().forEach((individuo) -> {
            print.append("\t" + individuo + "\n");
        });

        print.append("\n]\n");

        return print.toString();
    }

    /**
     * Recupera uma string contendo os dados estatísticos da população.
     * 
     * <ul>
     * <li>Contagem de elementos;</li>
     * <li>Contagem de elementos distintos;</li>
     * <li>Máximo grau de adaptação;</li>
     * <li>Média dos graus de adaptação;</li>
     * <li>Desvio padrão amostral dos graus de adaptação;</li>
     * <li>Soma total dos graus de adaptação.</li>
     * </ul>
     * @since 1.0
     * @return Dados estatísticos em uma string.
     */
    public String estatisticas() {
        StringBuilder dados = new StringBuilder();

        dados.append(String.format("Contagem:\t%d\n", size()));
        dados.append(String.format("Distintos:\t%d\n", getNdistintos()));
        dados.append(String.format("Máximo:\t%.2f\n", getMelhorGrau().doubleValue()));
        dados.append(String.format("Média:\t%.2f\n", getMediaGraus()));
        dados.append(String.format("Desvio padrão amostral:\t%.2f\n", getDesvioPadraoGraus()));
        dados.append(String.format("Soma total:\t%.2f\n", getSomaGraus()));
        
        return dados.toString();
    }
}
