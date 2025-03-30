class InversionCounter {

    public static long countSwaps(int[] arr) {
        
        return mergesortAndCount(arr, 0, arr.length - 1);
    }

    public static long mergesortAndCount(int[] arr, int left, int right) {
        if (left >= right) return 0;
        
        int mid = left + (right - left) / 2;
        long count = 0L;

        count += mergesortAndCount(arr, left, mid);
        count += mergesortAndCount(arr, mid + 1, right);
        count += mergeAndCount(arr, left, mid, mid + 1, right);

        return count;
    }

    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int i = left1, j = left2, k = 0;
        long count = 0L;

        int[] temp = new int[right2 - left1 + 1]; 

        while (i <= right1 && j <= right2) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += (right1 - i + 1); 
            }
        }

        while (i <= right1) {
            temp[k++] = arr[i++];
        }

        while (j <= right2) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left1 + x] = temp[x];
        }

        return count;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(countSwaps(arr));
        arr = new int[]{5, 4, 3, 2, 1};
        System.out.println(countSwaps(arr));
        arr = new int[]{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2, 1};
        System.out.println(countSwaps(arr));
        arr = new int[]{1, 3, 3, 2, 2};
        System.out.println(countSwaps(arr));

        int[] arr1 = {};
        int[] arr2 = {42};

        System.out.println(countSwaps(arr1));  // Expected: 0
        System.out.println(countSwaps(arr2));  // Expected: 0

        int[] largeArr = new int[100000];
        for (int i = 0; i < 100000; i++) largeArr[i] = 100000 - i;

        System.out.println(countSwaps(largeArr));
    }
}
