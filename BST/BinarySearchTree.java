/*
   Program 4
   CS310-01
   Description: Create an implementation for binary search tree of a dictionary
   4/25/20
   @author Jacob Le cssc1238
   Red ID: 822652578  
*/
 
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
 
@SuppressWarnings("unchecked")
public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
 
   private int currentSize; // holds size
   private long modCounter; // for fail-fast iterator
   private Node<K,V> root; // first node in the tree
   private K keyValue; // to hold value
   private int iterIndex;
  
   // taken from Riggin's reader and is the Node class
   private class Node<K,V> {
       private K key; // data item for key
       private V value; // data item for value
       private Node<K,V> leftChild; // node's left
       private Node<K,V> rightChild; // node's right
 
       public Node(K k, V v) {
           key = k;
           value = v;
           leftChild = rightChild = null; // setting left+right to null
       }
   }
  
   // constructor
   public BinarySearchTree() {
       root = null;
       currentSize = 0;
       modCounter = 0;
       keyValue = null; // set to be nothing
   }
 
// Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
   public boolean put(K key, V value) {
       if (isFull() )
           return false;
       if(root == null) // first node is null
           root = new Node<K,V>(key, value); // insert a new node at the head
       else
           insert(key, value, root, null, false);
       currentSize++; // increase the size to accommodate insert
       modCounter++;
       return true;
   }
// Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
   public boolean delete(K key) {
       if (isEmpty() )
           return false;
       remove(key,root,null,false);
       currentSize--;
       modCounter++;
       return true;
   }
 
   public V get(K key) {
       if (isEmpty() )
           return null;
       return find(key, root); // finds the key and the root node
   }
 
   public K getKey(V value) {
       if (isEmpty() )
           return null;
       return findKey(value, root); // finds the value and the root node
   }
  
   public int size() {
       return currentSize;
   }
   public boolean isFull() {
       return false;
   }
   public boolean isEmpty() {
       if (currentSize == 0)
           return true;
       return false;
   }
   public void clear() {
       root = null; // sets head to null
       currentSize = 0;
       modCounter = 0;
   }
 
   // private class for insert, taken from Riggin's reader
   private void insert(K k, V v, Node<K,V> n, Node<K,V> parent, boolean wasLeft) {
       if (n == null) {
           if(wasLeft)
               parent.leftChild = new Node<K,V>(k,v);
           else
               parent.rightChild = new Node<K,V>(k,v);
       }
       else if (((Comparable<K>)k).compareTo((K)n.key) < 0)
           insert(k,v,n.leftChild,n,true); // left
       else
           insert(k,v,n.rightChild,n,false); // right
   }
 
   private void remove(K k, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
       if (n == null) {
           return;
       } else if (((Comparable<K>) k).compareTo(n.key) < 0) {
           remove(k, n.leftChild, n, true); // left
       } else if (((Comparable<K>) k).compareTo(n.key) > 0) {
           remove(k, n.rightChild, n, false); // right
       } else { // node is found, cases:
           // 0 children
           if (n.leftChild == null && n.rightChild == null) {
               if (parent == null)
                   root = null;
               else if (wasLeft) // node deleted is on left
                   parent.leftChild = null;
               else // node delete is on right
                   parent.rightChild = null;
           // 1 children on left
           } else if (n.leftChild != null && n.rightChild == null) {
               if (parent == null)
                   root = n.leftChild;
               else if (wasLeft) // deleted node is left with a left child, so that child is parent's new left child
                   parent.leftChild = n.leftChild;
               else // deleted node is right with a left child, so that child is parent's new right child
                   parent.rightChild = n.leftChild;
           // 1 children on right
           } else if (n.rightChild != null && n.leftChild == null) {
               if (parent == null)
                   root = n.rightChild;
               else if (wasLeft) // deleted node is left with a right child, so that child is parent's new left child
                   parent.leftChild = n.rightChild;
               else // deleted node is right with a right child, so that child is parent's new right child
                   parent.rightChild = n.rightChild;
           }
       }
   }
  
   private V find(K k, Node<K,V> n) {
       if (n == null) // if head is null
           return null;
       if (((Comparable<K>)k).compareTo(n.key) < 0) // go left
           return find(k, n.leftChild);
       else if (((Comparable<K>)k).compareTo(n.key) > 0) // go right
           return find(k, n.rightChild);
       return (V) n.value; // found the value
   }
 
   private K findKey(V v, Node<K,V> n) {
       if (n == null)
           return null;
       if (((Comparable<V>)v).compareTo((V)n.value) < 0)
           return findKey(v, n.leftChild);
       else if (((Comparable<V>)v).compareTo((V)n.value) > 0)
           return findKey(v, n.rightChild);
       return (K) n.key; // found the key
 
   }
 
   public Iterator<K> keys() {
       return new KeyIteratorHelper();
   }
 
   public Iterator<V> values() {
       return new ValueIteratorHelper();
   }
 
   // Returns an Iterator of the keys in the dictionary, in ascending sorted order
   abstract class IteratorHelper<E> implements Iterator<E> {
       protected Node<K, V>[] nodes;
       protected int index, iterIndex;
       protected long modCheck;
      
       public IteratorHelper() {
           nodes = new Node[currentSize];
           index = 0;
           iterIndex = 0;
           modCheck = modCounter;
           inorderFillArray(root);
       }
      
       public boolean hasNext() {
           if (modCheck != modCounter)
               throw new ConcurrentModificationException();
           return index < currentSize;
       }
      
       public abstract E next();
      
       public void delete() {
           throw new UnsupportedOperationException();
       }
      
       private void inorderFillArray(Node<K, V> n) {
           if (n == null)
               return;
 
           inorderFillArray(n.leftChild);
           nodes[iterIndex++] = n;
           inorderFillArray(n.rightChild);
       }
   }
   class KeyIteratorHelper<K> extends IteratorHelper<K> {
       public KeyIteratorHelper() {
           super();
       }
       public K next() {
           return (K) nodes[index++].key;
       }
   }
   class ValueIteratorHelper<V> extends IteratorHelper<V> {
       public ValueIteratorHelper() {
           super();
       }
 
       public V next() {
           return (V) nodes[index++].value;
       }
   }
}
