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

import ic.ce.base.Caracteristica;

/**
 * Característica para seres binários.
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class LocusBinario extends Caracteristica<Integer> {

    private Boolean set;

    public LocusBinario() {
        this.set = null;
    }

    public LocusBinario(Boolean set) {
        this.set = set;
    }

    public final Boolean getBit() {
        return set;
    }

    public final void setBit(Boolean bit) {
        this.set = bit;
    }

    /**
     * Realiza a inversão de valor de uma característica binária.
     *
     * @since 1.0
     */
    public final void flip() {
        setBit(!getBit());
    }

    @Override
    public LocusBinario copia() {
        return new LocusBinario(this.getBit());
    }

    @Override
    public Integer getValor() {
        return (set) ? 1 : 0;
    }
}
