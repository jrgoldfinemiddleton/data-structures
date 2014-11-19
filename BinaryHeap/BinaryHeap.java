import java.util.Arrays;

/**
 * An array-based implementation of the binary heap ADT.  Stores only
 * positive integers and allows duplicate keys.  Maintains the heap-order
 * property.
 * 
 * @author Jason Goldfine-Middleton
 * @version 1.0 11/17/14
 */
public class BinaryHeap {

  // STATIC FIELDS
  
  /** Default size for heap */
  private static final int HEAP_SIZE = 16;
  
  /** Constant for empty node */
  private static final int EMPTY = -1;
  
  // STATIC METHODS
  
  /** Creates a new binary heap containing the given integer keys. */
  public static BinaryHeap bottomUpHeap(int[] keys) {
    BinaryHeap heap = new BinaryHeap();
    if (keys.length < 1) {
      return heap;
    }
    if (keys.length == 1) {
      heap.insert(keys[0]);
      return heap;
    }

    if (keys.length > HEAP_SIZE - 1) {
      heap.tree = new int[keys.length + 1];
      heap.tree[0] = EMPTY; // make the first spot empty
    }
    heap.lastLeaf = heap.tree.length - keys.length;
    System.arraycopy(keys, 0, heap.tree, heap.lastLeaf, keys.length);
    int index = heap.lastLeaf;
    while (index < heap.tree.length) {
      int left = heap.leftChild(index);
      int right = heap.rightChild(index);
      if (left >= heap.lastLeaf) { // has left leaf
        if (right >= heap.lastLeaf) { // has right leaf
          if (heap.tree[left] <= heap.tree[right]) {
            if (heap.tree[left] < heap.tree[index]) {
              heap.swapKeys(left, index);
              index = left;
            } else {
              index++;
            }
          } else { // right child less than left child
            if (heap.tree[right] < heap.tree[index]) {
              heap.swapKeys(right, index);
              index = right;
            } else {
              index++;
            }
          }
        } else { // only has left leaf
          if (heap.tree[left] < heap.tree[index]) {
            heap.swapKeys(left, index);
            index = left;
          } else {
            index++;
          }
        }
      } else { // no left leaf, move to next index
        index++;
      }
    }
    return heap;
  }
  
  /** Factory method for creating an empty BinaryHeap. */
  public static BinaryHeap createNewHeap() {
    return new BinaryHeap();
  }

  /** Quickly sorts an array of positive integers using a heap. */
  public static int[] heapSort(int[] keys) {
    BinaryHeap heap = bottomUpHeap(keys);
    int[] sorted = new int[keys.length];
    for (int i = 0; i < keys.length; i++) {
      sorted[i] = heap.removeMin();
    }
    return sorted;
  }

  /** Tests the various methods in BinaryHeap.java. */
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Creating new binary heap:");
    BinaryHeap heap = createNewHeap();
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
    BinaryHeap heap2 = bottomUpHeap(keys);
    System.out.println(heap2);
    System.out.println(heap2.treeToString());
    
    System.out.println("\nConstructing new bottom-up heap:");
    System.out.println("Input keys: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, " +
         "11, 12, 13, 14, 15, 16, 17, 18, 19, 20");
    keys = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                      11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
    BinaryHeap heap3 = bottomUpHeap(keys);
    System.out.println(heap3);
    System.out.println(heap3.treeToString());
    
