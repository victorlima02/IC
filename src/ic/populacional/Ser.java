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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Abstração do conceito: “ser vivo”.
 * <p>
 * Essa classe provê uma interface padronizada para tratamento de seres,
 * constituindo a base de uma hierarquia que pode ser interpretada pela noção de
 * espécie, e suas subespécies.
 * </p>
 * <p>
 * Uma classe que estende <code>Ser</code> conterá um vetor de tamanho variado,
 * ou não, de características que serão usadas por políticas de adaptação,
 * reprodução e avaliação.
 * </p>
 * <p>
 * Cada característica poderá ser de qualquer tipo que compõem o conjunto
 * formado pelas implementações da interface <code>Característica</code>, essa
 * podendo conter informações passíveis de compilação em representação numérica.
 * </p>
 * <p>
 * Possibilitando-se dessa forma a composição de seres com características
 * binárias, reais ou mescladas, uma vez que armazena-se um vetor de
 * características; sendo obrigatória apenas a implementação dessa interface
 * para cada classe de seus elementos (explorando-se o polimorfismo como
 * mecanismo de flexibilidade e diversidade).
 * </p>
 * <h3>
 * Sinônimos de Ser:
 * </h3>
 * <ul>
 * <li>Genótipo;</li>
 * <li>Individuo;</li>
 * <li>Cromossomo;</li>
 * <li>Elemento do espaço de busca.</li>
 * </ul>
 *
 * <h3>Suposições básicas:</h3>
 * <ul>
 * <li><b>Seres são imutáveis</b>; uma vez avaliados o grau de adaptação e suas
 * características não devem mudar. Quando necessário, o ser deve ser removido
 * da população,se em uma estiver, e inserido novamente;</li>
 * <li>Por padrão, seres serão considerados iguais se tiverem um mesmo grau de
 * adaptação: comparáveis entre si.</li>
 * </ul>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 *
 */
public abstract class Ser<G extends Number & Comparable<G>> implements Iterable<Caracteristica> {

    private static Long contadorDeSeres = 0L; 
    
    /**
     * Grau de adaptação a um ambiente.
     *
     * Um valor numérico atribuído ao ser por um ambiente uma vez avaliado por
     * ele.
     *
     * @since 1.0
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     */
    private G grauDeAdaptacao;

    /**
     * Ambiente que avaliou esse ser, atribuindo-o um grau de avaliação.
     * 
     * @since 1.0
     * @see #setGrauDeAdaptacao(ic.populacional.Ambiente) 
     */
    private Ambiente avaliadoPor;

    /**
     * Idade do ser, em número de gerações.
     *
     * @since 1.0
     */
    private Integer idade;
    
    /**
     * Número de identificação do ser, atribuído na criação.
     * 
     * @since 1.0
     */
    private final Long id;

    /**
     * Vetor de características (genoma).
     *
     * Lista de características que compõem o ser, não necessariamente do mesmo
     * tipo.
     *
     * @since 1.0
     */
    protected List<Caracteristica> caracteristicas;

    /**
     * Construtor padrão.
     *
     * <p>
     * Atribui o valor <i>null</i> ao grau de avaliação. Indicando que o ser
     * ainda esta em formação e não foi avaliado. Uma vez finalizado, um ser não
     * deve ser modificado.
     * </p>
     *
     * @since 1.0
     */
    public Ser() {
        this.idade = 0;
        this.grauDeAdaptacao = null;
        this.avaliadoPor = null;
        id=incrementaContadorDeSeres();
    }

    /**
     * Recupera a idade do ser.
     *
     * @since 1.0
     * @return Número de gerações.
     */
    public final Integer getIdade() {
        return idade;
    }

