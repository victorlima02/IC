/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.populacional.seres.inteiros;

import ic.populacional.algoritmo.operadores.Gerador;
import ic.populacional.seres.inteiros.mutadores.MutadorInteiro;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Victor de Lima Soares
 */
public class GeradorInteiroTest {

    Gerador gerador;

    public GeradorInteiroTest() {
        gerador = new GeradorInteiro(){

            @Override
            public SerInteiro getAleatorio() {
                SerInteiro novo = new SerInteiroMax(15);
                List<Integer> inteiros = getRandomIntegers(15,novo.getLimiteInferior(), novo.getLimiteSuperior());
                novo.setCaracteristicas(SerInteiro.integerListToLocusList(inteiros));
                return novo;
            }

            @Override
            public SerInteiro get() {
                return new SerInteiroMax(15);
            }
            
        };
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAleatorio method, of class GeradorInteiro.
     */
    @Test
    public void testGetAleatorio() {
        System.out.println("getAleatorio");

        Gerador instance = gerador;
        SerInteiro result;

        for (int i = 0; i < 10; i++) {
            result = (SerInteiro)instance.getAleatorio();
            assertEquals(15, result.getSize());
            MutadorInteiro.randomResetting(result, 0.5);
            
            System.out.println(result);
        }
        System.out.println("end getAleatorio");
    }

    /**
     * Test of get method, of class GeradorInteiro.
     */
    @Test
    public void testGet() {
        System.out.println("get");

        int n = 15;
        Gerador instance = gerador;
        SerInteiro result = (SerInteiro)instance.get();
        assertEquals(n, result.getSize());
        System.out.println("end get");
    }

    /**
     * Test of getRandomIntegers method, of class GeradorInteiro.
     */
    @Test
    public void testGetRandomIntegers() {
        System.out.println("getRandomIntegers");
        int n = 15;
        List<Integer> result = GeradorInteiro.getRandomIntegers(n);
        for (int i = 0; i < 10; i++) {
            assertEquals(n, result.size());
            System.out.println(result);
        }
        System.out.println("end getRandomIntegers");
    }

}
