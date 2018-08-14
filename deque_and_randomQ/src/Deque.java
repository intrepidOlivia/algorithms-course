import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first; // For nodes pushed onto the First end of the deque
    private Node last;  // For nodes pushed onto the Last end of the deque
    private int count = 0;

    /**
     * // constructs an empty deque
     */
    public Deque() {

    }

    /**
     * @return true if the deque is empty
     */
    public boolean isEmpty() {
        return first == null || last == null;
    }

    /**
     * @return the number of items currently in the deque.
     */
    public int size() {
        return count;
    }

    /**
     * Adds an item to the front of the deque.
     * @param item The item to be added.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The provided item was null.");
        }

        if (first == null) {
            first = new Node<>(item, null, null);
            last = first;
        } else {
            Node newFirst = new Node<>(item, null, first);
            first.changeNext(newFirst);
            first = newFirst;
        }
        count++;
    }

    /**
     * Adds an item to the end of the deque.
     * @param item The item to be added.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The provided item was null.");
        }

        if (last == null) {
            last = new Node<>(item, null, null);
            first = last;
        } else {
            Node newLast = new Node<>(item, last, null);
            last.changePrevious(newLast);
            last = newLast;
        }

        count++;
    }

    /**
     * @return The item removed and retrieved from the First side of the deque
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }

        Node retrieved = first;
        if (first.previous != null) {
            first = first.previous;
            first.changeNext(null);
        } else {
            first = null;
        }
        count--;
        return (Item) retrieved.item;
    }

    /**
     * @return the item removed and retrieved from the Last side of the deque
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }

        Node retrieved = last;
        if (last.next != null) {
            last = last.next;
            last.changePrevious(null);
        } else {
            last = null;
        }
        count--;
        return (Item) retrieved.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = (Item) current.item;
            current = current.previous;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<Item> {
        Item item;
        Node next;      // Next means: item toward the First end from this node.
        Node previous;  // Previous means: item toward the Last end from this node.

        public Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Changes the Node's "next" value to the provided item
         * @param node The item to link that was added closer to First
         */
        public void changeNext(Node node) {
            this.next = node;
        }

        /**
         * Changes the Node's "previous" value to the provided item
         * @param node The item to link that was added closer to Last
         */
        public void changePrevious(Node node) {
            this.previous = node;
        }
    }

    public static void main(String[] args) {
        // create new Deque
        Deque<Integer> d = new Deque<>();

        // add some items to the Deque at the front
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);

        // adds some items to the Deque at the back
        d.addLast(4);
        d.addLast(5);
        d.addLast(6);

        // check count
        System.out.println("The Deque is expected to have 6 items. Actual value: " + d.size());

        // iterate through them with for-each
        System.out.println("Iteration expected to output 3 2 1 4 5 6:");
        for (int i : d) {
            System.out.print(i + " ");
        }
        System.out.println();

        // remove items from front
        d.removeFirst();
        d.removeFirst();

        // remove items from back
        d.removeLast();

        // check count
        System.out.println("expected size: 3. Actual size: " + d.size());

        // Iterate again
        System.out.println("Expected iteration output: 1 4 5");
        for (int i : d) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Retrieve an item
        System.out.println("Retrieving item (5?) from last: " + d.removeLast());
        d.removeFirst();

        System.out.println("One item left? " + d.size());

        System.out.println("Removing from last: " + d.removeLast());

    }
}