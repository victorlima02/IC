/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.algoritmos.DE;

import ic.populacional.algoritmo.AlgoritmoEvolucionario;
import ic.populacional.seres.reais.SerReal;
import java.util.List;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class DE extends AlgoritmoEvolucionario<Double, SerReal<Double>> {

    {
        nome = "DE";
    }

    @Override
    public void iteracao() {
        
        List<SerReal<Double>> novaGeracao = gerador.getN(populacao.size()); 
        
        mutador.muta(novaGeracao);
                
        List<SerReal<Double>> filhos = recombinador.recombinaTodos(novaGeracao);
    
        populacao.setIndividuos(filhos);
        
    }
    
}

//    @Override
//    public String relatorio() {
//        StringBuilder relatorio = new StringBuilder();
//
//        //relatorio.append("Meta:\t" + ((Usina) ambiente).getMeta() + "\n");
//
//        relatorio.append(super.relatorio());
//
//        relatorio.append(((DistribuicaoVazao)getMelhorSer()).toStringDetalhado());
//
//        return relatorio.toString();
//    }
