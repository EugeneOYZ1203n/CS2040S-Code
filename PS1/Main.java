package PS1;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int key = 11;
        int n = 12;
        int[] A = new int[] {2, 4, 4, 5, 6, 7, 8, 9, 11, 17, 23, 28};

        int begin = 0;
        int end = n - 1;
        int k = 0;
        while (begin < end) {
            int mid = begin + (end - begin) / 2;
            if (key <= A[mid]) {
                end = mid;
            } else {
                begin = mid+1;
            }
            System.out.println(A[mid]);
                
            k += 1;
        }
            
        System.out.println(A[begin]);
        System.out.println(k);
    }
}