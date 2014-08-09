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
package ic.ce.seres.reais;

import ic.ce.base.Caracteristica;
import static java.lang.Math.nextDown;
import java.util.Objects;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class LocusReal extends Caracteristica<Double> implements Comparable<LocusReal> {

    private Double locus;
    private final Double limiteInferior;
    private final Double limiteSuperior;

    /**
     * Construtor com valor, "ilimitado".
     *
     * <h3>Limites impostos pelos valores:</h3>
     * <p>
     * Limite inferior, inclusive: <code>-Double.MAX_VALUE</code>.
     * </p>
     * <p>
     * Limite superior, exclusive: <code>Double.MAX_VALUE</code>.
     * </p>
     *
     * @since 1.0
     * @param locus Valor inicial do locus.
     */
    public LocusReal(Double locus) {
        this(-Double.MAX_VALUE, Double.MAX_VALUE);
        setValor(locus);
    }

    /**
     * Construtor sem valor.
     *
     * @since 1.0
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, exclusive.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o limite superior for menor que o limite inferior. </li>
     * </ul>
     */
    protected LocusReal(Double limiteInferior, Double limiteSuperior) {
        if (limiteSuperior < limiteInferior) {
            throw new IllegalArgumentException("Limite superior é menor que o limite inferior");
        }
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    /**
     * Construtor com valor.
     *
     * @since 1.0
     * @param locus Valor inicial do locus.
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, exclusive.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o limite superior for menor que o limite inferior. </li>
     * </ul>
     */
    public LocusReal(Double locus, Double limiteInferior, Double limiteSuperior) {
        this(limiteInferior, limiteSuperior);
        setValor(locus);
    }

    /**
     * Atribui novo valor ao locus.
     *
     * Valores null são permitidos.
     *
     * @since 1.0
     * @param locus Novo valor.
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o valor estiver fora dos limites. </li>
     * </ul>
     */
    public final void setValor(Double locus) {
        if (locus != null && (locus < getLimiteInferior() || locus >= getLimiteSuperior())) {
            throw new IllegalArgumentException("Valor do locus fora dos limites.");
        }
        this.locus = locus;
    }

    /**
     * Atribui novo valor ao locus, o número mais próximo do limite superior.
     *
     * @since 1.0
     */
    public final void maximixa() {
        this.locus = nextDown(getLimiteSuperior());
    }
    
     /**
     * Atribui novo valor ao locus, o número limite inferior.
     *
     * @since 1.0
     */
    public final void minimiza() {
        this.locus = getLimiteInferior();
    }

    /**
     * Recupera o limite inferior do locus.
     *
     * @since 1.0
     * @return Limite inferior, inclusive.
     */
    public final Double getLimiteInferior() {
        return limiteInferior;
    }

    /**
     * Recupera o limite superior do locus.
     *
     * @since 1.0
     * @return Limite superior, exclusive.
     */
    public final Double getLimiteSuperior() {
        return limiteSuperior;
    }

    @Override
    public Double getValor() {
        return locus;
    }

    @Override
    public LocusReal copia() {
        return new LocusReal(locus, getLimiteInferior(), getLimiteSuperior());
    }

    @Override
    public int compareTo(LocusReal o) {
        return locus.compareTo(o.locus);
    }

    @Override
    public boolean equals(Object outro) {
        if (!(outro instanceof LocusReal)) {
            return false;
        }
        return locus.equals(((LocusReal) outro).locus);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.locus);
        return hash;
    }

    @Override
    public String toString() {
        return locus.toString();
    }
}
