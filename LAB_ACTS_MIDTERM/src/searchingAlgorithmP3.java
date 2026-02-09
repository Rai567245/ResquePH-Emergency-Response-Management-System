import java.util.Arrays;
import java.util.Scanner;

public class searchingAlgorithmP3 {
    
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1; 
            } else {
                right = mid - 1; 
            }
        }
        return -1; 
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of an array: ");
        int size = sc.nextInt();

        int[] arr = new int[size];
        
        System.out.print("Enter " + size + " array elements: ");
        for(int i = 0; i < size; i++) {
        	arr[i] = sc.nextInt();
        }
        
        Arrays.sort(arr);

        while (true) {
            System.out.println("Elements to be searched: " + Arrays.toString(arr));

            System.out.print("Enter element to be searched: ");
            int target = sc.nextInt();

            int index = binarySearch(arr, target);

            if (index != -1) {
                System.out.println("Output: Found in index " + index);
            } else {
                System.out.println("Output: Element not found");
            }

            System.out.print("Press X to stop: ");
            String choice = sc.next();
            System.out.println();

            if (choice.equalsIgnoreCase("X")) {
                System.out.println("THANK YOU!");
                break;
            }
        }
        sc.close();
    }
}
