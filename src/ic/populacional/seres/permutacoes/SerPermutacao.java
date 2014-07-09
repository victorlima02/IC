/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.permutacoes;

import ic.populacional.Caracteristica;
import ic.populacional.seres.inteiros.SerInteiro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * 
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerPermutacao<G extends Number & Comparable<G>> extends SerInteiro<G> {

    /**
     * Construtor.
     *
     * @since 1.0
     * @param limiteInferior Limite inferior, inclusive.
     * @param limiteSuperior Limite superior, exclusive.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * </ul>
     */
    public SerPermutacao(int limiteInferior, int limiteSuperior) {
        super(limiteSuperior - limiteInferior, limiteInferior, limiteSuperior);
    }

    /**
     * Transforma uma lista de inteiros em uma lista de características.
     *
     * @since 1.0
     * @param caracteristicas
     * @return
     */
    public static List<Caracteristica<Integer>> integerListToLocusList(List<Integer> caracteristicas) {

        List<Caracteristica<Integer>> locus = new ArrayList(caracteristicas.size());

        caracteristicas.stream().forEach((inteiro) -> {
            locus.add(new LocusPermutacao(inteiro));
        });

        return locus;
    }
}
