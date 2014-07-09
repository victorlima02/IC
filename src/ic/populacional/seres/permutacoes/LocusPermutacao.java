/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.permutacoes;

import ic.populacional.Caracteristica;
import java.util.Objects;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class LocusPermutacao extends Caracteristica<Integer> implements Comparable<LocusPermutacao> {

    Integer locus;

    public LocusPermutacao(int i) {
        this.locus = i;
    }

    @Override
    public Integer getValor() {
        return locus;
    }

    public void setRepresentacaoNumerica(int x) {
        locus = x;
    }

    @Override
    public Caracteristica copia() {
        return new LocusPermutacao(locus);
    }

    @Override
    public int compareTo(LocusPermutacao o) {
        return locus.compareTo(o.locus);
    }

    @Override
    public boolean equals(Object outro) {
        if (!(outro instanceof LocusPermutacao)) {
            return false;
        }
        return locus.equals(((LocusPermutacao) outro).locus);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.locus);
        return hash;
    }

    @Override
    public String toString() {
        return locus.toString();
    }
}
