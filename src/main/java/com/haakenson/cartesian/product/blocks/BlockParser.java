package com.haakenson.cartesian.product.blocks;

import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 11:09 AM
 */
public class BlockParser {

    private final static Logger LOGGER = Logger.getLogger(BlockParser.class.getName());

    //a recursive structure of string blocks.
    //a block can either be plain text or "variant" which will expand based on the product

    public static final char OPEN = '{';
    public static final char CLOSE = '}';
    public static final char VARIANT_SEPARTOR = ',';

    BlockList blocks = new BlockList();

    String raw;

    /**
     * Utility function that wraps instance methods in single call.
     * @param raw a string containing echo-style cartesian-product notation
     * @return all the products / products produces by the input string
     */
    public static List<String> calculateProduct(String raw) {
        BlockParser parser = new BlockParser(raw);
        return parser.parse().products(new ArrayList<>());
    }

    public BlockParser(String raw) {
        this.raw = raw;
    }

    /**
     * Do the heavy lifting of parsing the string.
     * @return
     */
    private BlockList parse() {
        LOGGER.fine("Processing: " + raw);
        char[] contents = raw.toCharArray();
        boolean inBlock = false;
        StringBuilder current = new StringBuilder();

        VariantBlock currentVariant = null;

        for (int i = 0; i < contents.length; i++) {
            char c = contents[i];
            if (inBlock && c == OPEN) {
                //we're opening a sub-expression+

                //look-ahead to our close and sub-process
                StringBuilder inner = new StringBuilder();
                inner.append(c);
                //if we're not the first open, go into a sub-parser
                int currentlyOpen = 2;//the one we just saw is one-open

                for (int j = i + 1; j < contents.length; j++) {
                    char lookAhead = contents[j];
                    if (lookAhead == CLOSE) {
                        currentlyOpen--;
                        LOGGER.fine("Saw close with inner: " + inner + " count: " + currentlyOpen);

                    } else if (lookAhead == OPEN) {
                        currentlyOpen++;
                        LOGGER.fine("Saw open with inner: " + inner);
                    }
                    if (currentlyOpen == 0) {
                        String subSegment = current.toString() + inner.toString() + lookAhead;
                        LOGGER.fine("Entire sub-segment: " + subSegment);
                        BlockParser sub = new BlockParser(subSegment);
                        if (currentVariant == null) {
                            blocks.add(sub.parse());
                        } else {
                            currentVariant.variants.add(sub.parse());
                        }
                        current = new StringBuilder();
                        i = j;
                        break;
                    }
                    inner.append(lookAhead);
                }
            }  else if (inBlock && c == VARIANT_SEPARTOR) {
                if (current.length() > 0) {
                    currentVariant.variants.add(new ConstantBlock(current.toString()));
                    current = new StringBuilder();
                }
            }  else if (inBlock && c == CLOSE) {
                //create a new block
                if (current.length() > 0) {
                    LOGGER.fine("Reached close block with content: " + current);
                    currentVariant.variants.add(new ConstantBlock(current.toString()));
                }
                currentVariant = null;
                current = new StringBuilder();
                inBlock = false;
            } else if (!inBlock && c == OPEN) {
                inBlock = true;

                currentVariant = new VariantBlock();

                if (current.length() > 0) {
                    LOGGER.fine("Reached open block with content: " + current);
                    blocks.add(new ConstantBlock(current.toString()));
                }

                blocks.add(currentVariant);
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            LOGGER.fine("Ended with content: " + current);
            String s = current.toString();
            if (inBlock) {
                s = OPEN + s;
            }
            blocks.add(new ConstantBlock(s));
        }

        return blocks;
    }

    /**
     * Utility function used by blocks to expand their products.
     * @param base all strings seen so far
     * @param expand new strings seen at this level
     * @return the product of the two lists
     */
    public static List<String> product(List<String> base, List<String> expand) {
        List<String> expanded = new ArrayList<>();
        if (base.size() == 0) {
            expanded.addAll(expand);
        } else {
            for (String b : base) {
                for (String e : expand) {
                    expanded.add(b + e);
                }
            }
        }
        return expanded;
    }

    public static void main(String[] args) {
        //A small main method so I can iterate faster than running the whole test suite

        LOGGER.setLevel(Level.FINER);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        LOGGER.addHandler(handler);

        BlockParser parser = new BlockParser("a{b,c{d,e,f}g,h}ij{k,l}");
        BlockList blocks = parser.parse();

        for (Block block : blocks.getBlocks()) {
            System.out.println(block.getClass().getSimpleName() + " = " + block.toString());
        }

        System.out.println("\nProduct: ");
        System.out.println(String.join(" - ", blocks.products(new ArrayList<>())));
    }

}
