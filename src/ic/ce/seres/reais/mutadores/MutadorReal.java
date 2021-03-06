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
package ic.ce.seres.reais.mutadores;

import ic.ce.base.algoritmo.operadores.Mutador;
import ic.ce.seres.reais.LocusReal;
import ic.ce.seres.reais.SerReal;
import ic.ce.base.utilidades.Aleatorios;

/**
 * Operador de mutação para seres baseados em representações de ponto flutuante.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <S> Classe dos Seres.
 */
public class MutadorReal<S extends SerReal> extends Mutador<S> {

    public MutadorReal(double probabilidadeDeMutacao) {
        super(probabilidadeDeMutacao);
    }

    @Override
    public void muta(S ser) {
        uniformMutation(ser, 1.0 / ser.getSize());
    }

    /**
     * Mutação: Mutação Uniforme.
     *
     * <p>
     * Para cada locus do vetor de características do ser, um sorteio é
     * realizado para avaliar se o locus deve sofrer mutação. Caso seja, o locus
     * receberá um valor sorteado aleatoriamente, dentro dos limites impostos -
     * pelo locus a ser mutado.
     * </p>
     * <p>
     * Sorteio para cada locus: distribuição uniforme.
     * </p>
     * <p>
     * Sorteio do novo valor: distribuição uniforme.
     * </p>
     * <p>
     * Geração segura para multithread.
     * </p>
     *
     *
     * @since 1.0
     * @param ser para mutação
     * @param probabilidadeDeMutacao Probabilidade do locus sofrer mutação.
     */
    public final static void uniformMutation(SerReal ser, double probabilidadeDeMutacao) {
        ser.getCaracteristicas().parallelStream()
                .filter(c -> Aleatorios.sorteioUniforme(probabilidadeDeMutacao))
                .forEach(locus -> {
                    LocusReal locusReal = (LocusReal) locus;
                    locusReal.setValor(Aleatorios.getUniformeDouble(locusReal.getLimiteInferior(), locusReal.getLimiteSuperior()));
                }
                );
    }
}
