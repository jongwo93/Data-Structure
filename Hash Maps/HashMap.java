
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value is null");
        }
        double loadFactor = (double) (size + 1) / (double) table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        MapEntry<K, V> entry = new MapEntry<K, V>(key, value);
        int hashCode = Math.abs(key.hashCode() % table.length);
        V retVal = null;
        if (table[hashCode] == null) {
            table[hashCode] = entry;
            size++;
        } else {
            MapEntry<K, V> temp = table[hashCode];
            boolean exist = false;
            while (temp != null && !exist) {
                if (temp.getKey().equals(key)) {
                    exist = true;
                    retVal = temp.getValue();
                    temp.setValue(value);
                } else {
                    temp = temp.getNext();
                }
            }
            if (!exist) {
                entry.setNext(table[hashCode]);
                table[hashCode] = entry;
                size++;
            }
        }
        return retVal;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        V retVal = null;
        int hash = Math.abs(key.hashCode() % table.length);
        if (table[hash] == null) {
            throw new NoSuchElementException("key does not exist");
        } else {
            MapEntry<K, V> temp = table[hash];
            MapEntry<K, V> prev = null;
            while (temp != null) {
                if (temp.getKey().equals(key)) {
                    retVal = temp.getValue();
                    size--;
                    if (temp.getNext() == null) {
                        temp = null;
                    } else {
                        temp = temp.getNext();
                    }
                    if (prev == null) {
                        table[hash] = temp;
                    } else {
                        if (temp == null) {
                            prev.setNext(null);
                        } else {
                            prev.setNext(temp);
                        }
                    }
                    temp = null;
                }
                if (temp != null && temp.getNext() != null) {
                    prev = temp;
                    temp = temp.getNext();

                }
            }
            if (retVal == null) {
                throw new NoSuchElementException("key does not exist");
            }
        }
        return retVal;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int hash = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> temp = table[hash];
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return temp.getValue();
            } else {
                temp = temp.getNext();
            }
        }

        throw new NoSuchElementException("Key is not in the map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int hash = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> temp = table[hash];
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return true;
            } else {
                temp = temp.getNext();
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> retSet = new HashSet<>(size);
        for (MapEntry<K, V> temp : table) {
            while (temp != null) {
                retSet.add(temp.getKey());
                temp = temp.getNext();
            }
        }
        return retSet;
    }

    @Override
    public List<V> values() {
        List<V> retList = new ArrayList<>();
        for (MapEntry<K, V> temp : table) {
            while (temp != null) {
                retList.add(temp.getValue());
                temp = temp.getNext();
            }
        }
        return retList;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < 1 || length < size) {
            throw new IllegalArgumentException("length for resizing "
                    + "is not valid");
        }

        MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[length];
        int num = 0;
        for (MapEntry<K, V> entry : table) {
            while (entry != null && num < size) {
                int hash = Math.abs(entry.getKey().hashCode() % length);
                newTable[hash] = new MapEntry<K, V>(entry.getKey(),
                        entry.getValue(), newTable[hash]);
                num++;
                entry = entry.getNext();
            }
        }
        table = newTable;
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
