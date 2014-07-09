package ic.populacional.seres.binarios;

import ic.populacional.Caracteristica;

/**
 *
 * @author Victor de Lima Soares
 */
public class LocusBinario extends Caracteristica<Integer> {

    private Boolean set;

    public LocusBinario() {
        this.set = false;
    }

    public LocusBinario(Boolean set) {
        this.set = set;
    }

    public final boolean getBit() {
        return set;
    }

    public final void setBit(Boolean bit) {
        this.set = bit;
    }

    /**
     * Realiza a inversão de valor de uma característica binária.
     *
     * @since 1.0
     */
    public final void flip() {
        setBit(!getBit());
    }

    @Override
    public Caracteristica copia() {
        return new LocusBinario(this.getBit());
    }

    @Override
    public Integer getValor() {
        return (set) ? 1 : 0;
    }
}
