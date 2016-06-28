package com.haakenson.cartesian.product.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 11:01 AM
*/
public class VariantBlock implements Block {

    List<Block> variants = new ArrayList<>();

    public VariantBlock(List<Block> variants) {
        this.variants = variants;
    }

    @Override
    public List<String> variants() {
        return BlockParser.product(variants);
    }

}
