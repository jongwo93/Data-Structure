import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
 
/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class Sorting {
 
    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            boolean sorted = true;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    sorted = false;
                }
            }
            if (sorted) {
                return;
            }
        }
    }
 
    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 
                    && comparator.compare(arr[j - 1], arr[j]) > 0; j--) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
            }
        }
    }
 
    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
 
                }
            }
            if (min != i) {
                T temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
 
        }
    }
 
    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
    }
     
    /**
     * quick sort helper method that would recursively find the cut of 
     * each partitions
     * @param arr the array to be sorted
     * @param comparator the comparator used to compare the data
     * @param rand the random object used to select pivots
     * @param <T> data type to sort
     * @param l the starting index of portion
     * @param r the end index of portion
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, 
            Random rand, int l, int r) {
        if (l < r) {
            int pivotIndex = rand.nextInt(r - l) + l;
            int cut = qsPartition(arr, comparator, rand, l, r, pivotIndex);
           
            quickSortHelper(arr, comparator, rand, l, cut - 1);
            quickSortHelper(arr, comparator, rand, cut + 1, r);
        }
    }
   
    /**
     * quick sort partitioning method
     * @param arr the array to be sorted
     * @param comparator the comparator used to compare the data
     * @param rand the random object used to select pivots
     * @param <T> data type to sort
     * @param l the starting index of portion
     * @param r the end index of portion
     * @param pivotIndex the pivot index
     * @return the new pivot index
     */
    private static <T> int qsPartition(T[] arr, Comparator<T> comparator, 
            Random rand, int l, int r, int pivotIndex) {
        T pivotValue = arr[pivotIndex];
        arr[pivotIndex] = arr[r];
        arr[r] = pivotValue;
       
        int newPiv = l;
        int index = l;
        while (index < r) {
            if (comparator.compare(arr[index], pivotValue) < 0) {
                T temp = arr[index];
                arr[index] = arr[newPiv];
                arr[newPiv] = temp;
                newPiv++;
               
            }
            index++;
        }
       
        T newPivot = arr[newPiv];
        arr[newPiv] = arr[r];
        arr[r] = newPivot;
       
        return newPiv;
    }
 
    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        mergeSortHelper(arr, comparator, 0, arr.length - 1);
    }
   
    /**
     * Merge Sort helper method that recursively split arrays
     * @param arr the array to be sorted
     * @param comparator the comparator used to compare the data in arr
     * @param <T> data type to sort
     * @param l the starting index of left
     * @param h the ending index of right
     */
    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator, 
            int l, int h) {
        if (l < h) {
            int m = (l + h) / 2;
            mergeSortHelper(arr, comparator, l, m);
            mergeSortHelper(arr, comparator, m + 1, h);
            merge(arr, comparator, l, m, h);
        }
 
    }
   
    /**
     * Merge method that merges splited arrays back into original array sorted
     * @param arr the array to be sorted
     * @param comparator the comparator used to compare the data in arr
     * @param <T> data type to sort
     * @param l the starting index of left
     * @param m the starting index of right / middle index
     * @param h the ending index of right
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator, 
            int l, int m, int h) {
       
        T[] temp = (T[]) new Object[arr.length];
        for (int i = l; i <= h; i++) {
            temp[i] = arr[i];
        }
        int left = l;
        int mid = m + 1;
        int index = left;
        while ((left <= m) && (mid <= h)) {
            if (comparator.compare(temp[left], temp[mid]) <= 0) {
                arr[index] = temp[left];
                left++;
            } else {
                arr[index] = temp[mid];
                mid++;
            }
            index++;
        }
       
        while (left <= m) {
            arr[index] = temp[left];
            left++;
            index++;
        }
    }
    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("array is null");
        }
       
        List<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        if (arr.length > 0) {
            int max = arr[0];
            for (int i = 0; i < arr.length; i++) {
                if (max < Math.abs(arr[i])) {
                    max = Math.abs(arr[i]);
                }
            }
            int exp = 1;
            while (max > 0) {
                System.out.println("1:" + arr.length);
                for (int i = 0; i < arr.length; i++) {
                    System.out.println("2:" + (arr[i] / exp) % 10);
                    buckets[((arr[i] / exp) % 10) + 9].add(arr[i]);
                }
               
                int index = 0;
                for (int i = 0; i < buckets.length; i++) {
                    for (Integer d : buckets[i]) {
                        arr[index] = d;
                        index++;
                    }
                    buckets[i].clear();
                }
                exp = exp * 10;
                max = max / 10;
               
            }
       
        }
    }
}