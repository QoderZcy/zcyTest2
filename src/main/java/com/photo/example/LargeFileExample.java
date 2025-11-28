package com.photo.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * This is a large example file created for demonstration purposes.
 * It contains multiple classes, methods, and extensive documentation.
 */
public class LargeFileExample {

    // ==================== Constants ====================
    
    private static final int MAX_CAPACITY = 10000;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final long TIMEOUT_MILLISECONDS = 30000L;
    private static final double PI_VALUE = 3.14159265358979323846;
    private static final String APPLICATION_NAME = "Large File Example Application";
    private static final String VERSION = "1.0.0";
    private static final String AUTHOR = "Example Author";
    private static final String COPYRIGHT = "Copyright (c) 2025";
    
    // ==================== Fields ====================
    
    private String name;
    private int identifier;
    private long timestamp;
    private double value;
    private boolean active;
    private List<String> items;
    private Map<String, Object> properties;
    private Set<Integer> uniqueNumbers;
    private Queue<String> messageQueue;
    private Stack<Integer> operationStack;
    
    // ==================== Constructors ====================
    
    public LargeFileExample() {
        this.name = "Default";
        this.identifier = 0;
        this.timestamp = System.currentTimeMillis();
        this.value = 0.0;
        this.active = true;
        this.items = new ArrayList<>();
        this.properties = new HashMap<>();
        this.uniqueNumbers = new HashSet<>();
        this.messageQueue = new LinkedList<>();
        this.operationStack = new Stack<>();
    }
    
    public LargeFileExample(String name) {
        this();
        this.name = name;
    }
    
    public LargeFileExample(String name, int identifier) {
        this(name);
        this.identifier = identifier;
    }
    
    public LargeFileExample(String name, int identifier, double value) {
        this(name, identifier);
        this.value = value;
    }
    
    // ==================== Getter and Setter Methods ====================
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public List<String> getItems() {
        return items;
    }
    
    public void setItems(List<String> items) {
        this.items = items;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    // ==================== Business Logic Methods ====================
    
    /**
     * Calculates the factorial of a given number.
     * 
     * @param n the number to calculate factorial for
     * @return the factorial result
     */
    public long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * Checks if a number is prime.
     * 
     * @param number the number to check
     * @return true if prime, false otherwise
     */
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Generates Fibonacci sequence up to n terms.
     * 
     * @param n number of terms to generate
     * @return list of Fibonacci numbers
     */
    public List<Long> generateFibonacci(int n) {
        List<Long> sequence = new ArrayList<>();
        if (n <= 0) {
            return sequence;
        }
        sequence.add(0L);
        if (n == 1) {
            return sequence;
        }
        sequence.add(1L);
        for (int i = 2; i < n; i++) {
            long next = sequence.get(i - 1) + sequence.get(i - 2);
            sequence.add(next);
        }
        return sequence;
    }
    
    /**
     * Performs bubble sort on an array.
     * 
     * @param array the array to sort
     */
    public void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * Performs quick sort on an array.
     * 
     * @param array the array to sort
     * @param low starting index
     * @param high ending index
     */
    public void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }
    
    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
    
