import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue()                 // construct an empty randomized queue
    public boolean isEmpty()                 // is the randomized queue empty?
    public int size()                        // return the number of items on the randomized queue
    public void enqueue(Item item) throws java.lang.IllegalArgumentException          // add the item
    public Item dequeue() throws java.util.NoSuchElementException                    // remove and return a random item
    public Item sample() throws java.util.NoSuchElementException                     // return a random item (but do not remove it)
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    public static void main(String[] args)   // unit testing (optional)

    private class Iterant() {
        public void remove() throws java.lang.UnsupportedOperationException
    }
}