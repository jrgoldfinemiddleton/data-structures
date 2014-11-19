import java.util.Arrays;

/** Tests the various methods in BinaryHeap.java. */
public class BinaryHeapTester {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Creating new binary heap:");
    BinaryHeap heap = BinaryHeap.createNewHeap();
    System.out.println(heap);

    for (int i = 0; i < heap.size(); i++) {
      //Thread.sleep(2000);  // uncomment to make things more exciting
      int a = (int) (100 * Math.random());
      System.out.println("\nInserting key: " + a);
      heap.insert(a);
      System.out.println(heap);
      System.out.println(heap.treeToString());
    }

    System.out.println();

    int oldMin = 0;
    for (int i = 0; i < heap.size(); i++) {
      //Thread.sleep(2000);  // uncomment to make things more exciting
      if (oldMin > heap.min()) {
        System.out.println("HOUSTON, WE HAVE A PROBLEM!");
      }
      oldMin = heap.min();
      System.out.println("\nMinimum key: " + heap.min());
      System.out.println("Removing " + heap.min() + " from heap.");
      heap.removeMin();
      System.out.println(heap);
      System.out.println(heap.treeToString());
    }

    System.out.println("\nAttempting to removeMin() from empty heap:");
    System.out.println("Returned minimum key should be -1: " +
        heap.removeMin());

    System.out.println("\nConstructing new bottom-up heap:");
    System.out.println("Input keys: 4, 5, 7, 1, 9, 8, 2, 3, 9, 1");
    int[] keys = new int[]{ 4, 5, 7, 1, 9, 8, 2, 3, 9, 1 };
    BinaryHeap heap2 = BinaryHeap.bottomUpHeap(keys);
    System.out.println(heap2);
    System.out.println(heap2.treeToString());

    System.out.println("\nConstructing new bottom-up heap:");
    System.out.println("Input keys: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, " +
        "11, 12, 13, 14, 15, 16, 17, 18, 19, 20");
    keys = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
    BinaryHeap heap3 = BinaryHeap.bottomUpHeap(keys);
    System.out.println(heap3);
    System.out.println(heap3.treeToString());

    System.out.println("\nTesting heapsort.");
    keys = new int[]{ 5, 34, 56, 232, 456, 23, 545, 4, 5, 17, 34, 24, 89, 1 };
    System.out.println("Input: " + Arrays.toString(keys));
    System.out.println("Output: " + Arrays.toString(BinaryHeap.heapSort(keys)));
  }
}
