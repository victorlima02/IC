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
package ic.ce.populacional.seres.permutacoes;

import ic.ce.populacional.Caracteristica;
import java.util.Objects;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class LocusPermutacao extends Caracteristica<Integer> implements Comparable<LocusPermutacao> {

    Integer locus;

    public LocusPermutacao(int i) {
        this.locus = i;
    }

    @Override
    public Integer getValor() {
        return locus;
    }

    public void setRepresentacaoNumerica(int x) {
        locus = x;
    }

    @Override
    public LocusPermutacao copia() {
        return new LocusPermutacao(locus);
    }

    @Override
    public int compareTo(LocusPermutacao o) {
        return locus.compareTo(o.locus);
    }

    @Override
    public boolean equals(Object outro) {
        if (!(outro instanceof LocusPermutacao)) {
            return false;
        }
        return locus.equals(((LocusPermutacao) outro).locus);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.locus);
        return hash;
    }

    @Override
    public String toString() {
        return locus.toString();
    }
}