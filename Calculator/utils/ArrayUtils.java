package utils;

public class ArrayUtils {
    
    public static <T> boolean arrayContainsValue(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
}

