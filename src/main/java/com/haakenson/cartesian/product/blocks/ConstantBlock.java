package com.haakenson.cartesian.product.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent a non-varying string in the Block-tree
 *
 * User: millisecond
 * Date: 6/28/16
 * Time: 10:26 AM
 */
public class ConstantBlock implements Block {

    //always a list of one, see constructor
    List<String> variants;

    public ConstantBlock(String raw) {
        if (raw == null || raw.length() == 0) {
            throw new IllegalArgumentException("Invalid argument, cannot be null or empty: " + raw);
        }

        //pre-calc the list so we don't allocate a ton of them
        this.variants = Collections.singletonList(raw);
    }

    public List<String> products(List<String> base) {
        return BlockParser.product(base, this.variants);
    }

    @Override
    public String toString() {
        return String.join(" ", variants);
    }

}