    /**
     * Performs binary search on a sorted array.
     * 
     * @param array sorted array to search
     * @param target value to find
     * @return index of target or -1 if not found
     */
    public int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Calculates the greatest common divisor (GCD).
     * 
     * @param a first number
     * @param b second number
     * @return GCD of a and b
     */
    public int calculateGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return calculateGCD(b, a % b);
    }
    
    /**
     * Calculates the least common multiple (LCM).
     * 
     * @param a first number
     * @param b second number
     * @return LCM of a and b
     */
    public int calculateLCM(int a, int b) {
        return (a * b) / calculateGCD(a, b);
    }
    
    /**
     * Reverses a string.
     * 
     * @param input the string to reverse
     * @return reversed string
     */
    public String reverseString(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }
    
    /**
     * Checks if a string is a palindrome.
     * 
     * @param input the string to check
     * @return true if palindrome, false otherwise
     */
    public boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }
        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }
    
    /**
     * Counts the number of vowels in a string.
     * 
     * @param input the string to analyze
     * @return number of vowels
     */
    public int countVowels(String input) {
        if (input == null) {
            return 0;
        }
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : input.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Converts a decimal number to binary.
     * 
     * @param decimal the decimal number
     * @return binary representation as string
     */
    public String decimalToBinary(int decimal) {
        if (decimal == 0) {
            return "0";
        }
        StringBuilder binary = new StringBuilder();
        int num = decimal;
        while (num > 0) {
            binary.insert(0, num % 2);
            num /= 2;
        }
        return binary.toString();
    }
    
    /**
     * Converts a binary string to decimal.
     * 
     * @param binary the binary string
     * @return decimal value
     */
    public int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }
    
    /**
     * Calculates power of a number.
     * 
     * @param base the base number
     * @param exponent the exponent
     * @return result of base raised to exponent
     */
    public double power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent < 0) {
            return 1.0 / power(base, -exponent);
        }
        double half = power(base, exponent / 2);
        if (exponent % 2 == 0) {
            return half * half;
        } else {
            return base * half * half;
        }
    }
    
    /**
     * Finds all prime numbers up to n using Sieve of Eratosthenes.
     * 
     * @param n the upper limit
     * @return list of prime numbers
     */
    public List<Integer> sieveOfEratosthenes(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n < 2) {
            return primes;
        }
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
    
    /**
     * Calculates the sum of digits in a number.
     * 
     * @param number the number
     * @return sum of digits
     */
    public int sumOfDigits(int number) {
        int sum = 0;
        int num = Math.abs(number);
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
    
    /**
     * Reverses the digits of a number.
     * 
     * @param number the number
     * @return number with reversed digits
     */
    public int reverseNumber(int number) {
        int reversed = 0;
        int num = Math.abs(number);
        while (num > 0) {
            reversed = reversed * 10 + num % 10;
            num /= 10;
        }
        return number < 0 ? -reversed : reversed;
    }
    
    /**
     * Checks if a number is an Armstrong number.
     * 
     * @param number the number to check
     * @return true if Armstrong number, false otherwise
     */
    public boolean isArmstrongNumber(int number) {
        int originalNumber = number;
        int result = 0;
        int digits = String.valueOf(number).length();
        while (number != 0) {
            int digit = number % 10;
            result += Math.pow(digit, digits);
            number /= 10;
        }
        return result == originalNumber;
    }
    
    /**
     * Generates a random string of specified length.
     * 
     * @param length the desired length
     * @return random string
     */
    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
    /**
     * Shuffles an array randomly.
     * 
     * @param array the array to shuffle
     */
    public void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    
    /**
     * Finds the maximum element in an array.
     * 
     * @param array the array to search
     * @return maximum element
     */
    public int findMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    /**
     * Finds the minimum element in an array.
     * 
     * @param array the array to search
     * @return minimum element
     */
    public int findMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    /**
     * Calculates the average of array elements.
     * 
     * @param array the array
     * @return average value
     */
    public double calculateAverage(int[] array) {
        if (array == null || array.length == 0) {
            return 0.0;
        }
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }
    
    /**
     * Calculates the median of array elements.
     * 
     * @param array the array
     * @return median value
     */
    public double calculateMedian(int[] array) {
        if (array == null || array.length == 0) {
            return 0.0;
        }
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        int middle = sorted.length / 2;
        if (sorted.length % 2 == 0) {
            return (sorted[middle - 1] + sorted[middle]) / 2.0;
        } else {
            return sorted[middle];
        }
    }
    
    /**
     * Finds the mode (most frequent element) in an array.
     * 
     * @param array the array
     * @return the mode
     */
    public int findMode(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int value : array) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        int mode = array[0];
        int maxFrequency = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }
    
    /**
     * Removes duplicates from an array.
     * 
     * @param array the array
     * @return array without duplicates
     */
    public int[] removeDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        Set<Integer> uniqueSet = new LinkedHashSet<>();
        for (int value : array) {
            uniqueSet.add(value);
        }
        int[] result = new int[uniqueSet.size()];
        int index = 0;
        for (Integer value : uniqueSet) {
            result[index++] = value;
        }
        return result;
    }
    
    /**
     * Rotates an array to the right by k positions.
     * 
     * @param array the array to rotate
     * @param k number of positions to rotate
     */
    public void rotateArray(int[] array, int k) {
        if (array == null || array.length == 0) {
            return;
        }
        k = k % array.length;
        reverse(array, 0, array.length - 1);
        reverse(array, 0, k - 1);
        reverse(array, k, array.length - 1);
    }
    
    private void reverse(int[] array, int start, int end) {
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * Merges two sorted arrays.
     * 
     * @param array1 first sorted array
     * @param array2 second sorted array
     * @return merged sorted array
     */
    public int[] mergeSortedArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                result[k++] = array1[i++];
            } else {
                result[k++] = array2[j++];
            }
        }
        while (i < array1.length) {
            result[k++] = array1[i++];
        }
        while (j < array2.length) {
            result[k++] = array2[j++];
        }
        return result;
    }
    
    /**
     * Finds the intersection of two arrays.
     * 
     * @param array1 first array
     * @param array2 second array
     * @return array containing common elements
     */
    public int[] findIntersection(int[] array1, int[] array2) {
        Set<Integer> set1 = new HashSet<>();
        for (int value : array1) {
            set1.add(value);
        }
        Set<Integer> intersection = new HashSet<>();
        for (int value : array2) {
            if (set1.contains(value)) {
                intersection.add(value);
            }
        }
        int[] result = new int[intersection.size()];
        int index = 0;
        for (Integer value : intersection) {
            result[index++] = value;
        }
        return result;
    }
    
    /**
     * Finds the union of two arrays.
     * 
     * @param array1 first array
     * @param array2 second array
     * @return array containing all unique elements
     */
    public int[] findUnion(int[] array1, int[] array2) {
        Set<Integer> union = new HashSet<>();
        for (int value : array1) {
            union.add(value);
        }
        for (int value : array2) {
            union.add(value);
        }
        int[] result = new int[union.size()];
        int index = 0;
        for (Integer value : union) {
            result[index++] = value;
        }
        return result;
    }
    
    /**
     * Calculates the standard deviation of array elements.
     * 
     * @param array the array
     * @return standard deviation
     */
    public double calculateStandardDeviation(int[] array) {
        if (array == null || array.length == 0) {
            return 0.0;
        }
        double mean = calculateAverage(array);
        double sumSquaredDifferences = 0.0;
        for (int value : array) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / array.length);
    }
    
    /**
     * Finds the kth largest element in an array.
     * 
     * @param array the array
     * @param k the position
     * @return kth largest element
     */
    public int findKthLargest(int[] array, int k) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int value : array) {
            minHeap.offer(value);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }
    
    /**
     * Checks if an array is sorted in ascending order.
     * 
     * @param array the array to check
     * @return true if sorted, false otherwise
     */
    public boolean isSorted(int[] array) {
        if (array == null || array.length <= 1) {
            return true;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Counts the occurrences of a value in an array.
     * 
     * @param array the array
     * @param value the value to count
     * @return number of occurrences
     */
    public int countOccurrences(int[] array, int value) {
        if (array == null) {
            return 0;
        }
        int count = 0;
        for (int element : array) {
            if (element == value) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Calculates the product of all elements in an array.
     * 
     * @param array the array
     * @return product of all elements
     */
    public long calculateProduct(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        long product = 1;
        for (int value : array) {
            product *= value;
        }
        return product;
    }
    
    /**
     * Finds all pairs in an array that sum to a target value.
     * 
     * @param array the array
     * @param target the target sum
     * @return list of pairs
     */
    public List<int[]> findPairsWithSum(int[] array, int target) {
        List<int[]> pairs = new ArrayList<>();
        if (array == null || array.length < 2) {
            return pairs;
        }
        Set<Integer> seen = new HashSet<>();
        for (int value : array) {
            int complement = target - value;
            if (seen.contains(complement)) {
                pairs.add(new int[]{complement, value});
            }
            seen.add(value);
        }
        return pairs;
    }
    
    /**
     * Checks if a string contains only digits.
     * 
     * @param str the string to check
     * @return true if contains only digits, false otherwise
     */
    public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Capitalizes the first letter of each word in a string.
     * 
     * @param str the string to process
     * @return capitalized string
     */
    public String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
    
    /**
     * Removes all whitespace from a string.
     * 
     * @param str the string to process
     * @return string without whitespace
     */
    public String removeWhitespace(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s+", "");
    }
    
    /**
     * Counts the number of words in a string.
     * 
     * @param str the string to analyze
     * @return number of words
     */
    public int countWords(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        String[] words = str.trim().split("\\s+");
        return words.length;
    }
    
    /**
     * Finds the longest word in a string.
     * 
     * @param str the string to analyze
     * @return the longest word
     */
    public String findLongestWord(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String[] words = str.split("\\s+");
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }
    
    /**
     * Checks if two strings are anagrams.
     * 
     * @param str1 first string
     * @param str2 second string
     * @return true if anagrams, false otherwise
     */
    public boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        if (str1.length() != str2.length()) {
            return false;
        }
        char[] chars1 = str1.toLowerCase().toCharArray();
        char[] chars2 = str2.toLowerCase().toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }
    
    /**
     * Compresses a string by replacing consecutive characters with count.
     * 
     * @param str the string to compress
     * @return compressed string
     */
    public String compressString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        for (int i = 0; i < str.length(); i++) {
            if (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
                compressed.append(str.charAt(i));
                if (count > 1) {
                    compressed.append(count);
                }
                count = 1;
            }
        }
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
    
    @Override
    public String toString() {
        return "LargeFileExample{" +
                "name='" + name + '\'' +
                ", identifier=" + identifier +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", active=" + active +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LargeFileExample that = (LargeFileExample) o;
        return identifier == that.identifier &&
                timestamp == that.timestamp &&
                Double.compare(that.value, value) == 0 &&
                active == that.active &&
                Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, identifier, timestamp, value, active);
    }
    
    // ==================== Inner Classes ====================
    
    /**
     * Inner class representing a data node.
     */
    public static class DataNode {
        private String key;
        private Object value;
        private DataNode next;
        private DataNode previous;
        
        public DataNode(String key, Object value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.previous = null;
        }
        
        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public Object getValue() {
            return value;
        }
        
        public void setValue(Object value) {
            this.value = value;
        }
        
        public DataNode getNext() {
            return next;
        }
        
        public void setNext(DataNode next) {
            this.next = next;
        }
        
        public DataNode getPrevious() {
            return previous;
        }
        
        public void setPrevious(DataNode previous) {
            this.previous = previous;
        }
    }
    
    /**
     * Inner class for tree node implementation.
     */
    public static class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;
        private int height;
        
        public TreeNode(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
        
        public TreeNode getLeft() {
            return left;
        }
        
        public void setLeft(TreeNode left) {
            this.left = left;
        }
        
        public TreeNode getRight() {
            return right;
        }
        
        public void setRight(TreeNode right) {
            this.right = right;
        }
        
        public int getHeight() {
            return height;
        }
        
        public void setHeight(int height) {
            this.height = height;
        }
    }
    
    /**
     * Inner class for graph edge representation.
     */
    public static class Edge {
        private int source;
        private int destination;
        private double weight;
        
        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        public int getSource() {
            return source;
        }
        
        public void setSource(int source) {
            this.source = source;
        }
        
        public int getDestination() {
            return destination;
        }
        
        public void setDestination(int destination) {
            this.destination = destination;
        }
        
        public double getWeight() {
            return weight;
        }
        
        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
    
    /**
     * Finds all substrings of a given length.
     * 
     * @param str the string
     * @param length substring length
     * @return list of substrings
     */
    public List<String> findSubstrings(String str, int length) {
        List<String> substrings = new ArrayList<>();
        if (str == null || length <= 0 || length > str.length()) {
            return substrings;
        }
        for (int i = 0; i <= str.length() - length; i++) {
            substrings.add(str.substring(i, i + length));
        }
        return substrings;
    }
    
    /**
     * Finds the first non-repeating character in a string.
     * 
     * @param str the string
     * @return first non-repeating character or null
     */
    public Character findFirstNonRepeatingChar(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        Map<Character, Integer> charCount = new LinkedHashMap<>();
        for (char c : str.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    /**
     * Validates if a string is a valid email address.
     * 
     * @param email the email string
     * @return true if valid email, false otherwise
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Converts a string to title case.
     * 
     * @param str the string
     * @return title case string
     */
    public String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : str.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }
    
    /**
     * Generates all permutations of a string.
     * 
     * @param str the string
     * @return list of permutations
     */
    public List<String> generatePermutations(String str) {
        List<String> permutations = new ArrayList<>();
        if (str == null) {
            return permutations;
        }
        if (str.length() == 0) {
            permutations.add("");
            return permutations;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            String remaining = str.substring(0, i) + str.substring(i + 1);
            for (String perm : generatePermutations(remaining)) {
                permutations.add(c + perm);
            }
        }
        return permutations;
    }
    
    /**
     * Calculates Levenshtein distance between two strings.
     * 
     * @param str1 first string
     * @param str2 second string
     * @return edit distance
     */
    public int calculateLevenshteinDistance(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return -1;
        }
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i <= str1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= str2.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }
    
    /**
     * Finds the longest common subsequence of two strings.
     * 
     * @param str1 first string
     * @param str2 second string
     * @return longest common subsequence
     */
    public String longestCommonSubsequence(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return "";
        }
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.insert(0, str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return lcs.toString();
    }
    
    /**
     * Performs merge sort on an array.
     * 
     * @param array the array to sort
     */
    public void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortHelper(array, 0, array.length - 1);
    }
    
    private void mergeSortHelper(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(array, left, mid);
            mergeSortHelper(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }
    
    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, left, temp.length);
    }
    
    /**
     * Performs heap sort on an array.
     * 
     * @param array the array to sort
     */
    public void heapSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }
    
    private void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heapify(array, n, largest);
        }
    }
    
    /**
     * Finds the longest increasing subsequence length.
     * 
     * @param array the array
     * @return length of longest increasing subsequence
     */
    public int longestIncreasingSubsequence(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] dp = new int[array.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int maxLength = 0;
        for (int length : dp) {
            maxLength = Math.max(maxLength, length);
        }
        return maxLength;
    }
    
    /**
     * Finds the maximum subarray sum (Kadane's algorithm).
     * 
     * @param array the array
     * @return maximum subarray sum
     */
    public long maxSubarraySum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        long maxSoFar = array[0];
        long maxEndingHere = array[0];
        for (int i = 1; i < array.length; i++) {
            maxEndingHere = Math.max(array[i], maxEndingHere + array[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }
    
    /**
     * Calculates the nth Catalan number.
     * 
     * @param n the position
     * @return nth Catalan number
     */
    public long calculateCatalanNumber(int n) {
        if (n < 0) {
            return 0;
        }
        long[] catalan = new long[n + 1];
        catalan[0] = 1;
        if (n > 0) {
            catalan[1] = 1;
        }
        for (int i = 2; i <= n; i++) {
            catalan[i] = 0;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }
        return catalan[n];
    }
    
    /**
     * Generates Pascal's triangle up to n rows.
     * 
     * @param n number of rows
     * @return Pascal's triangle
     */
    public List<List<Integer>> generatePascalTriangle(int n) {
        List<List<Integer>> triangle = new ArrayList<>();
        if (n <= 0) {
            return triangle;
        }
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
                }
            }
            triangle.add(row);
        }
        return triangle;
    }
    
    /**
     * Calculates binomial coefficient C(n, k).
     * 
     * @param n total items
     * @param k items to choose
     * @return binomial coefficient
     */
    public long binomialCoefficient(int n, int k) {
        if (k > n || k < 0) {
            return 0;
        }
        if (k == 0 || k == n) {
            return 1;
        }
        k = Math.min(k, n - k);
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }
    
    /**
     * Finds all prime factors of a number.
     * 
     * @param number the number
     * @return list of prime factors
     */
    public List<Integer> findPrimeFactors(int number) {
        List<Integer> factors = new ArrayList<>();
        if (number <= 1) {
            return factors;
        }
        while (number % 2 == 0) {
            factors.add(2);
            number /= 2;
        }
        for (int i = 3; i * i <= number; i += 2) {
            while (number % i == 0) {
                factors.add(i);
                number /= i;
            }
        }
        if (number > 2) {
            factors.add(number);
        }
        return factors;
    }
    
    /**
     * Checks if a number is a perfect square.
     * 
     * @param number the number
     * @return true if perfect square, false otherwise
     */
    public boolean isPerfectSquare(int number) {
        if (number < 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(number);
        return sqrt * sqrt == number;
    }
    
    /**
     * Finds the square root using Newton's method.
     * 
     * @param number the number
     * @param precision desired precision
     * @return square root
     */
    public double calculateSquareRoot(double number, double precision) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        if (number == 0) {
            return 0;
        }
        double x = number;
        double root;
        while (true) {
            root = 0.5 * (x + (number / x));
            if (Math.abs(root - x) < precision) {
                break;
            }
            x = root;
        }
        return root;
    }
    
    /**
     * Converts Roman numeral to integer.
     * 
     * @param roman the Roman numeral string
     * @return integer value
     */
    public int romanToInt(String roman) {
        if (roman == null || roman.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanValues.getOrDefault(roman.charAt(i), 0);
            if (i < roman.length() - 1) {
                int next = romanValues.getOrDefault(roman.charAt(i + 1), 0);
                if (current < next) {
                    result -= current;
                } else {
                    result += current;
                }
            } else {
                result += current;
            }
        }
        return result;
    }
    
    /**
     * Converts integer to Roman numeral.
     * 
     * @param num the integer
     * @return Roman numeral string
     */
    public String intToRoman(int num) {
        if (num <= 0 || num > 3999) {
            return "";
        }
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length && num > 0; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(symbols[i]);
            }
        }
        return result.toString();
    }
    
    /**
     * Calculates the day of the week for a given date (Zeller's congruence).
     * 
     * @param day day of month
     * @param month month (1-12)
     * @param year year
     * @return day of week (0=Saturday, 1=Sunday, 2=Monday, etc.)
     */
    public int calculateDayOfWeek(int day, int month, int year) {
        if (month < 3) {
            month += 12;
            year--;
        }
        int q = day;
        int m = month;
        int k = year % 100;
        int j = year / 100;
        int h = (q + ((13 * (m + 1)) / 5) + k + (k / 4) + (j / 4) - (2 * j)) % 7;
        return h;
    }
    
    /**
     * Checks if a year is a leap year.
     * 
     * @param year the year
     * @return true if leap year, false otherwise
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    /**
     * Calculates the number of days in a month.
     * 
     * @param month month (1-12)
     * @param year year
     * @return number of days
     */
    public int getDaysInMonth(int month, int year) {
        if (month < 1 || month > 12) {
            return 0;
        }
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return days[month - 1];
    }
    
    /**
     * Encodes a string using Base64 encoding.
     * 
     * @param input the string to encode
     * @return Base64 encoded string
     */
    public String base64Encode(String input) {
        if (input == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
    
    /**
     * Decodes a Base64 encoded string.
     * 
     * @param encoded the Base64 encoded string
     * @return decoded string
     */
    public String base64Decode(String encoded) {
        if (encoded == null) {
            return null;
        }
        try {
            return new String(Base64.getDecoder().decode(encoded));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Generates a UUID string.
     * 
     * @return UUID string
     */
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Calculates the hash code for a string using a custom algorithm.
     * 
     * @param str the string
     * @return hash code
     */
    public long calculateCustomHash(String str) {
        if (str == null) {
            return 0;
        }
        long hash = 0;
        long prime = 31;
        for (char c : str.toCharArray()) {
            hash = hash * prime + c;
        }
        return hash;
    }
    
    // ==================== Additional Utility Classes ====================
    
    /**
     * Inner class for Matrix operations.
     */
    public static class Matrix {
        private int[][] data;
        private int rows;
        private int cols;
        
        public Matrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.data = new int[rows][cols];
        }
        
        public Matrix(int[][] data) {
            this.rows = data.length;
            this.cols = data[0].length;
            this.data = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                System.arraycopy(data[i], 0, this.data[i], 0, cols);
            }
        }
        
        public void setValue(int row, int col, int value) {
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                data[row][col] = value;
            }
        }
        
        public int getValue(int row, int col) {
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                return data[row][col];
            }
            return 0;
        }
        
        public Matrix add(Matrix other) {
            if (this.rows != other.rows || this.cols != other.cols) {
                return null;
            }
            Matrix result = new Matrix(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    result.data[i][j] = this.data[i][j] + other.data[i][j];
                }
            }
            return result;
        }
        
        public Matrix multiply(Matrix other) {
            if (this.cols != other.rows) {
                return null;
            }
            Matrix result = new Matrix(this.rows, other.cols);
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < other.cols; j++) {
                    for (int k = 0; k < this.cols; k++) {
                        result.data[i][j] += this.data[i][k] * other.data[k][j];
                    }
                }
            }
            return result;
        }
        
        public Matrix transpose() {
            Matrix result = new Matrix(cols, rows);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    result.data[j][i] = this.data[i][j];
                }
            }
            return result;
        }
        
        public int[][] getData() {
            return data;
        }
        
        public int getRows() {
            return rows;
        }
        
        public int getCols() {
            return cols;
        }
    }
    
    /**
     * Inner class for Complex number operations.
     */
    public static class ComplexNumber {
        private double real;
        private double imaginary;
        
        public ComplexNumber(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }
        
        public ComplexNumber add(ComplexNumber other) {
            return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
        }
        
        public ComplexNumber subtract(ComplexNumber other) {
            return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
        }
        
        public ComplexNumber multiply(ComplexNumber other) {
            double newReal = this.real * other.real - this.imaginary * other.imaginary;
            double newImaginary = this.real * other.imaginary + this.imaginary * other.real;
            return new ComplexNumber(newReal, newImaginary);
        }
        
        public ComplexNumber divide(ComplexNumber other) {
            double denominator = other.real * other.real + other.imaginary * other.imaginary;
            if (denominator == 0) {
                throw new ArithmeticException("Division by zero");
            }
            double newReal = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
            double newImaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
            return new ComplexNumber(newReal, newImaginary);
        }
        
        public double magnitude() {
            return Math.sqrt(real * real + imaginary * imaginary);
        }
        
        public double phase() {
            return Math.atan2(imaginary, real);
        }
        
        public ComplexNumber conjugate() {
            return new ComplexNumber(real, -imaginary);
        }
        
        public double getReal() {
            return real;
        }
        
        public double getImaginary() {
            return imaginary;
        }
        
        @Override
        public String toString() {
            if (imaginary >= 0) {
                return real + " + " + imaginary + "i";
            } else {
                return real + " - " + (-imaginary) + "i";
            }
        }
    }
    
    /**
     * Inner class for Fraction operations.
     */
    public static class Fraction {
        private long numerator;
        private long denominator;
        
        public Fraction(long numerator, long denominator) {
            if (denominator == 0) {
                throw new IllegalArgumentException("Denominator cannot be zero");
            }
            long gcd = gcd(Math.abs(numerator), Math.abs(denominator));
            this.numerator = numerator / gcd;
            this.denominator = denominator / gcd;
            if (this.denominator < 0) {
                this.numerator = -this.numerator;
                this.denominator = -this.denominator;
            }
        }
        
        private static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }
        
        public Fraction add(Fraction other) {
            long newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
            long newDenominator = this.denominator * other.denominator;
            return new Fraction(newNumerator, newDenominator);
        }
        
        public Fraction subtract(Fraction other) {
            long newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
            long newDenominator = this.denominator * other.denominator;
            return new Fraction(newNumerator, newDenominator);
        }
        
        public Fraction multiply(Fraction other) {
            return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
        }
        
        public Fraction divide(Fraction other) {
            if (other.numerator == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator);
        }
        
        public double toDouble() {
            return (double) numerator / denominator;
        }
        
        public long getNumerator() {
            return numerator;
        }
        
        public long getDenominator() {
            return denominator;
        }
        
        @Override
        public String toString() {
            if (denominator == 1) {
                return String.valueOf(numerator);
            }
            return numerator + "/" + denominator;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Fraction)) {
                return false;
            }
            Fraction other = (Fraction) obj;
            return this.numerator == other.numerator && this.denominator == other.denominator;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(numerator, denominator);
        }
    }
    
    /**
     * Inner class for Stack implementation.
     */
    public static class CustomStack<T> {
        private List<T> elements;
        private int capacity;
        
        public CustomStack(int capacity) {
            this.capacity = capacity;
            this.elements = new ArrayList<>();
        }
        
        public void push(T element) {
            if (elements.size() >= capacity) {
                throw new IllegalStateException("Stack overflow");
            }
            elements.add(element);
        }
        
        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack underflow");
            }
            return elements.remove(elements.size() - 1);
        }
        
        public T peek() {
            if (isEmpty()) {
                return null;
            }
            return elements.get(elements.size() - 1);
        }
        
        public boolean isEmpty() {
            return elements.isEmpty();
        }
        
        public int size() {
            return elements.size();
        }
        
        public void clear() {
            elements.clear();
        }
    }
    
    /**
     * Inner class for Queue implementation.
     */
    public static class CustomQueue<T> {
        private LinkedList<T> elements;
        private int capacity;
        
        public CustomQueue(int capacity) {
            this.capacity = capacity;
            this.elements = new LinkedList<>();
        }
        
        public void enqueue(T element) {
            if (elements.size() >= capacity) {
                throw new IllegalStateException("Queue is full");
            }
            elements.addLast(element);
        }
        
        public T dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            return elements.removeFirst();
        }
        
        public T front() {
            if (isEmpty()) {
                return null;
            }
            return elements.getFirst();
        }
        
        public boolean isEmpty() {
            return elements.isEmpty();
        }
        
        public int size() {
            return elements.size();
        }
        
        public void clear() {
            elements.clear();
        }
    }
    
    /**
     * Inner class for Binary Search Tree.
     */
    public static class BinarySearchTree {
        private TreeNode root;
        
        public BinarySearchTree() {
            this.root = null;
        }
        
        public void insert(int value) {
            root = insertRec(root, value);
        }
        
        private TreeNode insertRec(TreeNode node, int value) {
            if (node == null) {
                return new TreeNode(value);
            }
            if (value < node.getValue()) {
                node.setLeft(insertRec(node.getLeft(), value));
            } else if (value > node.getValue()) {
                node.setRight(insertRec(node.getRight(), value));
            }
            return node;
        }
        
        public boolean search(int value) {
            return searchRec(root, value);
        }
        
        private boolean searchRec(TreeNode node, int value) {
            if (node == null) {
                return false;
            }
            if (value == node.getValue()) {
                return true;
            }
            if (value < node.getValue()) {
                return searchRec(node.getLeft(), value);
            }
            return searchRec(node.getRight(), value);
        }
        
        public void delete(int value) {
            root = deleteRec(root, value);
        }
        
        private TreeNode deleteRec(TreeNode node, int value) {
            if (node == null) {
                return null;
            }
            if (value < node.getValue()) {
                node.setLeft(deleteRec(node.getLeft(), value));
            } else if (value > node.getValue()) {
                node.setRight(deleteRec(node.getRight(), value));
            } else {
                if (node.getLeft() == null) {
                    return node.getRight();
                } else if (node.getRight() == null) {
                    return node.getLeft();
                }
                node.setValue(minValue(node.getRight()));
                node.setRight(deleteRec(node.getRight(), node.getValue()));
            }
            return node;
        }
        
        private int minValue(TreeNode node) {
            int min = node.getValue();
            while (node.getLeft() != null) {
                min = node.getLeft().getValue();
                node = node.getLeft();
            }
            return min;
        }
        
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            inorderRec(root, result);
            return result;
        }
        
        private void inorderRec(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderRec(node.getLeft(), result);
                result.add(node.getValue());
                inorderRec(node.getRight(), result);
            }
        }
        
        public List<Integer> preorderTraversal() {
            List<Integer> result = new ArrayList<>();
            preorderRec(root, result);
            return result;
        }
        
        private void preorderRec(TreeNode node, List<Integer> result) {
            if (node != null) {
                result.add(node.getValue());
                preorderRec(node.getLeft(), result);
                preorderRec(node.getRight(), result);
            }
        }
        
        public List<Integer> postorderTraversal() {
            List<Integer> result = new ArrayList<>();
            postorderRec(root, result);
            return result;
        }
        
        private void postorderRec(TreeNode node, List<Integer> result) {
            if (node != null) {
                postorderRec(node.getLeft(), result);
                postorderRec(node.getRight(), result);
                result.add(node.getValue());
            }
        }
        
        public int height() {
            return heightRec(root);
        }
        
        private int heightRec(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(heightRec(node.getLeft()), heightRec(node.getRight()));
        }
    }
    
    /**
     * Inner class for Graph implementation.
     */
    public static class Graph {
        private int vertices;
        private List<List<Integer>> adjacencyList;
        
        public Graph(int vertices) {
            this.vertices = vertices;
            this.adjacencyList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        
        public void addEdge(int source, int destination) {
            adjacencyList.get(source).add(destination);
            adjacencyList.get(destination).add(source);
        }
        
        public void addDirectedEdge(int source, int destination) {
            adjacencyList.get(source).add(destination);
        }
        
        public List<Integer> bfs(int start) {
            List<Integer> result = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            visited[start] = true;
            queue.offer(start);
            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                result.add(vertex);
                for (int neighbor : adjacencyList.get(vertex)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            return result;
        }
        
        public List<Integer> dfs(int start) {
            List<Integer> result = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            dfsRec(start, visited, result);
            return result;
        }
        
        private void dfsRec(int vertex, boolean[] visited, List<Integer> result) {
            visited[vertex] = true;
            result.add(vertex);
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    dfsRec(neighbor, visited, result);
                }
            }
        }
        
        public boolean hasCycle() {
            boolean[] visited = new boolean[vertices];
            for (int i = 0; i < vertices; i++) {
                if (!visited[i] && hasCycleDFS(i, visited, -1)) {
                    return true;
                }
            }
            return false;
        }
        
        private boolean hasCycleDFS(int vertex, boolean[] visited, int parent) {
            visited[vertex] = true;
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    if (hasCycleDFS(neighbor, visited, vertex)) {
                        return true;
                    }
                } else if (neighbor != parent) {
                    return true;
                }
            }
            return false;
        }
        
        public int getVertices() {
            return vertices;
        }
        
        public List<List<Integer>> getAdjacencyList() {
            return adjacencyList;
        }
    }
    
    /**
     * Inner class for Trie (Prefix Tree) implementation.
     */
    public static class Trie {
        private TrieNode root;
        
        private static class TrieNode {
            Map<Character, TrieNode> children;
            boolean isEndOfWord;
            
            TrieNode() {
                children = new HashMap<>();
                isEndOfWord = false;
            }
        }
        
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                current.children.putIfAbsent(c, new TrieNode());
                current = current.children.get(c);
            }
            current.isEndOfWord = true;
        }
        
        public boolean search(String word) {
            TrieNode node = searchNode(word);
            return node != null && node.isEndOfWord;
        }
        
        public boolean startsWith(String prefix) {
            return searchNode(prefix) != null;
        }
        
        private TrieNode searchNode(String str) {
            TrieNode current = root;
            for (char c : str.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    return null;
                }
                current = current.children.get(c);
            }
            return current;
        }
        
        public void delete(String word) {
            deleteRec(root, word, 0);
        }
        
        private boolean deleteRec(TrieNode node, String word, int index) {
            if (index == word.length()) {
                if (!node.isEndOfWord) {
                    return false;
                }
                node.isEndOfWord = false;
                return node.children.isEmpty();
            }
            char c = word.charAt(index);
            TrieNode childNode = node.children.get(c);
            if (childNode == null) {
                return false;
            }
            boolean shouldDeleteChild = deleteRec(childNode, word, index + 1);
            if (shouldDeleteChild) {
                node.children.remove(c);
                return node.children.isEmpty() && !node.isEndOfWord;
            }
            return false;
        }
    }
    
    /**
     * Inner class for LRU Cache implementation.
     */
    public static class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, Node<K, V>> cache;
        private final Node<K, V> head;
        private final Node<K, V> tail;
        
        private static class Node<K, V> {
            K key;
            V value;
            Node<K, V> prev;
            Node<K, V> next;
            
            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.head = new Node<>(null, null);
            this.tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }
        
        public V get(K key) {
            Node<K, V> node = cache.get(key);
            if (node == null) {
                return null;
            }
            moveToHead(node);
            return node.value;
        }
        
        public void put(K key, V value) {
            Node<K, V> node = cache.get(key);
            if (node != null) {
                node.value = value;
                moveToHead(node);
            } else {
                Node<K, V> newNode = new Node<>(key, value);
                cache.put(key, newNode);
                addToHead(newNode);
                if (cache.size() > capacity) {
                    Node<K, V> removed = removeTail();
                    cache.remove(removed.key);
                }
            }
        }
        
        private void addToHead(Node<K, V> node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
        
        private void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        
        private void moveToHead(Node<K, V> node) {
            removeNode(node);
            addToHead(node);
        }
        
        private Node<K, V> removeTail() {
            Node<K, V> node = tail.prev;
            removeNode(node);
            return node;
        }
    }
    
    /**
     * Inner class for Disjoint Set (Union-Find) implementation.
     */
    public static class DisjointSet {
        private int[] parent;
        private int[] rank;
        
        public DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
        
        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    /**
     * Inner class for Bloom Filter implementation.
     */
    public static class BloomFilter {
        private BitSet bitSet;
        private int size;
        private int hashFunctions;
        
        public BloomFilter(int size, int hashFunctions) {
            this.size = size;
            this.hashFunctions = hashFunctions;
            this.bitSet = new BitSet(size);
        }
        
        public void add(String element) {
            for (int i = 0; i < hashFunctions; i++) {
                int hash = hash(element, i);
                bitSet.set(hash);
            }
        }
        
        public boolean mightContain(String element) {
            for (int i = 0; i < hashFunctions; i++) {
                int hash = hash(element, i);
                if (!bitSet.get(hash)) {
                    return false;
                }
            }
            return true;
        }
        
        private int hash(String element, int seed) {
            int hash = 0;
            for (char c : element.toCharArray()) {
                hash = (hash * 31 + c + seed) % size;
            }
            return Math.abs(hash);
        }
    }
    
    /**
     * Inner class for Segment Tree implementation.
     */
    public static class SegmentTree {
        private int[] tree;
        private int n;
        
        public SegmentTree(int[] arr) {
            this.n = arr.length;
            this.tree = new int[4 * n];
            build(arr, 0, 0, n - 1);
        }
        
        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                int leftChild = 2 * node + 1;
                int rightChild = 2 * node + 2;
                build(arr, leftChild, start, mid);
                build(arr, rightChild, mid + 1, end);
                tree[node] = tree[leftChild] + tree[rightChild];
            }
        }
        
        public void update(int idx, int val) {
            update(0, 0, n - 1, idx, val);
        }
        
        private void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                int leftChild = 2 * node + 1;
                int rightChild = 2 * node + 2;
                if (idx <= mid) {
                    update(leftChild, start, mid, idx, val);
                } else {
                    update(rightChild, mid + 1, end, idx, val);
                }
                tree[node] = tree[leftChild] + tree[rightChild];
            }
        }
        
        public int query(int l, int r) {
            return query(0, 0, n - 1, l, r);
        }
        
        private int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) {
                return 0;
            }
            if (l <= start && end <= r) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;
            int leftSum = query(leftChild, start, mid, l, r);
            int rightSum = query(rightChild, mid + 1, end, l, r);
            return leftSum + rightSum;
        }
    }
    
    /**
     * Inner class for Fenwick Tree (Binary Indexed Tree) implementation.
     */
    public static class FenwickTree {
        private int[] tree;
        private int n;
        
        public FenwickTree(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }
        
        public void update(int index, int delta) {
            index++; // 1-indexed
            while (index <= n) {
                tree[index] += delta;
                index += index & (-index);
            }
        }
        
        public int query(int index) {
            index++; // 1-indexed
            int sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & (-index);
            }
            return sum;
        }
        
        public int rangeQuery(int left, int right) {
            return query(right) - (left > 0 ? query(left - 1) : 0);
        }
    }
    
    /**
     * Inner class for Skip List implementation.
     */
    public static class SkipList {
        private static final int MAX_LEVEL = 16;
        private static final double P = 0.5;
        private SkipNode head;
        private int level;
        private Random random;
        
        private static class SkipNode {
            int value;
            SkipNode[] forward;
            
            SkipNode(int value, int level) {
                this.value = value;
                this.forward = new SkipNode[level + 1];
            }
        }
        
        public SkipList() {
            this.level = 0;
            this.head = new SkipNode(Integer.MIN_VALUE, MAX_LEVEL);
            this.random = new Random();
        }
        
        private int randomLevel() {
            int lvl = 0;
            while (random.nextDouble() < P && lvl < MAX_LEVEL) {
                lvl++;
            }
            return lvl;
        }
        
        public void insert(int value) {
            SkipNode[] update = new SkipNode[MAX_LEVEL + 1];
            SkipNode current = head;
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].value < value) {
                    current = current.forward[i];
                }
                update[i] = current;
            }
            int newLevel = randomLevel();
            if (newLevel > level) {
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = head;
                }
                level = newLevel;
            }
            SkipNode newNode = new SkipNode(value, newLevel);
            for (int i = 0; i <= newLevel; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
        
        public boolean search(int value) {
            SkipNode current = head;
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].value < value) {
                    current = current.forward[i];
                }
            }
            current = current.forward[0];
            return current != null && current.value == value;
        }
        
        public void delete(int value) {
            SkipNode[] update = new SkipNode[MAX_LEVEL + 1];
            SkipNode current = head;
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].value < value) {
                    current = current.forward[i];
                }
                update[i] = current;
            }
            current = current.forward[0];
            if (current != null && current.value == value) {
                for (int i = 0; i <= level; i++) {
                    if (update[i].forward[i] != current) {
                        break;
                    }
                    update[i].forward[i] = current.forward[i];
                }
                while (level > 0 && head.forward[level] == null) {
                    level--;
                }
            }
        }
    }
    
    /**
     * Inner class for AVL Tree implementation.
     */
    public static class AVLTree {
        private TreeNode root;
        
        private int height(TreeNode node) {
            return node == null ? 0 : node.getHeight();
        }
        
        private int balanceFactor(TreeNode node) {
            return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
        }
        
        private void updateHeight(TreeNode node) {
            if (node != null) {
                node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
            }
        }
        
        private TreeNode rotateRight(TreeNode y) {
            TreeNode x = y.getLeft();
            TreeNode T2 = x.getRight();
            x.setRight(y);
            y.setLeft(T2);
            updateHeight(y);
            updateHeight(x);
            return x;
        }
        
        private TreeNode rotateLeft(TreeNode x) {
            TreeNode y = x.getRight();
            TreeNode T2 = y.getLeft();
            y.setLeft(x);
            x.setRight(T2);
            updateHeight(x);
            updateHeight(y);
            return y;
        }
        
        public void insert(int value) {
            root = insertRec(root, value);
        }
        
        private TreeNode insertRec(TreeNode node, int value) {
            if (node == null) {
                return new TreeNode(value);
            }
            if (value < node.getValue()) {
                node.setLeft(insertRec(node.getLeft(), value));
            } else if (value > node.getValue()) {
                node.setRight(insertRec(node.getRight(), value));
            } else {
                return node;
            }
            updateHeight(node);
            int balance = balanceFactor(node);
            if (balance > 1 && value < node.getLeft().getValue()) {
                return rotateRight(node);
            }
            if (balance < -1 && value > node.getRight().getValue()) {
                return rotateLeft(node);
            }
            if (balance > 1 && value > node.getLeft().getValue()) {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
            if (balance < -1 && value < node.getRight().getValue()) {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
            return node;
        }
        
        public void delete(int value) {
            root = deleteRec(root, value);
        }
        
        private TreeNode deleteRec(TreeNode node, int value) {
            if (node == null) {
                return null;
            }
            if (value < node.getValue()) {
                node.setLeft(deleteRec(node.getLeft(), value));
            } else if (value > node.getValue()) {
                node.setRight(deleteRec(node.getRight(), value));
            } else {
                if (node.getLeft() == null || node.getRight() == null) {
                    TreeNode temp = node.getLeft() != null ? node.getLeft() : node.getRight();
                    if (temp == null) {
                        return null;
                    } else {
                        return temp;
                    }
                } else {
                    TreeNode temp = minValueNode(node.getRight());
                    node.setValue(temp.getValue());
                    node.setRight(deleteRec(node.getRight(), temp.getValue()));
                }
            }
            updateHeight(node);
            int balance = balanceFactor(node);
            if (balance > 1 && balanceFactor(node.getLeft()) >= 0) {
                return rotateRight(node);
            }
            if (balance > 1 && balanceFactor(node.getLeft()) < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
            if (balance < -1 && balanceFactor(node.getRight()) <= 0) {
                return rotateLeft(node);
            }
            if (balance < -1 && balanceFactor(node.getRight()) > 0) {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
            return node;
        }
        
        private TreeNode minValueNode(TreeNode node) {
            TreeNode current = node;
            while (current.getLeft() != null) {
                current = current.getLeft();
            }
            return current;
        }
    }
    
    /**
     * Inner class for Red-Black Tree node.
     */
    public static class RBNode {
        int data;
        RBNode parent;
        RBNode left;
        RBNode right;
        boolean color; // true for Red, false for Black
        
        public RBNode(int data) {
            this.data = data;
            this.color = true; // New nodes are always red
        }
    }
    
    /**
     * Inner class for Heap implementation.
     */
    public static class MinHeap {
        private List<Integer> heap;
        
        public MinHeap() {
            this.heap = new ArrayList<>();
        }
        
        private int parent(int i) {
            return (i - 1) / 2;
        }
        
        private int leftChild(int i) {
            return 2 * i + 1;
        }
        
        private int rightChild(int i) {
            return 2 * i + 2;
        }
        
        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
        
        public void insert(int value) {
            heap.add(value);
            int i = heap.size() - 1;
            while (i > 0 && heap.get(parent(i)) > heap.get(i)) {
                swap(i, parent(i));
                i = parent(i);
            }
        }
        
        public int extractMin() {
            if (heap.isEmpty()) {
                throw new IllegalStateException("Heap is empty");
            }
            int min = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                minHeapify(0);
            }
            return min;
        }
        
        private void minHeapify(int i) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);
            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }
            if (smallest != i) {
                swap(i, smallest);
                minHeapify(smallest);
            }
        }
        
        public int peek() {
            if (heap.isEmpty()) {
                throw new IllegalStateException("Heap is empty");
            }
            return heap.get(0);
        }
        
        public int size() {
            return heap.size();
        }
        
        public boolean isEmpty() {
            return heap.isEmpty();
        }
    }
    
    /**
     * Inner class for String manipulation utilities.
     */
    public static class StringUtils {
        
        public static String repeat(String str, int count) {
            if (str == null || count <= 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(str);
            }
            return sb.toString();
        }
        
        public static String padLeft(String str, int length, char padChar) {
            if (str == null) {
                str = "";
            }
            if (str.length() >= length) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length - str.length(); i++) {
                sb.append(padChar);
            }
            sb.append(str);
            return sb.toString();
        }
        
        public static String padRight(String str, int length, char padChar) {
            if (str == null) {
                str = "";
            }
            if (str.length() >= length) {
                return str;
            }
            StringBuilder sb = new StringBuilder(str);
            for (int i = str.length(); i < length; i++) {
                sb.append(padChar);
            }
            return sb.toString();
        }
        
        public static String truncate(String str, int maxLength) {
            if (str == null || str.length() <= maxLength) {
                return str;
            }
            return str.substring(0, maxLength);
        }
        
        public static String truncateWithEllipsis(String str, int maxLength) {
            if (str == null || str.length() <= maxLength) {
                return str;
            }
            if (maxLength < 3) {
                return str.substring(0, maxLength);
            }
            return str.substring(0, maxLength - 3) + "...";
        }
        
        public static boolean containsIgnoreCase(String str, String searchStr) {
            if (str == null || searchStr == null) {
                return false;
            }
            return str.toLowerCase().contains(searchStr.toLowerCase());
        }
        
        public static int countOccurrences(String str, String substring) {
            if (str == null || substring == null || substring.isEmpty()) {
                return 0;
            }
            int count = 0;
            int index = 0;
            while ((index = str.indexOf(substring, index)) != -1) {
                count++;
                index += substring.length();
            }
            return count;
        }
        
        public static String replaceAll(String str, String target, String replacement) {
            if (str == null || target == null || replacement == null) {
                return str;
            }
            return str.replace(target, replacement);
        }
        
        public static String[] splitByLength(String str, int length) {
            if (str == null || length <= 0) {
                return new String[0];
            }
            int numParts = (int) Math.ceil((double) str.length() / length);
            String[] parts = new String[numParts];
            for (int i = 0; i < numParts; i++) {
                int start = i * length;
                int end = Math.min(start + length, str.length());
                parts[i] = str.substring(start, end);
            }
            return parts;
        }
        
        public static boolean isBlank(String str) {
            return str == null || str.trim().isEmpty();
        }
        
        public static boolean isNotBlank(String str) {
            return !isBlank(str);
        }
        
        public static String defaultIfBlank(String str, String defaultStr) {
            return isBlank(str) ? defaultStr : str;
        }
    }
    
    /**
     * Inner class for Math utilities.
     */
    public static class MathUtils {
        
        public static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }
        
        public static double clamp(double value, double min, double max) {
            return Math.max(min, Math.min(max, value));
        }
        
        public static boolean isEven(int number) {
            return number % 2 == 0;
        }
        
        public static boolean isOdd(int number) {
            return number % 2 != 0;
        }
        
        public static int abs(int number) {
            return number < 0 ? -number : number;
        }
        
        public static double distance(double x1, double y1, double x2, double y2) {
            double dx = x2 - x1;
            double dy = y2 - y1;
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        public static double lerp(double a, double b, double t) {
            return a + (b - a) * t;
        }
        
        public static double normalize(double value, double min, double max) {
            if (max == min) {
                return 0;
            }
            return (value - min) / (max - min);
        }
        
        public static double map(double value, double inMin, double inMax, double outMin, double outMax) {
            return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
        }
        
        public static boolean isPowerOfTwo(int n) {
            return n > 0 && (n & (n - 1)) == 0;
        }
        
        public static int nextPowerOfTwo(int n) {
            if (n <= 0) {
                return 1;
            }
            n--;
            n |= n >> 1;
            n |= n >> 2;
            n |= n >> 4;
            n |= n >> 8;
            n |= n >> 16;
            return n + 1;
        }
        
        public static long factorial(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Factorial not defined for negative numbers");
            }
            if (n == 0 || n == 1) {
                return 1;
            }
            long result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        }
        
        public static double degreesToRadians(double degrees) {
            return degrees * Math.PI / 180.0;
        }
        
        public static double radiansToDegrees(double radians) {
            return radians * 180.0 / Math.PI;
        }
    }
    
    /**
     * Inner class for Array utilities.
     */
    public static class ArrayUtils {
        
        public static void reverse(int[] array) {
            if (array == null) {
                return;
            }
            int left = 0;
            int right = array.length - 1;
            while (left < right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left++;
                right--;
            }
        }
        
        public static int[] copyOf(int[] array) {
            if (array == null) {
                return null;
            }
            int[] copy = new int[array.length];
            System.arraycopy(array, 0, copy, 0, array.length);
            return copy;
        }
        
        public static boolean contains(int[] array, int value) {
            if (array == null) {
                return false;
            }
            for (int element : array) {
                if (element == value) {
                    return true;
                }
            }
            return false;
        }
        
        public static int indexOf(int[] array, int value) {
            if (array == null) {
                return -1;
            }
            for (int i = 0; i < array.length; i++) {
                if (array[i] == value) {
                    return i;
                }
            }
            return -1;
        }
        
        public static int lastIndexOf(int[] array, int value) {
            if (array == null) {
                return -1;
            }
            for (int i = array.length - 1; i >= 0; i--) {
                if (array[i] == value) {
                    return i;
                }
            }
            return -1;
        }
        
        public static void fill(int[] array, int value) {
            if (array == null) {
                return;
            }
            Arrays.fill(array, value);
        }
        
        public static int[] concatenate(int[] array1, int[] array2) {
            if (array1 == null && array2 == null) {
                return new int[0];
            }
            if (array1 == null) {
                return copyOf(array2);
            }
            if (array2 == null) {
                return copyOf(array1);
            }
            int[] result = new int[array1.length + array2.length];
            System.arraycopy(array1, 0, result, 0, array1.length);
            System.arraycopy(array2, 0, result, array1.length, array2.length);
            return result;
        }
    }
    
    /**
     * Main method for demonstration.
     */
    public static void main(String[] args) {
        LargeFileExample example = new LargeFileExample("Example Instance", 12345, 99.99);
        
        System.out.println("Application: " + APPLICATION_NAME);
        System.out.println("Version: " + VERSION);
        System.out.println("Author: " + AUTHOR);
        System.out.println(COPYRIGHT);
        System.out.println();
        
        System.out.println("Instance: " + example);
        System.out.println();
        
        System.out.println("Testing mathematical operations:");
        System.out.println("Factorial of 5: " + example.calculateFactorial(5));
        System.out.println("Is 17 prime? " + example.isPrime(17));
        System.out.println("Fibonacci sequence (10 terms): " + example.generateFibonacci(10));
        System.out.println("GCD of 48 and 18: " + example.calculateGCD(48, 18));
        System.out.println("LCM of 12 and 15: " + example.calculateLCM(12, 15));
        System.out.println();
        
        System.out.println("Testing string operations:");
        System.out.println("Reverse 'Hello': " + example.reverseString("Hello"));
        System.out.println("Is 'racecar' a palindrome? " + example.isPalindrome("racecar"));
        System.out.println("Vowels in 'Hello World': " + example.countVowels("Hello World"));
        System.out.println("Decimal 42 to binary: " + example.decimalToBinary(42));
        System.out.println();
        
        System.out.println("Testing array operations:");
        int[] testArray = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("Original array: " + Arrays.toString(testArray));
        System.out.println("Maximum: " + example.findMax(testArray));
        System.out.println("Minimum: " + example.findMin(testArray));
        System.out.println("Average: " + example.calculateAverage(testArray));
        System.out.println("Median: " + example.calculateMedian(testArray));
        
        example.quickSort(testArray, 0, testArray.length - 1);
        System.out.println("Sorted array: " + Arrays.toString(testArray));
        System.out.println();
        
        System.out.println("Testing prime number generation:");
        System.out.println("Primes up to 50: " + example.sieveOfEratosthenes(50));
        System.out.println();
        
        System.out.println("Testing number operations:");
        System.out.println("Sum of digits in 12345: " + example.sumOfDigits(12345));
        System.out.println("Reverse of 12345: " + example.reverseNumber(12345));
        System.out.println("Is 153 an Armstrong number? " + example.isArmstrongNumber(153));
        System.out.println();
        
        System.out.println("Random string (10 chars): " + example.generateRandomString(10));
        System.out.println();
        
        System.out.println("Testing word operations:");
        String testString = "the quick brown fox jumps over the lazy dog";
        System.out.println("Test string: " + testString);
        System.out.println("Word count: " + example.countWords(testString));
        System.out.println("Longest word: " + example.findLongestWord(testString));
        System.out.println("Capitalized: " + example.capitalizeWords(testString));
        System.out.println();
        
        System.out.println("Testing anagrams:");
        System.out.println("Are 'listen' and 'silent' anagrams? " + 
                         example.areAnagrams("listen", "silent"));
        System.out.println();
        
        System.out.println("String compression:");
        System.out.println("Compress 'aaabbbccc': " + example.compressString("aaabbbccc"));
        System.out.println("Compress 'abcdef': " + example.compressString("abcdef"));
        
        System.out.println();
        System.out.println("Demonstration completed successfully!");
        
        // Additional demonstrations
        System.out.println();
        System.out.println("=== Extended Features Demonstration ===");
        System.out.println();
        
        System.out.println("Testing advanced string operations:");
        System.out.println("First non-repeating character in 'hello': " + 
                         example.findFirstNonRepeatingChar("hello"));
        System.out.println("Is 'test@example.com' a valid email? " + 
                         example.isValidEmail("test@example.com"));
        System.out.println("Title case 'hello world': " + 
                         example.toTitleCase("hello world"));
        System.out.println("Levenshtein distance between 'kitten' and 'sitting': " + 
                         example.calculateLevenshteinDistance("kitten", "sitting"));
        System.out.println("LCS of 'ABCDGH' and 'AEDFHR': " + 
                         example.longestCommonSubsequence("ABCDGH", "AEDFHR"));
        System.out.println();
        
        System.out.println("Testing sorting algorithms:");
        int[] mergeArray = {38, 27, 43, 3, 9, 82, 10};
        example.mergeSort(mergeArray);
        System.out.println("Merge sorted: " + Arrays.toString(mergeArray));
        int[] heapArray = {12, 11, 13, 5, 6, 7};
        example.heapSort(heapArray);
        System.out.println("Heap sorted: " + Arrays.toString(heapArray));
        System.out.println();
        
        System.out.println("Testing advanced algorithms:");
        int[] lisArray = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Longest increasing subsequence length: " + 
                         example.longestIncreasingSubsequence(lisArray));
        int[] maxSubArray = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum subarray sum: " + example.maxSubarraySum(maxSubArray));
        System.out.println();
        
        System.out.println("Testing number theory:");
        System.out.println("5th Catalan number: " + example.calculateCatalanNumber(5));
        System.out.println("Pascal's triangle (5 rows): " + example.generatePascalTriangle(5));
        System.out.println("Binomial coefficient C(5,2): " + example.binomialCoefficient(5, 2));
        System.out.println("Prime factors of 315: " + example.findPrimeFactors(315));
        System.out.println("Is 16 a perfect square? " + example.isPerfectSquare(16));
        System.out.println("Square root of 25: " + example.calculateSquareRoot(25, 0.0001));
        System.out.println();
        
        System.out.println("Testing Roman numerals:");
        System.out.println("Roman 'XIV' to int: " + example.romanToInt("XIV"));
        System.out.println("Int 1994 to Roman: " + example.intToRoman(1994));
        System.out.println();
        
        System.out.println("Testing date utilities:");
        System.out.println("Is 2024 a leap year? " + example.isLeapYear(2024));
        System.out.println("Days in February 2024: " + example.getDaysInMonth(2, 2024));
        System.out.println();
        
        System.out.println("Testing encoding:");
        String original = "Hello World";
        String encoded = example.base64Encode(original);
        System.out.println("Base64 encoded 'Hello World': " + encoded);
        System.out.println("Base64 decoded: " + example.base64Decode(encoded));
        System.out.println("Generated UUID: " + example.generateUUID());
        System.out.println("Custom hash of 'test': " + example.calculateCustomHash("test"));
        System.out.println();
        
        System.out.println("Testing Matrix operations:");
        int[][] matrixData1 = {{1, 2}, {3, 4}};
        int[][] matrixData2 = {{5, 6}, {7, 8}};
        Matrix m1 = new Matrix(matrixData1);
        Matrix m2 = new Matrix(matrixData2);
        Matrix sum = m1.add(m2);
        System.out.println("Matrix addition result (0,0): " + sum.getValue(0, 0));
        Matrix product = m1.multiply(m2);
        System.out.println("Matrix multiplication result (0,0): " + product.getValue(0, 0));
        System.out.println();
        
        System.out.println("Testing Complex numbers:");
        ComplexNumber c1 = new ComplexNumber(3, 4);
        ComplexNumber c2 = new ComplexNumber(1, 2);
        System.out.println("Complex number 1: " + c1);
        System.out.println("Complex number 2: " + c2);
        System.out.println("Sum: " + c1.add(c2));
        System.out.println("Product: " + c1.multiply(c2));
        System.out.println("Magnitude of c1: " + c1.magnitude());
        System.out.println();
        
        System.out.println("Testing Fractions:");
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        System.out.println("Fraction 1: " + f1);
        System.out.println("Fraction 2: " + f2);
        System.out.println("Sum: " + f1.add(f2));
        System.out.println("Product: " + f1.multiply(f2));
        System.out.println("f1 as decimal: " + f1.toDouble());
        System.out.println();
        
        System.out.println("All demonstrations completed successfully!");
    }
}
