package com.haakenson.cartesian.product;

import com.haakenson.cartesian.product.blocks.BlockParser;
import com.haakenson.cartesian.product.blocks.ConstantBlock;

import java.util.List;

/**
 * Wrap the core functionality in nice and clean static methods callable by the rest of the code.
 *
 * User: millisecond
 * Date: 6/28/16
 * Time: 10:30 AM
 */
public class CartesianProduct {

    public static final int MAX_INPUT = 1000;

    /**
     * Returns the separated products produced by the input string
     * @param in the string to expand, i.e. a{b,c}d{e,f,g}hi
     * @return
     */
    public static List<String> products(String in) {
        validateInput(in);
        return BlockParser.calculateProduct(in);
    }

    /**
     * Returns the concatenated products produced by the input string, useful for testing assertEquals
     * @param in the string to expand, i.e. a{b,c}d{e,f,g}hi
     * @return
     */
    public static String product(String in) {
        validateInput(in);
        return String.join(" ", BlockParser.calculateProduct(in));
    }

    public static void validateInput(String in) {
        if (in == null) {
            throw new IllegalArgumentException("Null input not allowed");
        }
        if (in.length() > MAX_INPUT) {
            throw new IllegalArgumentException("Input exceeded max size: " + MAX_INPUT + " with size: " + in.length());
        }
    }

}
