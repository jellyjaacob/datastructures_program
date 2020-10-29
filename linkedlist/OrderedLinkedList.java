/*
    Program 2
    CS310-01
    Description: Create an implementation for an ordered linked list with priority
    3/2/20
    @author Jacob Le cssc1238
    Red ID: 822652578
 */

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

@SuppressWarnings("unchecked")
public class OrderedLinkedListPriorityQueue <E extends Comparable <E>> implements PriorityQueue<E> {

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

    public OrderedLinkedListPriorityQueue() {
        head = null;
        currentSize = 0;
        modificationCounter = 0;
    }

    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E object) {

        Node<E> newNode = new Node<E>(object);
        Node<E> prev = null, curr = head;
        while (curr != null && ((Comparable<E>)object).compareTo(curr.data) >= 0) {
            prev = curr;
            curr = curr.next;
        }
        if (prev == null) { // goes in 1st place/list is empty
            newNode.next = head;
            head = newNode;
        } else { // in middle/end
            prev.next = newNode;
            newNode.next = curr;
        }
        currentSize++;
        modificationCounter++;
        return true;
    }
    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
public E remove() {

        if (isEmpty())
            return null;

        E temp = head.data;
        head = head.next;
        currentSize--;
        modificationCounter++;
        return temp;
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

        Node<E> curr = head;
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

    // Returns the object of highest priority that has been in the
    // PQ the longest, but does NOT remove it.
    // Returns null if the PQ is empty.
    public E peek() {
        // returns the head;
        if (head == null)
            return null;
        return head.data;
    }
    // Returns true if the priority queue contains the specified element
    // false otherwise.
public boolean contains(E obj) {

        Node<E> curr = head;

        while (curr != null) {
            if (((Comparable<E>)obj).compareTo(curr.data) == 0) {
                return true;
            }
            curr = curr.next; // move to next variable
        }
        return false;
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

    // Returns an iterator of the objects in the PQ, in no particular
    // order.
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

