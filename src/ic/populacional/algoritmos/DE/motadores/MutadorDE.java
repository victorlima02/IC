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
package ic.populacional.algoritmos.DE.motadores;

import ic.populacional.seres.reais.SerReal;
import ic.populacional.seres.reais.mutadores.MutadorReal;

/**
 * Operador de mutação DE.
 * 
 * @author Victor de Lima Soares
 * @versio 1.0
 */
public abstract class MutadorDE extends MutadorReal<SerReal>  {

    private Integer nDiferencas;
    private Double fatorDePerturbação;

    public MutadorDE(Integer nDiferencas, Double fatorDePerturbação) {
        super(1d);
        setNDiferencas(nDiferencas);
        setFatorDePerturbação(fatorDePerturbação);
    }
   
         
    /**
     * Recupera o número de diferenças ponderadas na mutação.
     *
     * @since 1.0
     * @return número de diferenças ponderadas atual.
     */
    public final Integer getNDiferencas() {
        return nDiferencas;
    }

    /**
     * Atribuí o número de diferenças ponderadas na mutação.
     *
     * @since 1.0
     * @param nDiferencas Novo número de diferenças.
     */
    public final void setNDiferencas(Integer nDiferencas) {
        this.nDiferencas = nDiferencas;
    }

    /**
     * Recupera o fator de perturbação da mutação.
     *
     * @since 1.0
     * @return o fator de perturbação atual.
     */
    public final Double getFatorDePerturbação() {
        return fatorDePerturbação;
    }

    /**
     * Atribui o fator de perturbação constante para mutação.
     *
     * @since 1.0
     * @param fatorDePerturbação Novo fator de perturbação.
     */
    public final void setFatorDePerturbação(Double fatorDePerturbação) {
        this.fatorDePerturbação = fatorDePerturbação;
    }
}
