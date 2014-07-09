/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.inteiros;

/**
 * Implementação da classe SerInteiro com limites atribuídos ao máximo.
 *
 * <p>
 * Essa classe estende a classe <code>SerInteiro</code>, atribuindo como limites
 * aos valores dos locus os limites da representação para inteiros:
 * <code>Integer.MIN_VALUE</code>, <code>Integer.MAX_VALUE-1</code>.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * @param <G> Classe do retorno da função objetivo (Grau de adaptação):
 * AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float,
 * Integer, Long, Short.
 */
public class SerInteiroMax<G extends Number & Comparable<G>> extends SerInteiro<G> {

    /**
     * Construtor.
     * 
     * @since 1.0
     */
    public SerInteiroMax(int ncaracteristicas) {
        super(ncaracteristicas,Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
