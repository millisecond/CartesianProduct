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
        assertEquals("abc", CartesianProduct.product("abc"));
        assertEquals("", CartesianProduct.product(""));
    }

    @Test
    public void testBasic() throws Exception {
        assertCollectionContainsAll("abd acd".split(" "), CartesianProduct.products("a{b,c}d"));
        assertCollectionContainsAll("ab ac".split(" "), CartesianProduct.products("a{b,c}"));
        assertCollectionContainsAll("ba ca".split(" "), CartesianProduct.products("{b,c}a"));
        assertCollectionContainsAll("b c".split(" "), CartesianProduct.products("{b,c}"));
        assertCollectionContainsAll("abdehi acdehi abdfhi acdfhi abdghi acdghi".split(" "), CartesianProduct.products("a{b,c}d{e,f,g}hi"));
    }

    @Test
    public void testNested() throws Exception {
        assertCollectionContainsAll("ba aa da ca".split(" "), CartesianProduct.products("{b,{a,d,c}}a"));
        assertCollectionContainsAll("abijk abijl acdgijk acdgijl acegijk acegijl acfgijk acfgijl ahijk ahijl".split(" "), CartesianProduct.products("a{b,c{d,e,f}g,h}ij{k,l}"));
    }

    /**
     * Verifies that the list and array contain the same values, in any order
     * Length check and 'seen' prevent duplicates from getting through
     * @param split
     * @param product
     */
    private void assertCollectionContainsAll(String[] split, List<String> product) {
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
