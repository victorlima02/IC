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
package ic.ce.populacional.seres.permutacoes.muradores;

import ic.ce.populacional.Caracteristica;
import ic.ce.populacional.Ser;
import ic.ce.populacional.algoritmo.operadores.Mutador;
import ic.ce.populacional.seres.permutacoes.SerPermutacao;
import ic.ce.populacional.utilidades.IndiceAleatorio;
import java.util.List;

/**
 * Operador de mutação para seres baseados em permutação.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 *
 * @param <S> Classe dos Seres.
 */
public abstract class MutadorPermutacao<S extends SerPermutacao> extends Mutador<S> {

    public MutadorPermutacao(double probabilidadeDeMutacao) {
        super(probabilidadeDeMutacao);
    }

    /**
     * Realiza a troca (swap) de posições de duas características em um ser,
     * selecionadas aleatoriamente.
     *
     *
     * <p>
     * Sorteio uniforme e sem repetição.
     * </p>
     *
     * <p>
     * Característica I(Sorteada) será igual a característica J(Sorteada) atual.
     * e vice-versa.
     * </p>
     *
     * @since 1.0
     * @param origem Ser objeto da mutação.
     *
     */
    public final static void swapMutation(Ser origem) {
        List<Integer> indices = IndiceAleatorio.getUniforme(origem, 2);
        swap(origem, indices.get(0), indices.get(1));
    }

    /**
     * Realiza a troca (swap) de posições de duas características em um ser.
     *
     * <p>
     * Característica I será igual a característica J atual. e vice-versa.
     * </p>
     *
     * @since 1.0
     * @param origem Ser objeto da mutação.
     *
     * @param indiceI Índice i.
     * @param indiceJ Índice j.
     *
     */
    public final static void swap(Ser origem, int indiceI, int indiceJ) {
        Caracteristica cI = origem.getCaracteristica(indiceI);
        Caracteristica cJ = origem.getCaracteristica(indiceJ);

        origem.setCaracteristica(indiceI, cJ);
        origem.setCaracteristica(indiceJ, cI);
    }

}
