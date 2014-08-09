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
package ic.ce.seres.binarios;

import ic.ce.base.Caracteristica;
import ic.ce.seres.SerFixo;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerador de seres baseados em números binários.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerBinario<G extends Number & Comparable<G>> extends SerFixo<G> {

    public SerBinario(int ncaracteristicas) {
        super(ncaracteristicas);
    }

    public SerBinario(List<Boolean> caracteristicas) {
        super(caracteristicas.size());
        setCaracteristicas(booleanListToLocusList(caracteristicas));
    }
    
    public static List<Caracteristica<Integer>> booleanListToLocusList(List<Boolean> caracteristicas) {

        List<Caracteristica<Integer>> locus = new ArrayList(caracteristicas.size());

        caracteristicas.stream().forEach((bool) -> {
            locus.add(new LocusBinario(bool));
        });

        return locus;
    }

    public final static Integer bits2Int(List<Boolean> bits) {

        double valor = 0;
        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                valor += Math.pow(2, bits.size() - 1 - i);
            }
        }
        return (int) valor;
    }

    public final static Integer locusBinarios2Int(List<Caracteristica> bits) {

        double valor = 0;
        for (int i = 0; i < bits.size(); i++) {
            if (((LocusBinario)bits.get(i)).getBit()) {
                valor += Math.pow(2, bits.size() - 1 - i);
            }
        }
        return (int) valor;
    }
}
