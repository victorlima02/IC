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
package ic.populacional.algoritmo.operadores;

import ic.populacional.utilidades.IndiceAleatorio;
import ic.populacional.Ambiente;
import ic.populacional.Populacao;
import ic.populacional.Ser;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Victor de Lima Soares
 */
public abstract class Seletor<G  extends Number & Comparable<G>, S extends Ser<G>> extends Operador {

    protected Ambiente<G,S> ambiente;
    protected Populacao<G,S> populacao;

    public Seletor(Ambiente ambiente, Populacao populacao) {
        this.ambiente = ambiente;
        this.populacao = populacao;
    }

    public abstract List<S> getPais();

    public abstract List<S> getSobreviventes();

    /**
     * Retorna os melhores indivíduos entre os escolhidos aleatoriamente.
     *
     * @param nMelhores Número de indivíduos a serem retornados (os melhores).
     * @param nAleatorios Número de indivíduos a serem coletados - amostra para
     * seleção.
     * @return Melhores seres da amostra coletada.
     * 
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o número de seres para retorno for maior que o número de seres por amostra.</li>
     * </ul>
     * 
     */
    public List<S> melhoresEntreAleatorios(int nMelhores, int nAleatorios) {

        if(nMelhores > nAleatorios) throw new IllegalArgumentException("Número de seres para retorno deve ser menor ou igual ao número de seres por amostra.");
       
        List<Integer> indices = IndiceAleatorio.getUniforme(populacao, nAleatorios);
        PriorityQueue<S> melhores = new PriorityQueue<>(ambiente.comparadorInverso());
        List<S> selecionados = new ArrayList<>(nMelhores);

        for (Integer indice : indices) {
            S individuo = populacao.get(indice);
            melhores.add(individuo);
        }

        for (int i = 0; i < nMelhores; i++) {
            selecionados.add(melhores.poll());
        }

        return selecionados;
    }
}
