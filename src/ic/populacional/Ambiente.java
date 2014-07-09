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
 * @version 1.0
 */
package ic.populacional;

import java.util.Collection;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Essa classe visa criar uma abstração do conceito de ambiente em seu sentido
 * biológico - aplicado a problemas de CE.
 * </p>
 * <p>
 * Sendo dessa forma, uma entidade avaliadora para uma população, que quando
 * sujeita aos vários fenômenos ambientais, evoluirá no tempo.
 * </p>
 * <p>
 * Um ambiente conterá mecanismos que permitirão uma população a evoluir em
 * acordo com as pressões evolutivas impostas, implementadas na forma de métodos
 * em classes mais especializadas – Operadores- que a utilizaram para avaliar
 * soluções geradas.
 * </p>
 * <p>
 * De forma generalizada, derivações de <code>Ambiente</code> avaliarão
 * indivíduos em uma População, parametrizando seu percurso evolucionário ao
 * atribuir um grau de adaptação a cada um e provendo objetos da classe
 * <code>Comparator</code>, capazes de comprar seres em uma ordem crescente ou
 * decrescente em relação ao grau de adaptação.
 * </p>
 * <h3>Observação sobre populações:</h3>
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
 * espécie.</li>
 * <li>Uma população não pode se auto avaliar, bem como seus indivíduos. Uma vez
 * que indivíduos terão um grau de adaptação variado, flutuante, em função do
 * ambiente em que se encontrarão. </li>
 * </ul>
 *
 * <h3>Responsabilidades:</h3>
 * <ul>
 * <li>Avaliar seres;</li>
 * <li>Avaliar populações(ou coleções de seres);</li>
 * <li>Comparar seres em relação ao grau de adaptação.</li>
 * </ul>
 *
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
 * @see Populacao
 */
public abstract class Ambiente<G extends Number & Comparable<G>, S extends Ser<G>> implements Comparator<S> {

    private final Modo modo;
    private final Comparator<S> comparador;
    private final Comparator<S> comparadorInverso;

    /**
     * Construtor padrão: modo Maximização.
     *
     * @since 1.0
     */
    public Ambiente() {
        modo = Modo.MAXIMIZACAO;
        comparador = (S o1, S o2) -> compara(o1, o2);
        comparadorInverso = (S o1, S o2) -> compara(o2, o1);
    }

    /**
     * Construtor com definição do modo.
     *
     * @since 1.0
     * @param modo Modo de comparação: Maximixação/Minimização.
     */
    public Ambiente(Modo modo) {
        this.modo = modo;
        switch (modo) {
            case MINIMIZACAO:
                comparador = (S o1, S o2) -> compara(o2, o1);
                comparadorInverso = (S o1, S o2) -> compara(o1, o2);
                break;
            case MAXIMIZACAO:
            default:
                comparador = (S o1, S o2) -> compara(o1, o2);
                comparadorInverso = (S o1, S o2) -> compara(o2, o1);
        }
    }

    /**
     * Defini o modo de comparação entre os seres.
     *
     * <p>
     * Pelo modo, o ambiente será capaz de identificar a ordem correta dos seres
     * ao compará-los.</p>
     * <p>
     * Para problemas de minimização, um ser com menor “grau de adaptação” será
     * melhor e posto após os com maiores graus – em uma ordem crescente.
     * Contudo, para maximização (modo padrão), seres com graus maiores serão
     * considerados melhores e quando ordenados viram após os com menores graus.
     * </p>
     *
     */
    public enum Modo {

        MAXIMIZACAO, MINIMIZACAO
    };

    /**
     * Função de Fitness: Avalia um ser atribuindo um grau de adaptação.
     *
     * <p>
     * Atua como função objetivo em algoritmos de otimização e permite que seres
     * sejam classificados em função do seu grau de adaptação, nesse ambiente.
     * </p>
     *
     * <p>
     * Essa função <b>deve chamar</b>
     * {@link Ser#setGrauDeAdaptacao(java.lang.Number) }
     * uma vez que seu valor de retorno for calculado para que o mesmo seja
     * retido - se essa for a intenção. Essa pode ser chamada após o
     * retorno,contudo.
     * </p>
     *
     * @since 1.0
     * @param individuo Ser a ser avaliado.
     * @return Grau de adaptação.
     *
     * @see Ser#setGrauDeAdaptacao(java.lang.Number)
     *
     */
    public abstract G avalia(S individuo);

