package com.haakenson.cartesian.product;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * User: millisecond
 * Date: 6/28/16
 * Time: 10:01 AM
 */
public class TestCartesianProducts {

    @Test
    public void testInputValidation() throws Exception {
        Exception caught = null;
        try {
            CartesianProduct.product(null);
        } catch (Exception e) {
            caught = e;
        }
        assertNotNull(caught);

        caught = null;
        try {
            CartesianProduct.product(new String(new char[CartesianProduct.MAX_INPUT + 1]));
        } catch (Exception e) {
            caught = e;
        }
        assertNotNull(caught);
    }

    @Test
    public void testNoop() throws Exception {
        //from the output of echo
        assertEquals(CartesianProduct.product("abc"), "abc");
        assertEquals(CartesianProduct.product(""), "");
        assertEquals(CartesianProduct.product("{}"), "{}");
        assertEquals(CartesianProduct.product("{a}"), "{a}");
        assertEquals(CartesianProduct.product("{a, b"), "{a, b");
        assertEquals(CartesianProduct.product("{a, b}"), "{a, b}");//echo treats spaces as breaking the set and just prints out raw values
    }

    @Test
    public void testBasic() throws Exception {
        assertCollectionContainsAll(CartesianProduct.products("a{b,c}d"), "abd acd".split(" "));
        assertCollectionContainsAll(CartesianProduct.products("a{b,c}d{e,f,g}hi"), "abdehi acdehi abdfhi acdfhi abdghi acdghi".split(" "));
    }

    @Test
    public void testNested() throws Exception {
        assertCollectionContainsAll(CartesianProduct.products("a{b,c{d,e,f}g,h}ij{k,l}"), "abijk abijl acdgijk acdgijl acegijk acegijl acfgijk acfgijl ahijk ahijl".split(" "));
    }

    /**
     * Verifies that the list and array contain the same values, in any order
     * Length check and 'seen' prevent duplicates from getting through
     * @param product
     * @param split
     */
    private void assertCollectionContainsAll(List<String> product, String[] split) {
        assertEquals(product.size(), split.length);
        Set<String> seen = new HashSet<>();
        for (String s : split) {
            if (seen.contains(s)) {
                fail("We saw duplicates in the product: " + s);
            }
            seen.add(s);
            if (!product.contains(s)) {
                fail("Product didn't contain expected value: " + s);
            }
        }
    }

}
