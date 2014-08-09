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
package ic.ce.seres.inteiros.mutadores;

import ic.ce.base.algoritmo.operadores.Mutador;
import ic.ce.seres.inteiros.LocusInteiro;
import ic.ce.seres.inteiros.SerInteiro;
import ic.ce.base.utilidades.Aleatorios;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Operador de mutação para seres baseados em representações inteiras.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <S> Classe dos Seres.
 */
public class MutadorInteiro<S extends SerInteiro> extends Mutador<S> {

    public MutadorInteiro(double probabilidadeDeMutacao) {
        super(probabilidadeDeMutacao);
    }

    @Override
    public void muta(S ser) {
        randomResetting(ser, 1.0 / ser.getSize());
    }

    /**
     * Muatação: Random Resetting.
     *
     * <p>
     * Para cada locus do vetor de características do ser, um sorteio é
     * realizado para avaliar se o locus deve sofrer mutação. Caso seja, o locus
     * receberá um valor sorteado aleatoriamente, dentro dos limites impostos.
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
     * @since 1.0
     * @param ser para mutação
     * @param probabilidadeDeReset Probabilidade do locus sofrer mutação.
     */
    public final static void randomResetting(SerInteiro ser, Double probabilidadeDeReset) {
        ser.getCaracteristicas().parallelStream()
                .filter(caracteristica -> Aleatorios.sorteioUniforme(probabilidadeDeReset))
                .forEach((caracteristica) -> {
                    ((LocusInteiro) caracteristica).setRepresentacaoNumerica(ThreadLocalRandom.current().nextInt(ser.getLimiteInferior(), ser.getLimiteSuperior() + 1));
                });
    }

}
