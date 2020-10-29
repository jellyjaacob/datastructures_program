// Program #1
// Creating a Priority Queue for Unordered Arrays with Interface and Comparable
// CS310-01
// 2/11/20
// @author Jacob Le cssc1238
// RED ID: 822652578


package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
 
@SuppressWarnings("unchecked")
public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private E[] pQueue; //array for priority queue, initialized
    private int currentSize, maxSize; //current size of the array and max size of array
    
    
    // basic constructor
    public UnorderedArrayPriorityQueue() {
        this(DEFAULT_MAX_CAPACITY);
    }

    // constructor takes a single integer parameter that represents the maximum capacity of the PQ
    public UnorderedArrayPriorityQueue (int maxCapacity) {
        maxSize = maxCapacity; // assigns size to be the max
        currentSize = 0; // assigns current size to 0
        pQueue = (E[]) new Comparable[maxCapacity]; //creating the array to use
    }

    public boolean insert(E object) {
        // insert a new object into PQ
        // if insertion is successful, then return true, false if PQ is full
        if(isFull()) {
            return false;
        }
        pQueue[currentSize++] = object;
        return true;
    }

    public E remove() { // need to find a way to find highest priority and return that object
        // remove object w/ highest priority and returns false if PQ is empty
        if(isEmpty()) {
            return null;
        }
        int maxIndex = 0; // assigning max Index to zero
        E maxElem = pQueue[0]; // assigning max Element to first element of PQ

        for (int i = 0; i < currentSize; i++) { // loops through each element of PQ
            if (((Comparable<E>)pQueue[i]).compareTo(maxElem) < 0) { // checks if PQ at i-th element and compares it to max Element and sees if they are highest priority or not
                maxIndex = i; // makes the max Index the new i-th index 
                maxElem = pQueue[i]; // makes the max Element the respective highest priority in the PQ                
            }
        }
        for (int j = maxIndex; j < currentSize-1; j++) { // loops starting at the max Index throughout the current size
            pQueue[j] = pQueue[j + 1]; //shifts the elements one over to the left
        }
        currentSize--; // gets rid of the "hole" in the array
        return maxElem; // returns object w/ highest priority
    }    

    public boolean delete(E obj) { // deletes all instances of obj from PQ
        
        if (isEmpty() ) 
            return false;
        int count = 0; // to keep track of instance that obj appear
        int j = 0; // used for the loop to match all instance of obj

        for (int i = 0; i < currentSize; i++) { // runs through PQ, checking each index
            if (((Comparable<E>)pQueue[i]).compareTo(obj) == 0) { // if the 2 objects are equal to each other
                count++; 
            } else {
                pQueue[j] = pQueue[j + count]; // swaps i-th elem with (i+count) elem
                j++; // increases j to match all instances of obj     
            }
        }
        currentSize = currentSize - count; // removes all instances of obj
        return true;
    }

    public E peek() { // returns object with highest priority
        if(isEmpty()) {
            return null;
        }
        E minimum = pQueue[0]; // setting min to the first object in PQ
        for (int i = 0; i < currentSize; i++) { // loop through each object of the PQ
            if (((Comparable<E>)pQueue[i]).compareTo(minimum) < 0) { // checks and compares PQ[i] to PQ[min]
                minimum = pQueue[i]; // changes minimum to PQ[i] if the if statement is true
            }
        }
        return minimum; 
    }

    public boolean contains(E obj) {
        // returns true if element is found, false otherwise
        for (int i = 0; i < currentSize; i++) { // loops through each object of pQ
            if ( ((Comparable<E>)pQueue[i]).compareTo(obj) == 0) { // checks if PQ[i] is equal to obj
                return true; 
            }
        }
        return false;      
    }

    public int size() {
        // return the current number of objects of the PQ
        return currentSize;
    }

    public void clear() {
        // clears the PQ and sets it back to zero
        currentSize = 0; 
    }

    public boolean isEmpty() {
        // if PQ is empty, returns true, otherwise false
        return currentSize == 0;
    }

    public boolean isFull() {
        // if PQ is full, return true, otherwise false
        return currentSize == maxSize;
    }

    public Iterator<E> iterator() {
        // returns an iterator of the objects in PQ, in no order
        // purpose is to traverse through each element of the PQ
        return new HelpIterator();
    }

    class HelpIterator implements Iterator<E> {
        private int counter; 

        public Iterator<E> iterator() {
            counter = 0; 
            return (Iterator<E>) this;
        }

        public boolean hasNext() {
            return counter < currentSize;
        }

        public E next() {
            if (!hasNext () ) 
                throw new NoSuchElementException();
            return pQueue[counter++];
        }
    }
} 
