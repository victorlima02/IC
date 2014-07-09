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
package ic.populacional.seres;

import ic.populacional.Caracteristica;
import ic.populacional.Ser;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Victor de Lima Soares
 */
public abstract class SerFixo<G  extends Number & Comparable<G>> extends Ser<G> {
   
    /**
     * Construtor.
     *
     * <p>
     * Características não serão atribuídas.
     * </p>
     *
     * @since 1.0
     * @param ncaracteristicas Número inicial de características.
     * 
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Número de locus for menor que zero.
     * </li>
     * </ul>
     */
    public SerFixo(int ncaracteristicas) {
        if (ncaracteristicas <= 0) {
            throw new IllegalArgumentException("Número de locus deve ser maior que zero.");
        }
        this.caracteristicas = Arrays.asList(new Caracteristica[ncaracteristicas]);
    }

    /**
     * Construtor.
     *
     * <p>
     * Por padrão, seres criados por esse método terão tamanho fixo.
     * </p>
     *
     * <p>
     * <code>Características</code> não serão copiadas de forma a criar novas
     * instâncias.
     * </p>
     *
     * @param caracteristicas Lista de características a serem atribuídas ao
     * ser.
     * @since 1.0
     */
    public SerFixo(int ncaracteristicas, List<? extends Caracteristica> caracteristicas) {
        this.caracteristicas = Arrays.asList(new Caracteristica[caracteristicas.size()]);
        setCaracteristicas(caracteristicas);
    }

}
