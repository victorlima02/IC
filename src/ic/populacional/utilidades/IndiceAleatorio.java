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
package ic.populacional.utilidades;

import ic.populacional.Caracteristica;
import ic.populacional.Populacao;
import ic.populacional.Ser;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe auxiliar para a recuperação de índices em listas de características ou
 * em seres.
 *
 * @version 1.0
 * @author Victor de Lima Soares
 */
public class IndiceAleatorio {

    /**
     * Retorna um índice aleatório para uma característica de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Ser cujo índice da característica é desejado.
     * @return Índice escolhido aleatoriamente.
     */
    public final static int getUniforme(Ser origem) {
        return getUniforme(origem.getCaracteristicas());
    }

    /**
     * Retorna um índice aleatório para uma característica de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Lista de características cujo índice de uma delas é
     * desejado.
     * @return Índice escolhido aleatoriamente.
     */
    public final static int getUniforme(List<Caracteristica> origem) {
        return ThreadLocalRandom.current().nextInt(0, origem.size());
    }

    /**
     * Retorna um índice aleatório para uma característica de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Ser cujo índice de uma característica é desejado.
     * @param limiteInferior Limite inferior(índice), maior que zero.
     * @param limiteSuperior Limite superior(índice), maior que zero.
     * @return Índice escolhido aleatoriamente.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite inferior for menor que zero;
     * </li>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * <li>
     * Limite superior for maior <b>ou igual</b> ao tamanho do ser, ou lista de
     * características;
     * </li>
     * </ul>
     */
    public final static int getUniforme(Ser origem, int limiteInferior, int limiteSuperior) {
        return getUniforme(origem.getCaracteristicas(), limiteInferior, limiteSuperior);
    }

    /**
     * Retorna um índice aleatório para uma característica de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Lista de características cujo índice de uma delas é
     * desejado.
     * @param limiteInferior Limite inferior(índice), maior que zero.
     * @param limiteSuperior Limite superior(índice,inclusive), maior que zero.
     * @return Índice escolhido aleatoriamente.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * Limite inferior for menor que zero;
     * </li>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * <li>
     * Limite superior for maior <b>ou igual</b> ao tamanho do ser, ou lista de
     * características;
     * </li>
     * </ul>
     */
    public final static int getUniforme(List<Caracteristica> origem, int limiteInferior, int limiteSuperior) {
        if (limiteInferior < 0) {
            throw new IllegalArgumentException("Limite inferior deve ser maior que zero.");
        }
        if (limiteInferior > limiteSuperior) {
            throw new IllegalArgumentException("Limite superior deve ser maior que o limite inferior.");
        }
        if (limiteSuperior >= origem.size()) {
            throw new IllegalArgumentException("Limite superior deve ser menor que o tamanho do ser.");
        }

        return ThreadLocalRandom.current().nextInt(limiteInferior, limiteSuperior + 1);
    }

    /**
     * Retorna n índices aleatórios para características de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     *
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Ser cujos índices de características são desejados.
     * @param nNumeros Número de índices desejados.
     * @return Índices escolhidos aleatoriamente, sem repetição.
     */
    public final static List<Integer> getUniforme(Ser origem, int nNumeros) {
        return getUniforme(origem.getCaracteristicas(), nNumeros);
    }

    /**
     * Retorna <i>n</i> índices aleatórios para características de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Lista de características de origem: define os limites para
     * a geração.
     * @param nNumeros Número de índices desejados.
     * @return Índices escolhidos aleatoriamente, <b>sem repetição</b>.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * O número de índices pedidos for maior que o tamanho do ser.
     * </li>
     * </ul>
     */
    public final static List<Integer> getUniforme(List<Caracteristica> origem, int nNumeros) {

        if (nNumeros > origem.size()) {
            throw new IllegalArgumentException("Número de índices deve ser menor ou igual ao tamanho do ser.");
        }

        ArrayList<Integer> numeros = new ArrayList<>(nNumeros);

        while (numeros.size() < nNumeros) {
            int n = ThreadLocalRandom.current().nextInt(0, origem.size());
            if (!numeros.contains(n)) {
                numeros.add(n);
            }
        }
        return numeros;
    }

