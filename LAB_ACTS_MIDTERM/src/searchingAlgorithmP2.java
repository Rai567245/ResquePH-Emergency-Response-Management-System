import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class searchingAlgorithmP2 {

    // Linear Search Method for multiple occurrences
    public static ArrayList<Integer> linearSearchMultiple(int[] arr, int target) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                indices.add(i);
            }
        }
        return indices;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter array size: ");
            int size = sc.nextInt();

            if (size <= 0) {
                System.out.println("Array size must be greater than 0");
                return;
            }

            int[] arrElements = new int[size];
            System.out.print("Enter " + size + " array elements: ");
            for (int i = 0; i < size; i++) {
                arrElements[i] = sc.nextInt();
            }

            System.out.print("Enter element to be searched: ");
            int userInput = sc.nextInt();

            // Call the multiple-occurrence search method
            ArrayList<Integer> indices = linearSearchMultiple(arrElements, userInput);

            if (!indices.isEmpty()) {
                System.out.print("Element " + userInput + " found at index ");
                for (int i = 0; i < indices.size(); i++) {
                    System.out.print(indices.get(i));
                    if (i < indices.size() - 1) {
                        System.out.print(" and index ");
                    }
                }
                System.out.println();
            } else {
                System.out.println("Element " + userInput + " not found");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter integers only.");
        }

        sc.close();
    }
}
