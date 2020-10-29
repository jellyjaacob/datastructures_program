/*
Program #1
Creating a Priority Queue for Ordered Arrays using Interface and Comparable
CS310-01
2/11/20
@author Jacob Le cssc1238
RED ID: 822652578
*/

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class OrderedArrayPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {

    private E [] pQueue; //array for priority queue, initialized
    private int currentSize,maxSize; //current size of the array and max size of array
    
    // basic constructor
    public OrderedArrayPriorityQueue() {
        this(DEFAULT_MAX_CAPACITY);
    }

    // constructor takes a single integer parameter that represents the maximum capacity of the PQ
    public OrderedArrayPriorityQueue (int maxCapacity) {
        
        maxSize = maxCapacity; // assigns size to be the max
        currentSize = 0; // assigns current size to 0
        pQueue = (E[]) new Comparable[maxCapacity]; //creating the array to use
    }

    //  Inserts a new object into the priority queue
    public boolean insert(E object) {
        
        // We want to binary search, and compare that middle value to our object
        // If it is =, then insert at that spot. If not then we use compareTo to check if we go left or right
        // Rinse and repeat until we find that value we get from binary search is = object

        if(isFull() ) // checks if PQ is full and returns false
            return false;

        int where = BinarySearch(object, 0, currentSize-1); // assigns where to do a binary search
        
        for (int i = currentSize-1; i >= where; i--)
            pQueue[i+1] = pQueue[i];
        pQueue[where] = object;
        currentSize++;    
        return true;
    } 
   
    public E remove() { // Removes the object of highest priority that has been in the PQ the longest

        if (isEmpty()) 
            return null;
        return pQueue[--currentSize]; 
    }
    
    public boolean delete(E obj) { // delete all instances of obj from PQ 

        // binary search and compare the values, go left or right if they aren't =
        // continue comparing the values and once you find the obj is =, delete those instances

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
        if(isEmpty()) 
            return null;
        return pQueue[currentSize-1]; // returns the first element that is the highest priority
    }

    public boolean contains(E obj) {
        //  Returns the object if it is currently in the PQ.

        int where = BinarySearch(obj, 0, currentSize-1);

        if ( ((Comparable<E>)pQueue[where]).compareTo(obj) == 0) 
            return true;
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
    // binary search method incorporating Comparable
    public int BinarySearch(E object, int lo, int hi) {
        if (hi < lo )
            return lo;
        
        int mid = (lo+hi)/2;

        if ( ((Comparable<E>)object).compareTo(pQueue[mid]) >= 0) 
            return BinarySearch(object, lo, mid-1); // go left
        return BinarySearch(object, mid+1, hi); // go right
    }
}

