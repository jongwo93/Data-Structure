import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a max heap.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The initial array before the Build Heap algorithm takes place should
     * contain the data in the same order as it appears in the ArrayList.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        
        for (T d : data) {
            if (d == null) {
                throw new IllegalArgumentException("element in data is null");
            }
            backingArray[size + 1] = data.get(size);
            size++;
        }
        for (int parent = size / 2; parent >= 1; parent--) {
            while ((parent * 2) <= size) {
                int child = parent * 2;
                if (backingArray[child + 1] != null
                        && backingArray[child].compareTo(
                                backingArray[child + 1]) < 0) {
                    child++;
                }
                if (backingArray[parent].compareTo(backingArray[child]) < 0) {
                    T temp = backingArray[parent];
                    backingArray[parent] = backingArray[child];
                    backingArray[child] = temp;
                    parent = child;
                } else {
                    break;
                }
                
            }
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null, add");
        }
        if (size >= backingArray.length - 1) {
            T[] tempArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }
        size++;
        backingArray[size] = item;
        int parent = size / 2;
        int cur = size;
        while (parent != 0) {
            if (backingArray[parent].compareTo(item) < 0) {
                T temp = backingArray[parent];
                backingArray[parent] = item;
                backingArray[cur] = temp;
                cur = parent;
                parent = parent / 2;
            } else {
                return;
            }
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        T retVal = backingArray[1];
        if (backingArray[size] == retVal) {
            backingArray[size] = null; 
            size--;
            return retVal;
        } else {
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            
            int parent = 1;
            while ((parent * 2) <= size) {
                int child = parent * 2;
                if (backingArray[child + 1] != null 
                        && backingArray[child].compareTo(
                                backingArray[child + 1]) < 0) {
                    child++;
                }
                if (backingArray[parent].compareTo(backingArray[child]) < 0) {
                    T temp = backingArray[parent];
                    backingArray[parent] = backingArray[child];
                    backingArray[child] = temp;
                    parent = child;
                } else {
                    return retVal;
                }
            }
            return retVal;
        }
    }

    @Override
    public boolean isEmpty() {
        // DO NOT MODIFY THIS METHOD!
        return size == 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}
