package com.haakenson.cartesian.product.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 10:26 AM
 */
public class ConstantBlock implements Block {

    List<String> variants;

    public ConstantBlock(String raw) {
        //pre-calc the list so we don't allocate a ton of them
        this.variants = Collections.singletonList(raw);
    }

    public List<String> variants() {
        return variants;
    }

}
