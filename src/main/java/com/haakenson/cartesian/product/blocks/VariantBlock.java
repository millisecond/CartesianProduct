package com.haakenson.cartesian.product.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * A variant like {a,b,c} in the Block-tree; this would be three ConstantBlocks in this.variants.
 *
 * User: millisecond
 * Date: 6/28/16
 * Time: 11:01 AM
*/
public class VariantBlock implements Block {

    List<Block> variants = new ArrayList<>();

    public VariantBlock() {

    }

    @Override
    public List<String> products(List<String> base) {
        List<String> product = new ArrayList<>();
        for (Block variant : variants) {
            product.addAll(variant.products(new ArrayList<>()));
        }
        return BlockParser.product(base, product);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("{");
        for (Block variant : variants) {
            ret.append(variant.getClass().getSimpleName() + ": " + variant.toString() + " | ");
        }
        ret.append("}");
        return ret.toString();
    }

}
