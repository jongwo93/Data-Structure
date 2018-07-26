/**
 * Your implementation of a circular singly linked list.
 *
 * @author NICK JONGWOO JANG
 * @userid jjang74 (e.g. gburdell3)
 * @GTID 903170765 (e.g. 900000000)
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(T data, int index) {
        if (index < 0 || index > size) { 
            throw new IndexOutOfBoundsException("index is negative "
                    + "or too big, addAtIndex");
        } else if (data == null) {
            throw new IllegalArgumentException("data is null, addAtIndex");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> cur = head;
            for (int i = 1; i < index; i++) {
                cur = cur.getNext();
            }
            LinkedListNode<T> toAdd = 
                    new LinkedListNode<T>(data, cur.getNext());
            cur.setNext(toAdd);
            size++;
            
            
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, addToFront");
        }
        if (isEmpty()) {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
            size++;
        } else {
            LinkedListNode<T> toAdd = 
                    new LinkedListNode<T>(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(toAdd);
            size++;
            
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, addToBack");
        }
        if (isEmpty()) {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
            size++;
        } else {
            LinkedListNode<T> toAdd = 
                    new LinkedListNode<T>(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(toAdd);
            head = head.getNext();
            size++;
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is negative or"
                    + "too big, removeAtIndex");
        }
        if (isEmpty()) {
            return null;
        }
        T retVal = null;
        if (index == 0) {
            retVal = removeFromFront();
        } else if (index == (size() - 1)) {
            retVal = removeFromBack();
        } else {
            LinkedListNode<T> cur = head;
            LinkedListNode<T> prev = null;
            
            for (int i = 0; i < index; i++) {
                prev = cur;
                cur = cur.getNext();
            }
            if (cur.getNext() == head) {
                prev.setNext(head);
                
            } else {
                prev.setNext(cur.getNext());
                
            }
            size--;
            retVal = cur.getData();
        }
        return retVal;
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        T retVal = head.getData();
        if (size == 1) {
            head = null;
        } else {
            if (head.getNext() != null) {
                head.setData(head.getNext().getData());
                if (head.getNext().getNext() != null) {
                    head.setNext(head.getNext().getNext());
                } else {
                    head.setNext(null);
                }
            }
        }
        size--;
        
        return retVal;
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        T retVal = null;
        if (size() == 1) {
            retVal = head.getData();
            head = null;
        } else {
            LinkedListNode<T> cur = head;
            LinkedListNode<T> prev = null;
            while (cur.getNext() != head) {
                prev = cur;
                cur = cur.getNext();
            }
            retVal = cur.getData();
            prev.setNext(head);
        }
        size--;
        return retVal;
    }

    @Override
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, "
                    + "removeLastOccurence");
        }
        if (isEmpty()) {
            return null;
        } 
        T retVal = null;
        if (size() == 1) { 
            if (head.getData() == data) { 
                retVal = head.getData();
                head = null;
                size--;
            }
            
        } else {
            LinkedListNode<T> cur = head;
            LinkedListNode<T> rem = null;
            LinkedListNode<T> prev = null;
            while (cur.getNext() != head) {
                if (cur.getData() == data) {
                    rem = prev;
                    retVal = cur.getData();
                }
                prev = cur;
                cur = cur.getNext();
            }
            if (cur.getData() == data) {
                rem = prev;
                retVal = cur.getData();
                removeFromBack();
            } else if (rem == null) {
                removeFromFront();
            } else {
                rem.setNext(rem.getNext().getNext());
                size--;
            }
            
        }
        
        return retVal;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is negative or too big,"
                    + "get");
        }
        T retVal = null;
        if (index == 0) {
            retVal = head.getData();
        } else {
            LinkedListNode<T> cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.getNext();
            }
            retVal = cur.getData();
        }
        return retVal;
    }

    @Override
    public Object[] toArray() {
        Object[] list = new Object[size];
        if (isEmpty()) {
            return list;
        }
        LinkedListNode<T> cur = head;
        for (int i = 0; i < size; i++) {
            list[i] = cur.getData();
            if (cur.getNext() != null) {
                cur = cur.getNext();
            }
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}