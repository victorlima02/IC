/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.binarios;

import ic.populacional.Caracteristica;
import ic.populacional.algoritmo.operadores.Gerador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Victor de Lima Soares
 * @param <S> Classe dos Seres.
 */
public abstract class GeradorBinario<S extends SerBinario> extends Gerador<S> {

    /**
     * Retorna <i>n</i> Booleans aleatórios em uma lista.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param nbits Número de bits desejado.
     * @return Lista de Booleans sorteada.
     */
    public final static List<Boolean> boleanListUniforme(int nbits) {

        List<Boolean> lista = new ArrayList<>();
        for (int i = 0; i < nbits; i++) {
            lista.add(ThreadLocalRandom.current().nextBoolean());
        }
        return lista;
    }

    /**
     * Retorna <i>n</i> características binárias aleatórios em uma lista.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * @since 1.0
     * @param nbits Número de bits desejado.
     * @return Lista de características sorteada.
     */
    public final static List<LocusBinario> LocusListUniforme(int nbits) {

        List<LocusBinario> lista = new ArrayList<>(nbits);
        for (int i = 0; i < nbits; i++) {
            lista.add(new LocusBinario(ThreadLocalRandom.current().nextBoolean()));
        }
        return lista;
    }

}
