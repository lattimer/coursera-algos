import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] arr;
    private int size;

    public RandomizedQueue() {                 // construct an empty randomized queue
        this.arr = (Item[]) new Object[2];
        this.size = 0;
    }
    
    public boolean isEmpty() {                 // is the randomized queue empty?
        return (this.size == 0);
    }
    
    public int size() {                        // return the number of items on the randomized queue
        return this.size;
    }
    
    public void enqueue(Item item) {          // add the item
    
        if (item == null) { throw new IllegalArgumentException(); }
        
        if (arr.length == size) {
            this.resize(arr.length * 2);
        }
        arr[size] = item;
        size++;
    }
    
    public Item dequeue() {                    // remove and return a random item
    
        if (this.isEmpty()) { throw new NoSuchElementException(); }
    
        int loc = StdRandom.uniform(0, this.size);
        Item temp = arr[loc];
        if (this.size > 1) {
            arr[loc] = arr[this.size - 1];
        }
        arr[this.size - 1] = null;
        this.size--;
        if (arr.length / 4 == size && arr.length / 2 >= 1) {
            this.resize(arr.length / 2);
        }
        return temp;
    }
    
    public Item sample() {                     // return a random item (but do not remove it)
    
        if (this.isEmpty()) { throw new NoSuchElementException(); }
        
        int loc = StdRandom.uniform(0, size);
        return arr[loc];
    }
    
    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private final Item[] copy = (Item[]) new Object[size];
        private int loc;
        
        public ListIterator() {
            for (int i = 0; i < size; i++) {
                copy[i] = arr[i];
            }
            StdRandom.shuffle(copy);
            loc = 0;
        }
        
        public boolean hasNext()  { return loc != size; }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item temp = copy[loc];
            loc++;
            return temp;
        }
        
    }
    
    private void resize(int newSize) {
        Item[] tempArr = (Item[]) new Object[newSize];
        for (int i = 0; i < this.size(); i++) {
            tempArr[i] = this.arr[i];
        }
        this.arr = tempArr;
    }
    
    public static void main(String[] args) {
        
        // RandomizedQueue test = new RandomizedQueue();
        // for (int i = 0; i < 128; i++) {
        //     System.out.println("Array size: " + test.arr.length);
        //     test.enqueue(i);
        // }
        
        // for (int i = 0; i < 128; i++) {
        //     System.out.println("Array size: " + test.arr.length + "- # of Elements: " + test.size());
        //     test.dequeue();
        // }
    }
}