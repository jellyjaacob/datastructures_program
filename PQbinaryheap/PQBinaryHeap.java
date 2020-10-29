/*
   Program 3
   CS310-01
   Description: Create an implementation for a binary heap that involves priority queue
   4/2/20
   @author Jacob Le cssc1238
   Red ID: 822652578
*/
 
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
 
@SuppressWarnings("unchecked")
public class BinaryHeapPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {
// Min heap where 1-20, 1 is the highest priority, 20 is lowest
// Children is: 2x parent + 1 & 2
// Parent is: floor((children-1)/2)
 
   private E [] pQueue; //array for priority queue, initialized
   private int currentSize,maxSize; //current size of the array and max size of array
   public static final int DEFAULT_MAX_CAPACITY = 1000;
   private int lastPosition; //
  
   // basic constructor
   public BinaryHeapPriorityQueue() {
       this(DEFAULT_MAX_CAPACITY);
   }
 
   // constructor takes a single integer parameter that represents the maximum capacity of the PQ
   public BinaryHeapPriorityQueue (int maxCapacity) {
      
       maxSize = maxCapacity; // assigns size to be the max
       currentSize = 0; // assigns current size to 0
       pQueue = (E[]) new Comparable[maxCapacity]; //creating the array to use
   }
 
 
   // Inserts a new object into the priority queue. Returns true if
   // the insertion is successful. If the PQ is full, the insertion
   // is aborted, and the method returns false.
   public boolean insert(E object) {
       // Insert next available space and trickle up.
       if (isFull())
           return false;
      
       array[++lastPosition] = object; // adds the object at the very end
       trickleUp(lastPosition); // trickles up
 
   }
   // Removes the object of highest priority that has been in the
   // PQ the longest, and returns it. Returns null if the PQ is empty.
   public E remove() {
       // Remove from the root (pQ[0]) and replace it with last element and trickle down.
 
       E temp = pQueue[0];
       swap(0, lastPosition--);
 
   }
 
   // Deletes all instances of the parameter obj from the PQ if found, and
   // returns true. Returns false if no match to the parameter obj is found.
   public boolean delete(E obj) {
      
 
   // Returns the object of highest priority that has been in the
   // PQ the longest, but does NOT remove it.
   // Returns null if the PQ is empty.
   public E peek() {
      
   }
   // Returns true if the priority queue contains the specified element
   // false otherwise.
   public boolean contains(E obj) {
 
      
   }
 
   // Returns the number of objects currently in the PQ.
   public int size() {
       return currentSize;
   }
 
   // Returns the PQ to an empty state.
   public void clear() {
       head = null;
   }
 
   // Returns true if the PQ is empty, otherwise false
   public boolean isEmpty() {
       return size() == 0;
   }
 
   // Returns true if the PQ is full, otherwise false. List based
   // implementations should always return false.
   public boolean isFull() {
       return false; // always will never be full
   }
 
   public void swap(int from, int to) {
   // basic method to swap variables
       E temp = pQueue[from];
       pQueue[from] = pQueue[to];
       pQueue[to] = temp;
 
   }
 
   public void trickleUp(int position) {
       /* if (position == 0)
           return;
       int parent = (int) Math.floor((position-1)/2)
       if (((Comparable<E>)pQueue[position]).compareTo(pQueue.parent) > 0) {
           swap(position, parent); // swaps
           trickleUp(parent); // recursive call to compare child to parent
       } */
       int newIndex = currentSize - 1;
       int parentIndex = (newIndex - 1) >> 1;
       Wrapper<E> newValue = pQueue[newIndex];
       while(parentIndex >= 0 && newValue.compareTo(pQueue[parentIndex] < 0 )) {
           pQueue[newIndex] = pQueue[parentIndex];
           parentIndex = (parentIndex-1) >> 1;
       }
       pQueue[newIndex] = newValue;
   }
 
   public void trickleDown(int parent) {
 
       int left = (2*parent) + 1; // left node
       int right = (2*parent) + 2; // right node
 
       // 2 if statements to compare parent to left node first, then right
       /*if (left == position && (((Comparable<E>)pQueue[parent]).compareTo(pQueue[left]) < 0)) {
           swap(parent, left);
           return;
       }
       if (right == position && (((Comparable<E>)pQueue[parent]).compareTo(pQueue[right]) < 0)) {
           swap(parent, right);
           return;
       }
       if (left >= lastPosition || right >= lastPosition)
           return;
       if (pQueue[left] > pQueue[right] && pQueue[parent] < pQueue[left]) {
           swap(parent, left);
           trickleDown(left);
       } else if (pQueue[parent] < pQueue[right]) {
           swap(parent, right);
           trickleDown(right);
       } */
       int current = 0;
       int child = getNextChild(current);
       while (child != -1 && pQueue[current].compareTo(pQueue[child] < 0) && pQueue[child].compareTo(pQueue[currentSize-1]) < 0) {
           pQueue[current] = pQueue[child];
           current = child;
           child = getNextChild(current);
       }
       pQueue[current] = pQueue[currentSize-1];
   }
 
   // Returns an iterator of the objects in the PQ, in no particular
   // order.
   // needs to be ADJUSTED to be for an array, currently is for a linked list
   public Iterator<E> iterator() {
       return new SLListIterator();
   }
 
   class SLListIterator implements Iterator<E> {
       Node<E> nodePointer;
       private long modCount = modificationCounter;
 
       public SLListIterator() {
           nodePointer = head;
       }
 
       public boolean hasNext() {
           if (modCount != modificationCounter)
               throw new ConcurrentModificationException();
           return nodePointer != null;
       }
 
       public E next() {
           if (!hasNext())
               throw new NoSuchElementException();
           E tmp = nodePointer.data;
           nodePointer = nodePointer.next;
           return tmp;
       }
   }
