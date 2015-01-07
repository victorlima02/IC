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
package ic.ce.base.eventos;

import ic.ce.base.algoritmo.AlgoritmoEvolucionario;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

/**
 * Proxy para a redistribuição de eventos.
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class ProxyEventos {

    private final EnumMap<EventosEvolucionarios, EmissorEventosEvolucionarios> eventos;
    private final PropertyChangeSupport pcs;
    private final AlgoritmoEvolucionario origem;

    public ProxyEventos(AlgoritmoEvolucionario origem, Long IntervaloNotificacoes, TimeUnit unidade) {

        eventos = new EnumMap<>(EventosEvolucionarios.class);
        pcs = new PropertyChangeSupport(origem);
        this.origem = origem;

        for (EventosEvolucionarios tipoEvento : EventosEvolucionarios.values()) {
            eventos.put(tipoEvento, new EmissorEventosEvolucionarios(origem, pcs, IntervaloNotificacoes, unidade));
        }
    }

    /**
     * Inicia emissões.
     *
     * @since 1.0
     */
    public void start() {
        eventos.values().stream().forEach((emissor) -> {
            emissor.start();
        });
    }

    /**
     * Inicia desligamento dos emissores.
     *
     * <p>
     * Os emissores irão terminar de emitir seus eventos, e parar.
     * </p>
     *
     * @since 1.0
     */
    public void shutdown() {
        eventos.values().stream().forEach((emissor) -> {
            emissor.shutdown();
        });
    }

    /**
     * Desliga os emissores imediatamente.
     *
     * <p>
     * Os emissores não irão terminar de emitir seus eventos, somente irão
     * parar.
     * </p>
     *
     * @since 1.0
     */
    public void shutdownNow() {
        eventos.values().stream().forEach((emissor) -> {
            emissor.shutdownNow();
        });
    }

    public void addEvento(EventoEvolucionario evento) {
        eventos.get(evento.getTipo()).add(evento);
    }

    public void addEvento(EventosEvolucionarios tipoEvento, Object velho, Object novo) {
        eventos.get(tipoEvento).add(new EventoEvolucionario(origem, tipoEvento, velho, novo));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(EventosEvolucionarios tipoEvento, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(tipoEvento.getNome(), listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(EventosEvolucionarios tipoEvento, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(tipoEvento.getNome(), listener);
    }

}
