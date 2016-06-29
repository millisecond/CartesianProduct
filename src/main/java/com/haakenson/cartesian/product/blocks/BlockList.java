package com.haakenson.cartesian.product.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * User: millisecond
 * Date: 6/29/16
 * Time: 9:15 AM
 */
public class BlockList implements Block {

    //Go with has-a rather than is-a to simplify hierarchy and not worry about all the unrelated collection methods
    private List<Block> blocks = new ArrayList<>();

    public BlockList() {

    }

    public void add(Block block) {
        this.blocks.add(block);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    @Override
    public List<String> products(List<String> base) {
        List<String> ret = new ArrayList<>();
        for (Block block : blocks) {
            ret = BlockParser.product(ret, block.products(new ArrayList<>()));
        }
        return BlockParser.product(base, ret);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("<");
        for (Block variant : blocks) {
            ret.append(variant.getClass().getSimpleName() + ": " + variant.toString() + " | ");
        }
        ret.append(">");
        return ret.toString();
    }

}
