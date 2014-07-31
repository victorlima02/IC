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
package ic.ce.populacional.algoritmos.DE.mutadores;

import ic.ce.populacional.seres.reais.LocusReal;
import ic.ce.populacional.seres.reais.SerReal;
import ic.ce.populacional.utilidades.IndiceAleatorio;
import java.util.List;

/**
 * Operador de mutação DE: best.
 *
 * <p>
 * DE/best/nDiferencas/?
 * </p>
 * <p>
 * O operador mutação permite a criação de novos seres, denominados vetores
 * modificados ou doadores, por meio da diferença ponderada de dois indivíduos
 * escolhidos randomicamente na população.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class Best extends MutadorDE {

    public Best(Integer nDiferencas, Double fatorDePerturbação) {
        super(nDiferencas, fatorDePerturbação);
    }

    @Override
    public void muta(final SerReal ser) {

        Integer nRand = getNDiferencas() * 2;

        List<Integer> indices = IndiceAleatorio.getUniforme(getPopulacao(), nRand);
        SerReal base = (SerReal) getPopulacao().getMelhor();

         
        for (int c = 0; c < ser.getSize(); c++) {

            Double valor = (Double) base.getCaracteristica(c).getValor();

            for (int i = 0; i < getNDiferencas();) {
                Double valor1 = (Double) getPopulacao().get(indices.get(i++)).getCaracteristica(c).getValor();
                Double valor2 = (Double) getPopulacao().get(indices.get(i++)).getCaracteristica(c).getValor();

                valor += getFatorDePerturbação() * (valor1 - valor2);
            }
            LocusReal locus = (LocusReal) ser.getCaracteristica(c);

            if (valor >= locus.getLimiteSuperior()) {
                locus.maximixa();
            } else if (valor < locus.getLimiteInferior()) {
                locus.minimiza();
            } else {
                locus.setValor(valor);
            }
        }

    }
}