    /**
     * Atribui uma nova idade ao ser.
     *
     * @since 1.0
     * @param idade
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se a idade passada como parâmetro for menor que zero.</li>
     * </ul>
     */
    public final void setIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException("Idade do ser deve ser maior ou igual a zero.");
        }
        this.idade = idade;
    }

    /**
     * Incrementa a idade do ser e retorna seu novo valor.
     *
     * @since 1.0
     * @return Número de gerações.
     */
    public final Integer envelhece() {
        return ++idade;
    }

    /**
     * Recupera o ID de um ser.
     * 
     * @since 1.0
     * @return ID do ser.
     */
    public Long getId() {
        return id;
    }

    /**
     * Realiza a atribuição de uma característica ao ser, por índice.
     * <p>
     * Características não serão copiadas de forma a criar novas instâncias.
     * </p>
     *
     * @param indice A posição de atribuição.
     * @param caracteristica A característica a ser atribuída ao ser.
     *
     * @since 1.0
     * @see Caracteristica#getSer()
     */
    public final void setCaracteristica(Integer indice, Caracteristica caracteristica) {
        if (grauDeAdaptacao != null) {
            throw new IllegalStateException("Ser já avaliado não pode ser modificado.");
        }
        caracteristica.setSer(this);
        this.caracteristicas.set(indice, caracteristica);
    }

    /**
     * Realiza a atribuição das características do ao ser.
     * <p>
     * Características não serão copiadas de forma a criar novas instâncias.
     * </p>
     *
     * @param caracteristicas As características a serem atribuídas ao ser.
     *
     * @since 1.0
     */
    public final void setCaracteristicas(List<? extends Caracteristica> caracteristicas) {
        for (int i = 0; i < caracteristicas.size(); i++) {
            setCaracteristica(i, caracteristicas.get(i));
        }
    }

    /**
     * Realiza a atribuição de uma característica ao ser, por índice.
     * <p>
     * Características serão copiadas de forma a criar novas instâncias.
     * </p>
     *
     * @param indice A posição de atribuição.
     * @param origem A característica a ser atribuída ao ser.
     *
     * @since 1.0
     *
     * @see Caracteristica#copia()
     */
    public final void setCaracteristicaCopia(Integer indice, Caracteristica origem) {
        setCaracteristica(indice, origem.copia());
    }

    /**
     * Realiza a atribuição de características ao ser, por intervalos e por
     * referência.
     *
     * <p>
     * Características não serão copiadas de forma a criar novas instâncias.
     * </p>
     *
     * @param origem Lista de características de origem.
     * @param indice Posição de inserção.
     */
    public final void setCaracteristicas(Integer indice, List<? extends Caracteristica> origem) {

        for (Caracteristica locus : origem) {
            setCaracteristica(indice++, locus);
        }
    }

    /**
     * Realiza a atribuição de características ao ser, e por copia.
     *
     * @since 1.0
     * @param indice Posição de inserção.
     * @param origem Lista de características de origem.
     *
     * @see Caracteristica#copia()
     */
    public final void setCaracteristicasCopia(Integer indice, List<? extends Caracteristica> origem) {

        for (Caracteristica locus : origem) {
            setCaracteristicaCopia(indice++, locus);
        }
    }

    /**
     * Realiza a atribuição de características ao ser, por intervalos e por
     * copia.
     *
     * @since 1.0
     * @param indice Posição de inserção.
     * @param origem Lista de características de origem.
     * @param inicio Índice da lista de origem para o início da copia.
     * @param fim Índice da lista de origem para o fim da copia,
     * <b>exclusivo</b>.
     *
     * @see Caracteristica#copia()
     */
    public final void setCaracteristicasCopia(Integer indice, List<? extends Caracteristica> origem, int inicio, int fim) {

        for (Caracteristica locus : origem.subList(inicio, fim)) {
            setCaracteristicaCopia(indice++, locus);
        }
    }

    /**
     * Método de acesso às características.
     *
     * <b>Referência à instância da lista de referências: usar com cuidado -
     * qualquer modificação feita afetará o ser</b>.
     *
     * <p>
     * A lista de características será imodificável.
     * </p>
     *
     * @return O vetor de características deste objeto.
     * @since 1.0
     */
    public final List<Caracteristica> getCaracteristicas() {
        return Collections.unmodifiableList(caracteristicas);
    }

    /**
     * Método de acesso às características: uma copia será criada.
     *
     * Caso a característica tenha sido implementada com cuidado para que o
     * método de copia fosse seguro, esse método também o será.
     *
     * @return O vetor de características deste ser.
     * @since 1.0
     *
     * @see Caracteristica#copia()
     */
    public final List<Caracteristica> getCaracteristicasCopia() {
        List<Caracteristica> cromossomo = new ArrayList<>(this.getSize());

        caracteristicas.stream().forEach((locus) -> {
            cromossomo.add(locus.copia());
        });
        return cromossomo;
    }

    /**
     * Método de acesso às características.
     *
     * <b>Referência à instância na lista de referências : usar com cuidado -
     * qualquer modificação feita afetará o ser</b>.
     *
     * @param indice Índice para acesso.
     * @return A característica na posição "indice" na coleção de
     * características deste objeto.
     * @since 1.0
     */
    public final Caracteristica getCaracteristica(int indice) {
        return caracteristicas.get(indice);
    }

    /**
     * Retorna o tamanho do conjunto de características.
     *
     * @return O tamanho do conjunto de características.
     */
    public final int getSize() {
        return caracteristicas.size();
    }

    /**
     * Verifica se dois seres são compatíveis para reprodução.
     *
     * @param par
     * @return <ul>
     * <li>True: Se pertencerem a uma mesma espécie.</li>
     * <li>False: Se não pertencerem a uma mesma espécie.</li>
     * </ul>
     */
    public final boolean isCompativel(Ser par) {
        return (par.getEspecie().equals(this.getEspecie()));
    }

    /**
     * Verifica se dois ou mais seres são compatíveis para reprodução.
     *
     * @param pares Pares para teste de compatibilidade.
     * @return <ul>
     * <li>True: Se pertencerem a uma mesma espécie.</li>
     * <li>False: Se não pertencerem a uma mesma espécie.</li>
     * </ul>
     */
    public final boolean isCompativel(Ser... pares) {
        for (Ser par : pares) {
            if (!isCompativel(par)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Atribui um grau de adaptação ao ser.
     *
     * @param grauDeAdaptacao O grau de adaptação para atribuição.
     * @param avaliadoPor Ambiente que avaliou o ser.
     * @since 1.0
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     */
    public final void setGrauDeAdaptacao(G grauDeAdaptacao, Ambiente avaliadoPor) {
        this.grauDeAdaptacao = grauDeAdaptacao;
        setAvaliadoPor(avaliadoPor);
    }

    /**
     * Atribui um grau de adaptação ao ser.
     *
     * @param avaliadoPor Ambiente que avaliou o ser.
     * @since 1.0
     *
     * @see Ambiente#avalia(ic.populacional.Ser)
     */
    public final void setGrauDeAdaptacao(Ambiente avaliadoPor) {
        this.grauDeAdaptacao = (G) avaliadoPor.avalia(this);
        setAvaliadoPor(avaliadoPor);
    }

    /**
     * Método de acesso ao grau de adaptação do ser.
     *
     * @return O grau de adaptação.
     * @since 1.0
     */
    public final G getGrauDeAdaptacao() {
        return grauDeAdaptacao;
    }

    /**
     * Acesso ao ambiente que avaliou e atribuiu um grau de aptidão ao ser.
     *
     * @since 1.0
     * @return ambiente avaliador.
     */
    public final Ambiente getAvaliadoPor() {
        return avaliadoPor;
    }

    /**
     * Confere se o ser tem um grau de aptidão atribuído pelo ambiente.
     *
     * @since 1.0
     * @param ambiente Ambiente de interesse.
     * @return
     * <ul>
     * <li>true: se o ser foi avaliado por esse ambiente.</li>
     * <li>false: se o ser foi avaliado por esse ambiente.</li>
     * </ul>
     */
    public final Boolean isAvaliadoPor(Ambiente ambiente) {
        if(getAvaliadoPor()==null) return false;
        return getAvaliadoPor().equals(ambiente);
    }

    /**
     * Atribui um ambiente avaliador.
     *
     * Esse método permite que um ser mantenha uma referencia ao ambiente que o
     * avaliou e gravou o grau de avaliação nesse ser. Dessa forma uma ambiente
     * saberá se o ser já foi por ele avaliado, não repetindo essa operação.
     *
     * @since 1.0
     * @param avaliadoPor Ambiente que avaliou o ser.
     */
    private void setAvaliadoPor(Ambiente avaliadoPor) {
        if (avaliadoPor == null) {
            throw new NullPointerException("Avaliador é uma referencia nula");
        }
        this.avaliadoPor = avaliadoPor;
    }

    /**
     * Verifica o nome da espécie de um ser.
     * <p>
     * Busca na hierarquia qual classe que deriva <b>diretamente</b> de Ser.
     * </p>
     *
     * @return Nome da espécie.
     */
    public final String getEspecie() {
        Class<?> especie = this.getClass();

        while (especie.getSuperclass() != Ser.class) {
            especie = especie.getSuperclass();
        }

        return (especie.getName());
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        print.append("[ ");

        print.append("(" + getIdade() + ") ");
        String grau = (getGrauDeAdaptacao() == null) ? "null" : getGrauDeAdaptacao().toString();
        print.append("{" + grau + "} ");

        caracteristicas.stream().forEach((valor) -> {
            String valorLiteral = (valor == null) ? "null" : valor.toString();
            print.append(valorLiteral + " ");
        });

        print.append("]");
        return print.toString();
    }

    @Override
    public final Iterator<Caracteristica> iterator() {
        return caracteristicas.iterator();
    }

    /**
     * Define se uma instância é igual a outra.
     *
     * <p>
     * Deve ser consistente com o <code>comparator</code> se usado em coleções
     * ordenadas. E em caso de uso dos seres em <i>Sets</i>, se a intenção for
     * não permitir que uma mesma instância seja adicionada multiplas vezes,
     * usar {@link Object.equals(Object)}, se for impedir seres iguais, como com
     * mesmo grau de aptidão, esse método deve ser implementado de acordo.
     * </p>
     * <p>
     * Padrão: {@link Object.equals(Object)}
     * </p>
     *
     * @param o
     * @return
     * <ul>
     * <li>true: se as referências apontam para o mesmo objeto;</li>
     * <li>false: caso contrário.</li>
     * </ul>
     */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public final boolean equals(Object o) {
        return this==o;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.grauDeAdaptacao);
        hash = 67 * hash + Objects.hashCode(this.idade);
        return hash;
    }

    /**
     * Método de acesso ao número de seres já criados.
     * 
     * @since 1.0
     * @return Número de seres já criados.
     */
    public static Long getContadorDeSeres() {
        return contadorDeSeres;
    }

    /**
     * Incrementa o contador de seres criados.
     * 
     * @since 1.0
     */
    private static Long incrementaContadorDeSeres() {
        return Ser.contadorDeSeres++;
    }
    
}
