import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node head;
    private Node tail;
    private int size;
    
    private class Node {
    
        public Item data;
        public Node next;
        public Node prev;
        
        public Node(Item data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    
    }
    
    public Deque() {                          // construct an empty deque
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public boolean isEmpty() {                 // is the deque empty?
        return this.size == 0;
    }
    
    public int size() {                        // return the number of items on the deque
        return this.size;
    }
    
    public void addFirst(Item item) {          // add the item to the front
    
    if (item == null) { throw new IllegalArgumentException(); }
    
        if (this.head == null) {
            this.head = new Node(item);
            this.tail = this.head;
        } else {
            Node temp = new Node(item);
            temp.next = this.head;
            this.head.prev = temp;
            this.head = temp;
        }
        size++;
    }
    
    public void addLast(Item item) {          // add the item to the end
    
    if (item == null) { throw new IllegalArgumentException(); }
    
    if (this.tail == null) {
            this.tail = new Node(item);
            this.head = this.tail;
        } else {
            Node temp = new Node(item);
            this.tail.next = temp;
            temp.prev = this.tail;
            this.tail = temp;
        }
        size++;
    }
    
    public Item removeFirst() {               // remove and return the item from the front
        if (this.isEmpty()) { throw new NoSuchElementException(); }
        
        Item retData = this.head.data;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        size--;
        return retData;
    }
    
    public Item removeLast() {                 // remove and return the item from the end
        if (this.isEmpty()) { throw new NoSuchElementException(); }
        
        Item retData = this.tail.data;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        size--;
        return retData;
    }
    
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = head;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item data = current.data;
            current = current.next; 
            return data;
        }
    }
    
    
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<>();
        System.out.println(test.isEmpty());

        for (int i = 0; i < 10; i++) {
            test.addLast(i);
        }

        System.out.println(test.isEmpty());
        test.removeFirst();
        Iterator<Integer> testIter = test.iterator();
        while (testIter.hasNext()) {
            System.out.println(testIter.next());
        }
        System.out.println(test.removeLast());
        System.out.println("Deque size: " + test.size());
        testIter = test.iterator();
        while (testIter.hasNext()) {
            System.out.println(testIter.next());
        }
        
        while (test.size() > 0) {
            testIter = test.iterator();
            System.out.println("Iterator output:");
            while (testIter.hasNext()) {
                System.out.print(testIter.next() + " ");
            }
            System.out.println("Deque Size: " + test.size());
            System.out.println(test.removeLast());
        }
        
    }
}