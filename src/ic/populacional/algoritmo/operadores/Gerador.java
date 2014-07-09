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
package ic.populacional.algoritmo.operadores;

import ic.populacional.Ser;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Operador de geração.
 *
 * <p>
 * Geradores representam uma classe genérica cujo papel fundamental é prover uma
 * operação para criação de novos indivíduos - sem características atribuídas ou
 * geradas aleatoriamente.
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
public abstract class Gerador<S extends Ser> extends Operador implements Supplier<S> {

    
    /**
     * Retorna uma nova instância.
     *
     * <p>
     * Essa cópia deverá ser uma nova instância. E o método não deverá receber
     * parâmetros, pois o mesmo será usado e partes diversas da API que não
     * consideram sobrecargas.
     * </p>
     * <p>
     * O ser gerado deverá conter características iniciais, atribuídas por
     * operações aleatórias.
     * </p>
     *
     * @since 1.0
     * @return Nova instância de <code>Ser</code>.
     */
    public abstract S getAleatorio();

    /**
     * Retorna uma nova instância de ser, sem atribuição de características.
     *
     * <p>
     * Esse método deve gerar um ser, oriundo de um processamento mais leve que
     * {@link #getAleatorio()}.
     * </p>
     *
     * <p>
     * Enquanto {@link #getAleatorio()} visa a criação de um ser completo,
     * possivelmente com características aleatórias; esse método visa a geração
     * de uma instância para manipulação, possivelmente para um atribuição mais
     * elaborada de características; e.g, novo ser para receber o resultado de
     * uma recombinação (Motivação nesse caso seria puramente por desempenho).
     * </p>
     * <p>
     * Essa cópia deverá ser uma nova instância. E o método não deverá receber
     * parâmetros, pois o mesmo será usado e partes diversas da API, que não
     * consideram sobrecargas.
     * </p>
     *
     * @since 1.0
     * @return Nova instância de ser.
     */
    @Override
    public abstract S get();

    /**
     * Retorna n novas instâncias.
     *
     * <p>
     * Esse método usa da função {@link #get() } para criar as <i>n</i> novas
     * instâncias.
     * </p>
     *
     * @since 1.0
     * @param n Numero de instâncias necessárias.
     * @return Uma coleção(lista) de seres recém criados.
     *
     * @see #get()
     */
    public List<S> getN(int n) {
        List<S> novos = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            novos.add(get());
        }
        return novos;
    }

    /**
     * Retorna n novas instâncias geradas aleatoriamente.
     *
     * <p>
     * Esse método usa da função {@link #getAleatorio() } para criar as
     * <i>n</i> novas instâncias.
     * </p>
     *
     * @since 1.0
     * @param n Numero de instâncias necessárias.
     * @return Uma coleção(lista) de seres recém criados.
     *
     * @see #getAleatorio()
     */
    public List<S> getNAleatorios(int n) {
        List<S> novos = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            novos.add(getAleatorio());
        }
        return novos;
    }
}
