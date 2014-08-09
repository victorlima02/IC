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
package ic.ce.seres.binarios;

import ic.ce.base.algoritmo.operadores.Gerador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Victor de Lima Soares
 * @param <S> Classe dos Seres.
 */
public abstract class GeradorBinario<S extends SerBinario> extends Gerador<S> {

    /**
     * Retorna <i>n</i> Booleans aleatórios em uma lista.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param nbits Número de bits desejado.
     * @return Lista de Booleans sorteada.
     */
    public final static List<Boolean> boleanListUniforme(int nbits) {

        List<Boolean> lista = new ArrayList<>();
        for (int i = 0; i < nbits; i++) {
            lista.add(ThreadLocalRandom.current().nextBoolean());
        }
        return lista;
    }

    /**
     * Retorna <i>n</i> características binárias aleatórios em uma lista.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param nbits Número de bits desejado.
     * @return Lista de características sorteada.
     */
    public final static List<LocusBinario> LocusListUniforme(int nbits) {

        List<LocusBinario> lista = new ArrayList<>(nbits);
        for (int i = 0; i < nbits; i++) {
            lista.add(new LocusBinario(ThreadLocalRandom.current().nextBoolean()));
        }
        return lista;
    }

}