    /**
     * Avalia uma coleção de seres.
     *
     * <p>
     * Avalia uma coleção de seres pela chamada repetitiva de {@link #avalia(ic.populacional.Ser)
     * }.
     * </p>
     * <p>
     * Por questões de desempenho, considera-se que {@link #avalia(ic.populacional.Ser)
     * } constitua-se de uma função cujo tempo computacional para execução seja
     * elevado. Portanto, a coleção é usada para a geração de uma stream
     * paralelizada, onde múltiplas threads são criadas para avaliar os seres
     * individualmente.
     * </p>
     *
     * <p>
     * Essa função atribui a cada ser na coleção um grau de adaptação por meio
     * da função: {@link Ser#setGrauDeAdaptacao(java.lang.Number)}
     * </p>
     *
     * @since 1.0
     * @param seres Coleção a ser avaliada.
     *
     * @see #avalia(ic.populacional.Ser)
     * @see Ser#setGrauDeAdaptacao(java.lang.Number)
     */
    public void avalia(Collection<? extends S> seres) {
        seres.parallelStream().filter(ser -> !ser.isAvaliadoPor(this)).forEach((ser) -> ser.setGrauDeAdaptacao(this));
    }

    /**
     * Compara graus de adaptação.
     *
     * <p>
     * Compara dois seres, possibilitando sua ordenação quanto a adaptação.
     * </p>
     *
     * @param ser1 Ser a ser comparado pelo ambiente.
     * @param ser2 Segundo Ser a ser comparado pelo ambiente.
     *
     * @return <ul>
     * <li>Positivo se ser1 for melhor adptado.</li>
     * <li>Negativo se ser2 for melhor adptado.</li>
     * <li>0 se forem igualmente adaptados.</li>
     * </ul>
     *
     * @since 1.0
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compare(S ser1, S ser2) {
        return comparador.compare(ser1, ser2);
    }

    /**
     * Retorna um comparador para a ordem invertida quanto ao grau de adaptação.
     *
     * @since 1.0
     * @return Comparator para ordem invertida.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    public Comparator<S> comparadorInverso() {
        return comparadorInverso;
    }

    /**
     * Compara graus de adaptação: na ordem crescente.
     *
     * <p>
     * Compara dois seres, possibilitando sua ordenação quanto a adaptação.
     * </p>
     *
     * @param ser1 Ser a ser comparado pelo ambiente.
     * @param ser2 Segundo Ser a ser comparado pelo ambiente.
     *
     * @return <ul>
     * <li>Positivo se ser1 for melhor adptado.</li>
     * <li>Negativo se ser2 for melhor adptado.</li>
     * <li>0 se forem igualmente adaptados.</li>
     * </ul>
     *
     * @since 1.0
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    private int compara(S ser1, S ser2) {

        G grau1 = (ser1.isAvaliadoPor(this)) ? ser1.getGrauDeAdaptacao() : this.avalia(ser1);
        G grau2 = (ser2.isAvaliadoPor(this)) ? ser2.getGrauDeAdaptacao() : this.avalia(ser2);

        Integer comparacao = grau1.compareTo(grau2);

        if (comparacao == 0) {
            if (ser1 == ser2) {
                return 0;
            } else {   
                return ser1.getId().compareTo(ser2.getId());
            }
        }
        
        return comparacao;
    }

    /**
     * Acesso ao modo de comparação.
     *
     * @since 1.0
     * @return Modo de comparação.
     */
    public Modo getModo() {
        return modo;
    }

    /**
     * Método de para compilação de dados de uma população em formato de string.
     *
     * @since 1.0
     * @param populacao
     * @return String com dados da população.
     *
     * @deprecated
     * @see Populacao#toString()
     */
    @Deprecated
    public String toString(Populacao populacao) {

        StringBuilder ambiente = new StringBuilder();

        /* Padrão = Inicio da linha = modo (?m) */
        Pattern pattern = Pattern.compile("(?m)(^)");

        Matcher matcher = pattern.matcher(populacao.toString());

        ambiente.append("Ambiente: [\n");

        /* Substitui todo "inicio de linha" por uma tabulação. */
        ambiente.append(matcher.replaceAll("\t"));

        ambiente.append("]\n");

        return ambiente.toString();

    }
}