    System.out.println("\nTesting heapsort.");
    keys = new int[]{ 5, 34, 56, 232, 456, 23, 545, 4, 5, 17, 34, 24, 89, 1 };
    System.out.println("Input: " + Arrays.toString(keys));
    System.out.println("Output: " + Arrays.toString(heapSort(keys)));
  }

  // INSTANCE FIELDS
  
  /** Array representation of binary heap. */
  private int[] tree;

  /** Index of current rightmost leaf */
  private int lastLeaf;
  
  // METHODS

  /** Constructor for new binary heap with default size. */
  private BinaryHeap() {
    tree = new int[HEAP_SIZE];
    for (int i = 0; i < HEAP_SIZE; i++) {
      tree[i] = EMPTY;
    }
    lastLeaf = HEAP_SIZE; // indicates that the heap is empty (no root)
  }

  /** 
   * Bubbles the root key down to satisfy the heap-order property.  Used
   * by the removeMin() method. */
  private void bubbleDown() {
    int index = tree.length - 1;
    while (leftChild(index) >= lastLeaf) { // there are children
      int left = leftChild(index);
      int right = rightChild(index);
      if (right < lastLeaf) { // there is no right leaf
        if (tree[left] < tree[index]) {
          swapKeys(left, index);
          index = left;
        } else {
          break;
        }
      } else { // there is a right leaf
        if (tree[left] < tree[right]) {
          if (tree[left] < tree[index]) {
            swapKeys(left, index);
            index = left;
          } else {
            break;
          }
        } else { 
          if (tree[right] < tree[index]) {
            swapKeys(right, index);
            index = right;
          } else {
            break;
          }
        }
      }
    }
  }

  /** 
   * Helper method for insert(int), which moves a leaf's key up the tree
   * until the heap-order property is satisfied.
   * 
   * This method is only to be used after a new key is inserted at "lastLeaf".
   */
  private void bubbleUp() {
    int index = lastLeaf; // index of key just inserted
    while (parent(index) < tree.length && tree[index] < tree[parent(index)]) {
      swapKeys(index, parent(index));
      index = parent(index);
    }
  }

  /** Inserts a key into the heap, maintaining the heap-order property. */
  public void insert(int key) {
    if (lastLeaf == 1) { // if the heap has run out of space, double its size
      int[] newTree = new int[tree.length * 2];
      System.arraycopy(tree, 0, newTree,
          tree.length, tree.length);
      tree = newTree;
      for (int i = 0; i < tree.length; i++) {
        tree[i] = EMPTY;
      }
      lastLeaf += tree.length;
    }
    tree[lastLeaf - 1] = key;
    lastLeaf--;
    bubbleUp();
  }

  /** Returns the index of the left child of the node at "index". */
  private int leftChild(int index) {
    int revIndex = tree.length - index;
    return tree.length - (2 * revIndex);
  }

  /** Returns the key with the minimum value in the heap. */
  public int min() {
    return tree[tree.length - 1];
  }

  /** Returns the current number of keys stored in the heap. */
  public int numKeys() {
    return tree.length - lastLeaf;
  }

  /** Returns the index of the parent node to the node at "index". */
  private int parent(int index) {
    int revIndex = tree.length - index;
    return tree.length - (int) Math.floor(revIndex / 2.0);
  }

  /** Removes the smallest key from the heap and returns it. */
  public int removeMin() {
    if (numKeys() == 0) {
      return -1;
    }
    int min = min();
    tree[tree.length - 1] = tree[lastLeaf];
    tree[lastLeaf] = EMPTY;
    lastLeaf++;
    bubbleDown();
    return min;
  }

  /** Returns the index of the right child of the node at "index". */
  private int rightChild(int index) {
    int revIndex = tree.length - index;
    return tree.length - (2 * revIndex + 1);
  }

  /** Returns the max number of keys allowed in the heap. */
  public int size() {
    return tree.length - 1;
  }

  /** 
   * Returns a string representation of a subtree of the heap with root
   * "index". */
  private String subTreeToString(int index) {
    if (index <= 0 || index >= tree.length || tree[index] == EMPTY) {
      return "";
    }
    if (leftChild(index) > 0 && tree[leftChild(index)] == EMPTY) {
      return "" + tree[index];
    }
    StringBuilder s = new StringBuilder();
    if (leftChild(index) > 0 && tree[leftChild(index)] != EMPTY) {
      s.append("(" + subTreeToString(leftChild(index)) + ")");
    }
    s.append(tree[index]);
    if (rightChild(index) > 0 && tree[rightChild(index)] != EMPTY) {
      s.append("(" + subTreeToString(rightChild(index)) + ")");
    }
    return s.toString(); 
  }
  
  /** Swaps the keys at the specified node indices. */
  private void swapKeys(int index1, int index2) {
    int temp = tree[index1];
    tree[index1] = tree[index2];
    tree[index2] = temp;
  }

  /** Returns a string representation of the array containing the heap. */
  @Override
  public String toString() {
    return Arrays.toString(tree);
  }

  /** Returns a string representation of the heap in tree format. */
  public String treeToString() {
    String s = subTreeToString(tree.length - 1);
    return s;
  }
}
