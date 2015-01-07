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
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Emissor de eventos evolucionários com fila interna.
 *
 * <p>
 * Seguro para multi-threads.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 * @deprecated 
 * @todo Fix emissor:incompleto
 */
public class EmissorEventosEvolucionarios implements Queue<EventoEvolucionario> {

    private final Queue<EventoEvolucionario> eventos;
    private Long intervaloNotificacoes;
    private TimeUnit unidadeDeTempo;
    private final ScheduledThreadPoolExecutor scheduler;
    private ScheduledFuture future;
    private final PropertyChangeSupport pcs;
    private final AlgoritmoEvolucionario origem;
    private AtomicBoolean finalizando;

    /**
     * Construtor.
     *
     * @since 1.0
     * @param origem Origem dos eventos.
     * @param intervaloNotificacoes Intervalo entre emissões dos eventos.
     * @param unidade Unidade de tempo para o intervalo.
     *
     * @throws NullPointerException
     * <ul>
     * <li>Se algum dos parâmetros for igual a null.</li>
     * </ul>
     */
    public EmissorEventosEvolucionarios(AlgoritmoEvolucionario origem, Long intervaloNotificacoes, TimeUnit unidade) {
        if (origem == null) {
            throw new NullPointerException("Algoritmo de origem não definido.");
        }

        scheduler = new ScheduledThreadPoolExecutor(1);
        eventos = new ConcurrentLinkedQueue<>();
        setIntervaloNotificacoes(intervaloNotificacoes, unidade);
        this.origem = origem;
        finalizando.set(false);
        pcs = new PropertyChangeSupport(origem);
    }

    /**
     * Construtor.
     *
     * @since 1.0
     * @param origem Origem dos eventos.
     * @param pcs PropertyChangeSupport a ser utilizado.
     * @param intervaloNotificacoes Intervalo entre emissões dos eventos.
     * @param unidade Unidade de tempo para o intervalo.
     *
     * @throws NullPointerException
     * <ul>
     * <li>Se algum dos parâmetros for igual a null.</li>
     * </ul>
     */
    public EmissorEventosEvolucionarios(AlgoritmoEvolucionario origem, PropertyChangeSupport pcs, Long intervaloNotificacoes, TimeUnit unidade) {
        if (origem == null) {
            throw new NullPointerException("Algoritmo de origem não definido.");
        }
        if (pcs == null) {
            throw new NullPointerException("PropertyChangeSupport não definido.");
        }

        scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.setRemoveOnCancelPolicy(true);
        eventos = new ConcurrentLinkedQueue<>();
        setIntervaloNotificacoes(intervaloNotificacoes, unidade);
        this.origem = origem;
        finalizando.set(false);
        this.pcs = pcs;
    }

    /**
     * Define o intervalo entre notificações enviadas aos <i>listeners</i>.
     *
     * @since 1.0
     * @param intervaloNotificacoes Intervalo entre emissões dos eventos.
     * @param unidade Unidade de tempo para o intervalo.
     *
     * @throws NullPointerException
     * <ul>
     * <li>Se algum dos parâmetros for igual a null.</li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o intervalo for ser menor ou igual a zero.</li>
     * </ul>
     * @throws IllegalStateException
     * <ul>
     * <li>Se o método for usado após iniciar emissões.</li>
     * </ul>
     */
    public final void setIntervaloNotificacoes(Long intervaloNotificacoes, TimeUnit unidade) {
        if (intervaloNotificacoes == null || unidade == null) {
            throw new NullPointerException("Intervalo não definido.");
        }
        if (intervaloNotificacoes <= 0) {
            throw new IllegalArgumentException("Intervalo deve ser maior que zero.");
        }
        if(future != null) throw new IllegalStateException("Emissor já iniciou suas atividades, não pode ser modificado.");
        this.intervaloNotificacoes = intervaloNotificacoes;
        this.unidadeDeTempo = unidade;
    }

    /**
     * Inicia emissões.
     *
     * @since 1.0
     */
    public void start() {
        future=scheduler.scheduleWithFixedDelay(() -> {
            EventoEvolucionario evento = eventos.poll();
            if (evento != null) {
                pcs.firePropertyChange(evento);
            }
        }, 0, intervaloNotificacoes, unidadeDeTempo);
    }

    /**
     * Inicia desligamento do emissor.
     *
     * <p>
     * O emissor irá terminar de emitir , e parar.
     * </p>
     *
     * @since 1.0
     * @deprecated 
     * @todo reconstruir
     */
    public void shutdown() {
        finalizando.set(true);;
        //while()
        scheduler.shutdown();
    }

    /**
     * Desliga o emissor imediatamente.
     *
     * <p>
     * O emissor não irá terminar de emitir seus eventos, somente irá parar.
     * </p>
     *
     * @since 1.0
     */
    public void shutdownNow() {
        finalizando.set(true);
        scheduler.shutdownNow();
    }

    /**
     * Verifica se o emissor foi desligado.
     *
     * @since 1.0
     * @return<ul>
     * <li>
     * true: se o emissor estiver desligado.
     * </li>
     * false: caso contrário.
     * </ul>
     */
    public boolean isTerminated() {
        return scheduler.isTerminated();
    }

    /**
     * Recupera o intervalo de tempo entre emissões.
     *
     * @since 1.0
     * @return Intervalo entre cada emissão.
     */
    public Long getIntervaloNotificacoes() {
        return intervaloNotificacoes;
    }

    /**
     * Recupera a unidade de tempo usada para o intervalo entre emissões.
     *
     * @since 1.0
     * @return Unidade de tempo.
     */
    public TimeUnit getUnidadeDeTempo() {
        return unidadeDeTempo;
    }

    @Override
    public boolean add(EventoEvolucionario e) {
        if(finalizando.get()==false) return false;
        return eventos.add(e);
    }

    /**
     * Cria um evento e o adiciona a fila.
     *
     * @since 1.0
     * @param tipoEvento Tipo do evento.
     * @param velho Antigo valor da propriedade.
     * @param novo Novo valor da propriedade.
     */
    public void add(EventosEvolucionarios tipoEvento, Object velho, Object novo) {
        add(new EventoEvolucionario(origem, tipoEvento, velho, novo));
    }

    @Override
    public boolean offer(EventoEvolucionario e) {
        if(finalizando.get()==false) return false;
        return eventos.offer(e);
    }

    @Override
    public EventoEvolucionario remove() {
        return eventos.remove();
    }

    @Override
    public EventoEvolucionario poll() {
        return eventos.poll();
    }

    @Override
    public EventoEvolucionario element() {
        return eventos.element();
    }

    @Override
    public EventoEvolucionario peek() {
        return eventos.peek();
    }

    @Override
    public int size() {
        return eventos.size();
    }

    @Override
    public boolean isEmpty() {
        return eventos.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return eventos.contains(o);
    }

    @Override
    public Iterator<EventoEvolucionario> iterator() {
        return eventos.iterator();
    }

    @Override
    public Object[] toArray() {
        return eventos.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return eventos.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return eventos.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return eventos.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends EventoEvolucionario> c) {
        return eventos.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return eventos.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return eventos.retainAll(c);
    }

    @Override
    public void clear() {
        eventos.clear();
    }

    //Eventos Listener -------------
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
