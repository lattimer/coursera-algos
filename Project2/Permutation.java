import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        
        RandomizedQueue<String> test = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {
            test.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(test.dequeue());
        }
        
    }   
}