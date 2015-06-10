/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.unico.assignment.service;
import au.unico.assignment.dao.EnteredNumberDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import au.unico.assignment.service.GCDService;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import au.unico.assignment.dao.GreatestCommonDivisorDao;
import au.unico.assignment.model.*;
import java.util.*;
/**
 *
 * @author p740909
 */
public class GCDServiceTest {
    GCDService gcdService;
    
    public GCDServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() {
    }

    @Before
    public void setUp() {
        gcdService = new GCDService();
        GreatestCommonDivisorDao gcdDao = mock(GreatestCommonDivisorDao.class);
        List<GreatestCommonDivisor> gcdList = new ArrayList<GreatestCommonDivisor>();
        GreatestCommonDivisor gcdElem1 = new GreatestCommonDivisor();
        gcdElem1.setComputedGCD(10);
        gcdElem1.setId(1);
        GreatestCommonDivisor gcdElem2 = new GreatestCommonDivisor();
        gcdElem2.setComputedGCD(10);
        gcdElem2.setId(1);
        gcdList.add(gcdElem1);
        gcdList.add(gcdElem2);        
        gcdService.setGreatestCommonDivisorDao(gcdDao);
        
        EnteredNumberDao enteredDao = mock(EnteredNumberDao.class);
        List<EnteredNumber> enteredList = new ArrayList<EnteredNumber>();
        EnteredNumber enteredElem1 = new EnteredNumber();
        enteredElem1.setIntegerValue(10);
        enteredElem1.setId(1);
        EnteredNumber enteredElem2 = new EnteredNumber();
        enteredElem2.setIntegerValue(20);
        enteredElem2.setId(1);
        enteredList.add(enteredElem1);
        enteredList.add(enteredElem2);        
        gcdService.setEnteredNumberDao(enteredDao);
        
        when(gcdDao.findAll()).thenReturn(gcdList);
        when(enteredDao.findAll()).thenReturn(enteredList);
        
    }

    /**
     * Test of gcdSum method, of class GCDService.
     */
    @Test
    public void testGcdSum() throws Exception {
        System.out.println("gcdSum");
        int result = gcdService.gcdSum();
        assertNotEquals(0, result); 
    }
    
     /**
     * Test of testGcdList method, of class GCDService.
     */
    @Test
    public void testGcdList() throws Exception {
        List<Integer> result = gcdService.gcdList();
        assertNotNull(result);
    }
     /**
     * Test of testGetNumbersSuccesfullyAddedToJMSQueue() method, of class GCDService.
     */
    @Test
    public void testGetNumbersSuccesfullyAddedToJMSQueue() throws Exception {
        List<Integer> result = gcdService.getNumbersSuccesfullyAddedToJMSQueue();
        assertNotNull(result);
    }
}
