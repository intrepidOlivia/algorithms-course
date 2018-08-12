import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    public Deque()                           // construct an empty deque
    public boolean isEmpty()                 // is the deque empty?
    public int size()                        // return the number of items on the deque
    public void addFirst(Item item) throws java.lang.IllegalArgumentException          // add the item to the front
    public void addLast(Item item)  throws java.lang.IllegalArgumentException         // add the item to the end
    public Item removeFirst() throws java.util.NoSuchElementException                // remove and return the item from the front
    public Item removeLast() throws java.util.NoSuchElementException                 // remove and return the item from the end
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    public static void main(String[] args)   // unit testing (optional)

    private class Iterant() {

        public Item next() throws java.util.NoSuchElementException
        public void remove() throws java.util.NoSuchElementException
    }
}