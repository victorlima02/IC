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
package ic.populacional.seres.binarios.mutadores;

import ic.populacional.algoritmo.operadores.Mutador;
import ic.populacional.seres.binarios.LocusBinario;
import ic.populacional.seres.binarios.SerBinario;
import ic.populacional.utilidades.Aleatorios;

/**
 * Mutador para seres binários.
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <S> Classe dos Seres.
 */
public class MutadorBinario<S extends SerBinario> extends Mutador<S> {

    public MutadorBinario(double probabilidadeDeMutacao) {
        super(probabilidadeDeMutacao);
    }
    
    @Override
    public void muta(S ser) {
        bitFlipping(ser, 1.0/ser.getSize());
    }

    /**
     * bit-flipping.
     *
     * @param ser
     * @param probabilidadeDeFlip
     */
    public final static void bitFlipping(SerBinario ser, double probabilidadeDeFlip) {
        for (int i = 0; i < ser.getCaracteristicas().size(); i++) {
            LocusBinario locus = (LocusBinario) ser.getCaracteristica(i);
            if (Aleatorios.sorteioUniforme(probabilidadeDeFlip)) {
                locus.flip();
            }
        }
    }
}
