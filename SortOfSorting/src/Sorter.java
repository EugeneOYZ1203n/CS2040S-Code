class Sorter {

    public static void sortStrings(String[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            String key = arr[i];
            int j = i - 1;
            
            // Move elements that are greater than key one position ahead
            while (j >= 0 && isGreaterThan(arr[j], key)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static boolean isGreaterThan(String str1, String str2) {
        if (str1.charAt(0) > str2.charAt(0)) {
            return true;
        }
        
        if (str1.charAt(0) < str2.charAt(0)) {
            return false;
        }

        return str1.charAt(1) > str2.charAt(1);
    }
}