    /**
     * Retorna <i>n</i> índices aleatórios para características de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Ser de origem: define os limites para a geração.
     * @param nNumeros Número de índices desejados.
     * @param limiteInferior Limite inferior(índice), maior que zero.
     * @param limiteSuperior Limite superior(índice), maior que zero.
     * @return Índices escolhidos aleatoriamente, <b>sem repetição</b>.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * O número de índices pedidos for maior que o tamanho do ser.
     * </li>
     * <li>
     * Limite inferior for menor que zero;
     * </li>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * <li>
     * Limite superior for maior <b>ou igual</b> ao tamanho do ser, ou lista de
     * características;
     * </li>
     * <li>
     * Número de índices for maior que o tamanho do intervalo entre os limites.
     * </li>
     * </ul>
     */
    public final static List<Integer> getUniforme(Ser origem, int nNumeros, int limiteInferior, int limiteSuperior) {
        return getUniforme(origem.getCaracteristicas(), nNumeros, limiteInferior, limiteSuperior);
    }

    /**
     * Retorna <i>n</i> índices aleatórios para características de um ser.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     *
     * @since 1.0
     * @param origem Lista de características de origem: define os limites para
     * a geração.
     * @param nNumeros Número de índices desejados.
     * @param limiteInferior Limite inferior(índice), maior que zero.
     * @param limiteSuperior Limite superior(índice), maior que zero.
     * @return Índices escolhidos aleatoriamente, <b>sem repetição</b>.
     *
     * @throws IllegalArgumentException Se <ul>
     * <li>
     * O número de índices pedidos for maior que o tamanho do ser.
     * </li>
     * <li>
     * Limite inferior for menor que zero;
     * </li>
     * <li>
     * Limite superior for menor que o limite inferior;
     * </li>
     * <li>
     * Limite superior for maior <b>ou igual</b> ao tamanho do ser, ou lista de
     * características;
     * </li>
     * <li>
     * Número de índices for maior que o tamanho do intervalo entre os limites.
     * </li>
     * </ul>
     */
    public final static List<Integer> getUniforme(List<Caracteristica> origem, int nNumeros, int limiteInferior, int limiteSuperior) {

        if (nNumeros > origem.size()) {
            throw new IllegalArgumentException("Número de índices deve ser menor ou igual ao tamanho do ser.");
        }
        if (limiteInferior < 0) {
            throw new IllegalArgumentException("Limite inferior deve ser maior que zero.");
        }
        if (limiteInferior > limiteSuperior) {
            throw new IllegalArgumentException("Limite superior deve ser maior que o limite inferior.");
        }
        if (limiteSuperior >= origem.size()) {
            throw new IllegalArgumentException("Limite superior deve ser menor que o tamanho do ser.");
        }
        if (nNumeros > limiteSuperior - limiteInferior + 1) {
            throw new IllegalArgumentException("Número de índices deve ser menor ou igual ao tamanho do intervalo entre os limites: [" + limiteInferior+","+limiteSuperior+"]");
        }

        ArrayList<Integer> numeros = new ArrayList<>(nNumeros);

        while (numeros.size() < nNumeros) {
            int n = ThreadLocalRandom.current().nextInt(limiteInferior, limiteSuperior + 1);
            if (!numeros.contains(n)) {
                numeros.add(n);
            }
        }
        return numeros;
    }

    /**
     * Retorna um índice aleatório para um ser na população.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     * 
     * @since 1.0
     * @param origem População de origem: define os limites para a geração.
     * @return Índices escolhidos aleatoriamente, sem repetição.
     */
    public final static Integer getUniforme(Populacao origem) {

        Integer n = ThreadLocalRandom.current().nextInt(0, origem.size());
   
        return n;
    }
    /**
     * Retorna n índices aleatórios para seres em uma população.
     *
     * <p>
     * Distribuição uniforme.
     * </p>
     * <p>
     * Gerador seguro para múltiplas Threads.
     * </p>
     * @since 1.0
     * @param origem População de origem: define os limites para a geração.
     * @param nNumeros Número de índices desejados.
     * @return Índices escolhidos aleatoriamente, sem repetição.
     */
    public final static List<Integer> getUniforme(Populacao origem, int nNumeros) {

        List<Integer> numeros = new ArrayList<>(nNumeros);

        while (numeros.size() < nNumeros) {
            int n = ThreadLocalRandom.current().nextInt(0, origem.size());
            if (!numeros.contains(n)) {
                numeros.add(n);
            }
        }

        return numeros;
    }
}
