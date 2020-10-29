
/*
    Program 2
    CS310-01
    Description: Create an implementation for an unordered linked list
    3/2/20
    @author Jacob Le cssc1238
    Red ID: 822652578
 */

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

@SuppressWarnings("unchecked")
public class UnorderedLinkedListPriorityQueue <E extends Comparable <E>> implements PriorityQueue<E> {

    public static final int DEFAULT_MAX_CAPACITY = 1000;

    private class Node<E> { // node object
        E data;
        Node<E> next;

        public Node(E d) {
            data = d;
            next = null;
        }
    }

    private Node<E> head;
    private int currentSize;
    private long modificationCounter;

    public UnorderedLinkedListPriorityQueue() {
        head = null;
        currentSize = 0;
        modificationCounter = 0;
    }

    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E object) {

        /*Node<E> newNode = new Node<E>(object);
        if (head == null) 
            head = newNode;
        else {
            newNode.next = head;
            head = newNode;
            currentSize++;
            modificationCounter++;
            return true;
        } */
        Node<E> newNode = new Node<E>(object);
        Node<E> prev = null, curr = head;
        while (curr != null && ((Comparable<E>)object).compareTo(curr.data) > 0) {
            prev = curr;
            curr = curr.next;
        }
        if (prev == null) {
            newNode.next = head;
            head = newNode;
      } else {
            prev.next = newNode;
            newNode.next = curr;
        }
        modificationCounter++;
        currentSize++;
        return true;
    }
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
        if (isEmpty())  {
            return null;
        }
        /*
        Node<E> prev = null, minPrev = null, curr = head, minCurr = head;

        while (curr.next != null) { // traverse thru list
            if ((minCurr.data).compareTo(curr.data) >= 0) { 
                minCurr = curr; 
                minPrev = prev; 
            }
            prev = curr; // moves prev to curr
            curr = curr.next; // moves to the next node
        }
        if (minCurr == head)
            head = head.next;
        else {
            minPrev.next = minCurr.next;
        }
        currentSize--;
        modificationCounter++;
        return minCurr.data; */

        Node<E> curr = head, prev = null, ref = null, hiPQ = head;
        while (curr != null) {
            if (hiPQ.data.compareTo(curr.data) >= 0) {
                hiPQ = curr;
                ref = prev;
            }
            prev = curr;
            curr = curr.next;
        }
        if (hiPQ == head)
            head = head.next;
        else
            ref.next = hiPQ.next;
        currentSize--;
        modificationCounter++;
        return hiPQ.data;
    }
    // Deletes all instances of the parameter obj from the PQ if found, and
    // returns true. Returns false if no match to the parameter obj is found. 
    public boolean delete(E obj) {
        if (isEmpty())
            return false;
        while (head != null && ((Comparable<E>)obj).compareTo(head.data) == 0) {
            head = head.next;
            currentSize--;
        }
        if (head == null)
            return false;
        Node<E> curr = head, next = head.next;
        int count = 0;

        while (curr.next != null) {
            if (((Comparable<E>)obj).compareTo(curr.next.data) == 0) {
                count++; // keeps track of how many times obj is deleted
                curr.next = curr.next.next; // removes obj
                currentSize--;
            } else
                curr = curr.next;
        }
        /* while (curr != null) {
            if (((Comparable<E>)obj).compareTo(curr.data) == 0) {
                count++; // keeps track of how many times obj is deleted
                curr.next = next.next; // removes obj
                currentSize--;
            } else 
                curr = curr.next;
        } */

        modificationCounter++;
        if (count > 0) // if obj was removed, then return true;
            return true;
        return false;
        // While statement traverses thru list, use comparable to check the 2, if true
        // count++ and remove the obj and moves to the next instance of it
        // if count > 0, then return true bc item was removed
    }
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it.
    //  Returns null if the PQ is empty.
    public E peek() {

        if (isEmpty())  {
            return null;
        }

        E hiPQ = head.data; // to hold highest PQ
        Node<E> curr = head;
        Node<E> next = head.next;

        while (curr.next != null) { // traverse thru list
            if ((curr.data).compareTo(hiPQ) <= 0) { // compare curr to next of priority
                hiPQ = curr.data; // returns the element with highest priority
            }
            curr = curr.next; // moves to the next node
        }
    return hiPQ;
    }

    private E removeFirst() {
        if (head == null)
            return null;
        else
            head = head.next;
        E tmp = head.data;
        currentSize--;
        modificationCounter++;
        return tmp;
    }

    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj) {

        Node<E> curr = head;

        while (curr != null) {
            if (((Comparable<E>)curr.data).compareTo(obj) == 0) {
                return true;
            }
            curr = curr.next; // move to next variable
        }
        return false;

    }
    //  Returns the number of objects currently in the PQ.
    public int size() {
        return currentSize;
    }
    //  Returns the PQ to an empty state.
    public void clear() {
        head = null;
    }
    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() {
        return size() == 0;
    }
    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull() {
        return false; // LinkedLists can never be full, always expanding
    }
    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.
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
}
