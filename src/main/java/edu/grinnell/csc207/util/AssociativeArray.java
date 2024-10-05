package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K and values of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Leonardo Alves Nunes
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({"unchecked"})
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(), DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArray = new AssociativeArray<K, V>();
    int amountToResize = this.pairs.length / DEFAULT_CAPACITY; // this will tell how big the
                                                               // original array is in relation to
                                                               // the default size
    for (int i = 0; i < amountToResize; i++) {
      clonedArray.expand();
    } // for
    clonedArray.size = this.size;
    for (int i = 0; i < this.pairs.length; i++) {
      if (this.pairs[i] == null) {
        clonedArray.pairs[i] = null;
      } else {
        clonedArray.pairs[i] = new KVPair<>(this.pairs[i].key, this.pairs[i].val);
      } // if else
    } // for
    return clonedArray;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    if (this.size == 0) {
      return new String("{}");
    } // if
    String finalString = new String("{");

    for (int i = 0; i < this.size; i++) {
      finalString = finalString.concat(this.pairs[i].key.toString());
      finalString = finalString.concat(":");
      if (this.pairs[i].val == null) {
        finalString = finalString.concat("null");
      } else {
        finalString = finalString.concat(this.pairs[i].val.toString());
      } // if else

      if (i == this.size - 1) {
        finalString = finalString.concat("}");
      } else {
        finalString = finalString.concat(", ");
      } // if else
    } // for
    return finalString;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will return value.
   *
   * @param key The key whose value we are seeting.
   * @param value The value of that key.
   *
   * @throws NullKeyException If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } else {
      try {
        int index = this.find(key);
        pairs[index].val = value;
      } catch (KeyNotFoundException e) {
        if (this.size == this.pairs.length) {
          this.expand();
        } // if
        pairs[this.size] = new KVPair<K, V>();
        pairs[this.size].key = key;
        pairs[this.size].val = value;
        this.size += 1;
      } // try catch
    } // if else
  } // set(K,V)
  /**
   * Get the value associated with key.
   *
   * @param key A key
   *
   * @return The corresponding value
   *
   * @throws KeyNotFoundException when the key is null or does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    try {
      int index = this.find(key);
      return this.pairs[index].val;
    } catch (KeyNotFoundException e) {
      // COULD NOT FIND KEY
      throw new KeyNotFoundException();
    } // try catch
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should return false for the null key, since
   * it cannot appear.
   *
   * @param key The key we're looking for.
   *
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    if (this.pairs[0] == null) {
      return false;
    } // if
    for (int i = 0; i < this.size; i++) {
      if (this.pairs[i] == null) {
        return false;
      } // if
      if (this.pairs[i].key.equals(key)) {
        return true;
      } // if
    } // for
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key) will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   *
   * @param key The key to remove.
   */
  public void remove(K key) {
    try {
      int index = this.find(key);

      if (this.size == 1) {
        this.pairs[0] = null;
        this.size = 0;
      } else {
        while (index < this.size - 1) {
          this.pairs[index] = this.pairs[index + 1];
          index++;
        } // while
        this.pairs[this.size - 1] = null;
        this.size -= 1;
      } // if else

    } catch (KeyNotFoundException e) {
      // COULD NOT FIND A KEY TO REMOVE
    } // try catch
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   *
   * @return The number of key/value pairs in the array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such entry is found,
   * throws an exception.
   *
   * @param key The key of the entry.
   *
   * @return The index of the key, if found.
   *
   * @throws KeyNotFoundException If the key does not appear in the associative array.
   */
  int find(K key) throws KeyNotFoundException {
    int index = 0;
    if (this.pairs[0] == null) {
      throw new KeyNotFoundException();
    } // if
    while (index < this.size) {
      if (this.pairs[index].key.equals(key)) {
        return index;
      } // if
      index += 1;
    } // while
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray
