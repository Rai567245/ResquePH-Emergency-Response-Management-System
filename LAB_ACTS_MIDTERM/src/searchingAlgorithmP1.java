import java.util.Scanner;
public class searchingAlgorithmP1 {

    // Linear Search Method
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // return index if found
            }
        }
        return -1; // return -1 if not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Allow user input for array elements
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arrElements = new int[n];

        System.out.print("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arrElements[i] = sc.nextInt();
        }
 
        // Display the array
        System.out.print("Array Elements: [");
        for (int i = 0; i < arrElements.length; i++) {
            System.out.print(arrElements[i]);
            if (i < arrElements.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Ask user for element to search
        System.out.print("Enter element to be searched: ");
        int userInput = sc.nextInt();

        // Call linearSearch method
        int index = linearSearch(arrElements, userInput);

        if (index != -1) {
            System.out.println("Found at index " + index);
        } else {
            System.out.println("Element " + userInput + " not found");
        }
    }
}
