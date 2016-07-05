package com.ywg.androidtools.utils;

/**
 *
 * 对象操作
 */
public class ObjectUtils {

    /**
     * Returns true if a and b are equal.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {
        return a == b || (a == null ? b == null : a.equals(b));
    }

}