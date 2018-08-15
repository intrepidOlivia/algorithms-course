import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;   // The array to store the items
    private int count;      // keeps track of how many items are stored

    /**
     * Constructs an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[10];
        count = 0;
    }

    /**
     * @return true if the queue is currently empty
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * @return the number of items currently held in the randomized queue
     */
    public int size() {
        return count;
    }

    /**
     * Adds the item to the randomized queue.
     * @param item The item to be added.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item is null.");
        }

        // Adds item to the queue (where?)
        items[count] = item;
        count++;

        if (count > items.length / 2) {
            resize(items.length * 2);
        }
    }

    /**
     * Removes an item at random from the randomized queue and returns it.
     * @return A random item
     */
    public Item dequeue() {
        if (count <= 0) {
            throw new NoSuchElementException("The queue is empty.");
        }

        int index = StdRandom.uniform(0, count);

        Item item = items[index];
        items[index] = items[count - 1];
        items[count - 1] = null;
        count--;
        if (count < items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Returns a random item from the queue (but does not remove it)
     * @return a random item from the queue.
     */
    public Item sample() {
        if (count <= 0) {
            throw new NoSuchElementException("Queue is empty.");
        }

        int index = StdRandom.uniform(0, items.length);
        if (items[index] == null) {
            return sample();
        }

        return items[index];
    }

    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < count; i++) {
            copy[j++] = items[i];
        }
        items = copy;
    }

    private class RandQueueIterator implements Iterator<Item> {

        int current = 0;

        public boolean hasNext() {
            return items[current] != null;
        }

        public Item next() {
            return items[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args)  {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        System.out.println("Adding a bunch of items to queue.");

        // Add a bunch of items to the queue
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);

        q.iterateStack(q);

        // Remove some at random
        System.out.println("Randomly removing item: " + q.dequeue());
        System.out.println("Randomly removing item: " + q.dequeue());
        System.out.println("Randomly removing item: " + q.dequeue());

        q.iterateStack(q);

        // check the size
        System.out.println("Expected size: 2. Actual size: " + q.size());

        System.out.println("Adding more items.");
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);
        q.enqueue(10);

        q.iterateStack(q);
    }

    private void iterateStack(RandomizedQueue<Integer> q) {
        for (int i : q) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}