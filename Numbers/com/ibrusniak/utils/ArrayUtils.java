package com.ibrusniak.utils;

public final class ArrayUtils {
    
    public static int[] add(int[] src, int num) {
        int[] target = expand(src, 1);
        target[target.length - 1] = num;
        return target;
    }

    public static int[] expand(int[] src, int count) {
        int[] target = new int[src.length + count];
        target = copy(src, target);
        return target;
    }

    public static int[] narrow(int[] src, int count) {
        int[] target = new int[0];
        if (count < src.length) {
            target = new int[src.length - count];
            target = copy(src, target);
        }
        return target;
    }

    public static int[] copy(int[] src, int[] target) {
        for (int i = 0; i < src.length; i++) {
            if (i + 1 > target.length) {
                break;
            } else {
                target[i] = src[i];
            }
        }
        return target;
    }
}

