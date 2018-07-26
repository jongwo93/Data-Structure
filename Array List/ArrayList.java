
/**
 * Your implementation of an ArrayList.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     * Initialize the backingArray with INITIAL_CAPACITY and size as 0.
     * 
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) { 
            throw new IllegalArgumentException("Data is null, addAtIndex");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is negative "
                    + "or index > size");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            if (size >= backingArray.length) {
                T[] tempArray = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < backingArray.length; i++) {
                    tempArray[i] = backingArray[i];
                }
                backingArray = tempArray;
            }
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
            size++;
        }
 
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, addToFront");
        }
        if (size >= backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i + 1] = backingArray[i];
            }
            backingArray = tempArray;
        } else {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
        }
        backingArray[0] = data;
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, addToBack");
        }
        if (size >= backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }
        backingArray[size] = data;
        size++;
        
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index not valid, either "
                    + "negative or bigger than size of list");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size) {
            return removeFromBack();
        } else {
            T retVal = backingArray[index];
            
            for (int i = index + 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            return retVal;
        }
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        } else {
            T retVal = backingArray[0];
            
            for (int i = 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            
            return retVal;
        }
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else {
            T retVal = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return retVal;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index not valid, either "
                    + "negative or bigger than size of the list");
        }
        return backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
