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

/**
 * @author Victor Soares
 * @version 1.0
 *
 * @param <V> Tipo da representação numérica da característica.
 */
public abstract class Caracteristica<V extends Number> {

    private Ser dono;

    /**
     * Acessa ser vinculado a característica.
     *
     * @since 1.0
     * @return Ser que porta essa característica.
     *
     * @see #setSer(ic.populacional.Ser)
     */
    public Ser getSer() {
        return dono;
    }

    /**
     * Vincula uma característica a um ser.
     *
     * <p>
     * Pode ser usada uma única vez por instância. Impedindo que seres
     * compartilhem uma mesma característica, evitando problemas de modificação
     * acidental em conjunto de seres, especialmente, em operações de mutação.
     * </p>
     * <p>
     * Vinculação pode ser repetida se aplicada ao mesmo ser.
     * </p>
     *
     * @param dono Ser que portará essa característica.
     *
     * @throws IllegalStateException
     * <ul>
     * <li>Se a característica já estiver atribuída a outro ser.</li>
     * </ul>
     *
     * @see Ser#setCaracteristica(java.lang.Integer, ic.populacional.Caracteristica) 
     */
    public void setSer(Ser dono) {
        if (this.dono != null && this.dono != dono) {
            throw new IllegalStateException("Característica já atribuída a outro ser.");
        }
        this.dono = dono;
    }

    /**
     * Calcula uma representação (expressão) numérica para essa característica.
     *
     * <p>
     * Características podem conter uma série de informações sobre si mesma,
     * constituindo o globalmente o genótipo dos seres que as contem. Porém, uma
     * expressão numérica única, sumarizando os efeitos mais relevantes dessa
     * unidade de formação do ser em um único número, é necessária tanto para
     * criar-se um modelo do fenótipo, quanto para propósitos de processamento.
     * </p>
     *
     * @since 1.0
     * @return Expressão numérica da característica.
     *
     */
    public abstract V getValor();


    /**
     * Retorna uma cópia da característica.
     *
     * <p>
     * Essa cópia deverá ser uma nova instância, tal que
     * <code>iquals(copia)</code> seja verdadeiro. Difere de clone, para evitar
     * conflito na API, e resultados inesperados.
     * </p>
     *
     * <b>Deve gerar nova instância</b>
     * <b>Não deve copiar o vinculo com um ser.</b>
     *
     * @see Object#equals(java.lang.Object)
     * @return Uma copia desta característica.
     */
    public abstract Caracteristica copia();

    @Override
    public String toString() {
        return getValor().toString();
    }

}
