package com.haakenson.cartesian.product.blocks;

import java.util.*;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 11:09 AM
 */
public class BlockParser {

    //a recursive structure of string blocks.
    //a block can either be plain text or "variant" which will expand based on the product

    public static final char OPEN = '{';
    public static final char CLOSE = '}';
    public static final String VARIANT_SEPARTOR = ",";

    public static List<String> calculateProduct(String raw) {
        return product(parse(raw));
    }

    private static List<Block> parse(String raw) {
        System.out.println("Processing: " + raw);
        char[] contents = raw.toCharArray();
        boolean inBlock = false;
        StringBuilder current = new StringBuilder();

        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < contents.length; i++) {
            char c = contents[i];

            if (inBlock && c == OPEN) {
                //we're opening a sub-expression
                //look-ahead to the next close and create
                System.out.println("Opening a sub-expression with: " + current);

                StringBuilder inner = new StringBuilder();
                for (int j = i + 1; j < contents.length; j++) {
                    char lookAhead = contents[j];
                    if (lookAhead == CLOSE) {
                        System.out.println("Sub-parsing: " + inner.toString());
                        blocks.addAll(parse(OPEN + inner.toString() + CLOSE));
                        i = j;
                        break;
                    }
                    inner.append(lookAhead);
                }
            } else if (inBlock && c == CLOSE) {
                //create a new block
                if (current.length() > 0) {
                    System.out.println("Reached close block with content: " + current);

                    String[] split = current.toString().split(VARIANT_SEPARTOR);
                    if (split.length > 1) {
                        boolean hasSpaces = false;
                        for (String s : split) {
                            if (s.contains(" ")) {
                                hasSpaces = true;
                                break;
                            }
                        }
                        if (hasSpaces) {
                            //special case: echo {a, b} prints as {a, b}
                            blocks.add(new ConstantBlock(OPEN + current.toString() + CLOSE));
                        } else {
//                            blocks.add(new VariantBlock(Arrays.asList(split)));
                        }
                    } else {
                        //special case: echo {a} prints as {a}
                        blocks.add(new ConstantBlock(OPEN + current.toString() + CLOSE));
                    }
                } else {
                    //special case: an empty block is printed as {} by echo, let's do the same
                    blocks.add(new ConstantBlock(String.valueOf(OPEN) + String.valueOf(CLOSE)));
                }
                current = new StringBuilder();
                inBlock = false;
            } else if (!inBlock && c == OPEN) {
                inBlock = true;
                if (current.length() > 0) {
                    System.out.println("Reached open block with content: " + current);
                    blocks.add(new ConstantBlock(current.toString()));
                }
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            System.out.println("Ended with content: " + current);
            String s = current.toString();
            if (inBlock) {
                s = OPEN + s;
            }
            blocks.add(new ConstantBlock(s));
        }

        return blocks;
    }

    //package access so variants can also call this
    static List<String> product(List<Block> blocks) {
        return product(0, blocks);
    }

    private static List<String> product(int index, List<Block> blocks) {
        List<String> expanded = new ArrayList<>();
        for (Block block : blocks) {
            List<String> variants = block.variants();
            if (expanded.size() == 0) {
                expanded.addAll(variants);
            } else {
                List<String> nextStep = new ArrayList<>();
                for (String variant : variants) {
                    for (String orig : expanded) {
                        nextStep.add(orig + variant);
                    }
                }
                expanded = nextStep;
            }
        }
        return expanded;
    }

    public static void main(String[] args) {
        //A small main method so I can iterate faster than running the whole test suite

//        List<Block> blocks = parse("{a}");
//        List<Block> blocks = parse("{a, b");
        List<Block> blocks = parse("{car,{dump,pickup},plane}s");
//        List<Block> blocks = parse("{car,{dump,pickup}truck,plane}s");
//        List<Block> blocks = parse("{car,{dump,pick{up, down}}truck,plane}s");
        for (Block block : blocks) {
            System.out.println(block.getClass().getSimpleName() + " = " + String.join(" | ", block.variants()));
        }

        System.out.println("\nProduct: ");
        System.out.println(String.join("-", product(blocks)));
    }
}
