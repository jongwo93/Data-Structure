import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
/**
 * Your implementations of various string searching algorithms.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class PatternMatching {
 
    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> bruteForce(CharSequence pattern,
        CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 
                || text == null || comparator == null) {
            throw new IllegalArgumentException("boyerMoore, "
                    + "one of parameters is invalid");
        }
        List<Integer> retList = new ArrayList<>();
        if (pattern.length() > text.length() || text.length() == 0) {
            return retList;
        }
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = 0;
            while (j < pattern.length() && comparator.
                    compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == pattern.length()) {
                retList.add(j);
                i = pattern.length() + i - 1;
            } else {
                i++;
            }
        }
       
        return retList;
    }
 
    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null 
                || comparator == null) {
            throw new IllegalArgumentException("one of parameters not valid"); 
        }
        List<Integer> retList = new ArrayList<>();
        if (pattern.length() > text.length() || text.length() == 0) {
            return retList;
        }
        int[] failTable = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.
                    compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    retList.add(i);
                }
                int nextAlignment = failTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
       
       
        return retList;
    }
 
    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("pattern or comparator is null");
        }
        int[] failTable = new int[pattern.length()];
        int i = 0;
        int j = 1;
        failTable[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                failTable[j] = i;
                j++;
            } else {
                if (i == 0) {
                    failTable[j] = 0;
                    j++;
                } else {
                    i = failTable[i - 1];
                }
            }
        }
        return failTable;
    }
 
    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                       CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null 
                || comparator == null) {
            throw new IllegalArgumentException("boyerMoore, "
                    + "one of parameters is invalid");
        }
        List<Integer> retList = new ArrayList<>();
        if (pattern.length() > text.length() || text.length() == 0) {
            return retList;
        }
 
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.
                    compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
           
            if (j == -1) {
                retList.add(i);
                i++;
            } else {
                int shiftedIndex = lastTable.
                        getOrDefault(text.charAt(i + j), -1);
                if (shiftedIndex < j) {
                    i = i + (j - shiftedIndex);
                } else {
                    i++;
                }
            }
        }
       
       
        return retList;
    }
 
    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        Map<Character, Integer> bmLastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {   
            char c = pattern.charAt(i);
            if (!bmLastTable.containsKey(c)) {
                bmLastTable.put(c, i);
            } else if (bmLastTable.get(c) < i) {
                bmLastTable.replace(c, i);
            }
        }
        return bmLastTable;
    }
 
}