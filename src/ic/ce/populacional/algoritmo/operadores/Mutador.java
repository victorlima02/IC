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

import ic.ce.populacional.Ser;
import ic.ce.populacional.utilidades.Aleatorios;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Operador de mutação.
 *
 * <p>
 * Mutadores representam uma classe genérica cujo papel fundamental é prover uma
 * operação unária que forneça um novo ser, criado a partir de modificações
 * pontuais e aleatórias no individuo de entrada.
 * </p>
 * <p>
 * Por sua própria natureza, esse operador é dependente da representação e deve
 * ser implementado para cada estrutura concebida para formação de seres.
 * </p>
 *
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <S> Classe dos Seres.
 */
public abstract class Mutador<S extends Ser> extends Operador implements Consumer<S> {

    protected Double probabilidadeDeMutacao;

    public Mutador(Double probabilidadeDeMutacao) {
        setProbabilidadeMutacao(probabilidadeDeMutacao);
    }

    /**
     * Realiza a operação de mutação.
     *
     * <p>
     * Operação Unária.
     * </p>
     * <p>
     * Deve ser definida para adaptação ao algoritmo desejado, provendo uma
     * função que forneça um novo ser, criado a partir de modificações pontuais
     * e aleatórias no individuo de entrada.
     * </p>
     * <p>
     * Essa função deve ser executada em conjunto com {@link #sorteiaSeMuta(ic.populacional.Ser) }
     * que decidirá se a operação deve ser realizada.
     * </p>
     *
     * @since 1.0
     * @param origem Ser objeto da mutação.
     */
    public abstract void muta(S origem);

    /**
     * Realiza a operação de mutação: coleção.
     *
     * <p>
     * Aplica a operação de mutação a cada ser da coleção passada como
     * parâmetro.
     * </p>
     * <p>
     * Para cada ser, um sorteio será realizado pela função {@link #sorteiaSeMuta(ic.populacional.Ser) 
     * },em caso de valor verdadeiro, a operação de mutação é aplicada.
     * </p>
     * <p>
     * Por questões de desempenho, considera-se que {@link #muta(ic.populacional.Ser)
     * } constitua-se de uma função cujo tempo computacional para execução seja
     * elevado. Portanto, a coleção é usada para a geração de uma stream
     * paralelizada, onde múltiplas threads são criadas para mutar os seres
     * individualmente.
     * </p>
     *
     * @since 1.0
     * @param seres Seres para mutação.
     *
     * @see #muta(ic.populacional.Ser)
     * @see #sorteiaSeMuta(ic.populacional.Ser) 
     */
    public final void muta(Collection<S> seres) {
        seres.parallelStream().filter(this::sorteiaSeMuta).forEach(this::muta);
    }

    /**
     * Alternativa:{@link #muta(ic.populacional.Ser)}
     *
     * <p>
     * Método alternativo a {@link #muta(ic.populacional.Ser)}.
     * Realiza a chamada do método, para satisfazer a interface <code>Consumer</code>.
     * </p>
     *
     * @since 1.0
     *
     * @param ser Ser para mutação.
     *
     * @see #muta(ic.populacional.Ser)
     */
    @Override
    public final void accept(S ser) {
        muta(ser);
    }

    /**
     * Sorteia se aplica a mutação.
     *
     * <p>
     * Esse método deve ser usado antes da aplicação da operação de mutação, {@link #muta(ic.populacional.Ser)
     * }. Desse modo, um sorteio será realizado para decidir se a mesma deve ou
     * não ocorrer.
     * </p>
     * <p>
     * Por padrão, essa função utiliza de uma distribuição uniforme, onde a
     * probabilidade de sucesso é definida pelo construtor ou pelo método {@link #setProbabilidadeMutacao(Double)
     * }.
     * </p>
     *
     * <p>
     * Se sobrescrito, deve usar um gerador de números aleatórios seguro para
     * múltiplas Threads.
     * </p>
     *
     * <p>
     * O ser recebido como parâmetro, não é utilizado - por padrão.
     * </p>
     *
     * @since 1.0
     * @param ser Ser a ser mutado.
     * @return
     * <ul>
     * <li>true: se deve mutar;</li>
     * <li>false: caso contrario;</li>
     * </ul>
     *
     * @see #Mutador(Double)
     * @see #setProbabilidadeMutacao(Double)
     * @see Aleatorios#sorteioUniforme(double) 
     */
    public boolean sorteiaSeMuta(Ser ser) {
        return Aleatorios.sorteioUniforme(getProbabilidadeMutacao());
    }

    /**
     * Atribui uma probabilidade de mutação ao operador.
     *
     * <p>
     * Por padrão, sorteios são realizados em uma distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param probabilidadeMutacao Nova probabilidade de mutação
     *
     * @throws IllegalArgumentException Se a probabilidade de mutação estiver
     * fora de [0-1].
     *
     * @see #sorteiaSeMuta(ic.populacional.Ser)
     */
    public final void setProbabilidadeMutacao(Double probabilidadeMutacao)
            throws IllegalArgumentException {

        if (probabilidadeMutacao > 1 || probabilidadeMutacao < 0) {
            throw new IllegalArgumentException(
                    "Probabilida de mutação deve estar em [0,1].");
        }

        this.probabilidadeDeMutacao = probabilidadeMutacao;
    }

    /**
     * Recupera a probabilidade de mutação do operador.
     *
     * @since 1.0
     * @return A probabilidade de mutação.
     */
    public final Double getProbabilidadeMutacao() {
        return probabilidadeDeMutacao;
    }

}
