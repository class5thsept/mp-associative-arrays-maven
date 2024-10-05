package edu.grinnell.csc207;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.NullKeyException;
import edu.grinnell.csc207.util.KeyNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * A place for you to put your own tests (beyond the shared repo).
 *
 * @author Leonardo Alves Nunes
 */
public class TestsFromStudent {
  /**
   * A simple test.
   */
  @Test
  public void alwaysPass() throws Exception {
  } // alwaysPass()

  /**
   * Looking for elements from a previously set associative array
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void nunesLeonardoTest1() throws NullKeyException, KeyNotFoundException{
    AssociativeArray<String, String> capitals = new AssociativeArray<String, String>();
    capitals.set("Portugal", "Lisbon");
    capitals.set("Spain", "Madrid");
    capitals.set("Chile", "Santiago");
    assertTrue(capitals.hasKey("Spain"));
    assertEquals("Lisbon", capitals.get("Portugal"), "Get one of the values present in the list");
    capitals.remove("Chile");
    assertFalse(capitals.hasKey("Chile")); // As the value was removed, the key is not in the associative array anymore
  } // nunesLeonardoTest1()

  /**
   * Assessing the size of a long associative array of integers
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void nunesLeonardoTest2() throws NullKeyException, KeyNotFoundException{
    AssociativeArray<Integer, Integer> numbersMultipliedByTen = new AssociativeArray<Integer, Integer>();
    numbersMultipliedByTen.set(0,0);
    numbersMultipliedByTen.set(1,10);
    numbersMultipliedByTen.set(2,20);
    numbersMultipliedByTen.set(3,30);
    numbersMultipliedByTen.set(4,40);
    numbersMultipliedByTen.set(5,50);
    numbersMultipliedByTen.set(6,60);
    numbersMultipliedByTen.set(7,70);
    numbersMultipliedByTen.set(8,80);
    numbersMultipliedByTen.set(9,90);
    numbersMultipliedByTen.set(10,100);

    assertFalse(numbersMultipliedByTen.hasKey(100));
    assertEquals(11, numbersMultipliedByTen.size(), "Correctly gets the size of the Associative array.");

    numbersMultipliedByTen.remove(5);
    numbersMultipliedByTen.remove(8);
    numbersMultipliedByTen.remove(10);
    numbersMultipliedByTen.remove(1);
    numbersMultipliedByTen.remove(2);

    assertEquals(6, numbersMultipliedByTen.size(), "Correctly gets the size of the Associative array after deleting some items.");
  } // nunesLeonardoTest2()

  /**
   * Looking for elements not present in the associative array and cloning an empty one.
   * 
   * @throws NullKeyException
   */
  @Test
  public void nunesLeonardoEdge1() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, Integer> studentIds = new AssociativeArray<String, Integer>();
    try{
      studentIds.get("Bruna");
      fail("Found the key although the associative array was empty.");
    } catch (KeyNotFoundException e){
      // Expected
    }
    studentIds.set("Bruna", 123123123);
    assertEquals(123123123, studentIds.get("Bruna"), "Correctly gets an item from the associative array");

    studentIds.remove("Bruna");

    try{
      AssociativeArray<String, Integer> studentIdsCloned = studentIds.clone();
      assertEquals(studentIdsCloned.size(), studentIds.size());
    } catch (Exception e) {
      fail("Could not clone empty associative array");
    }
  } // nunesLeonardoEdge1()

} // class TestsFromStudent
