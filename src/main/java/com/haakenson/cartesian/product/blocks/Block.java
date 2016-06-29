package com.haakenson.cartesian.product.blocks;

import java.util.List;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 11:03 AM
 */
public interface Block {

    /**
     * Combine the strings produced by this block with the base strings.
     * @param base all products seen so far.
     * @return the combined products
     */
    List<String> products(List<String> base);

}
