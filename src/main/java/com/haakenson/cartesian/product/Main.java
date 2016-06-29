package com.haakenson.cartesian.product;

/**
 * A simple command-line wrapper around the Cartesian Product code.
 */
public class Main {

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Exaclty one argument required in this format: a{b,c}d{e,f,g}hi");
            return;
        }
        System.out.println(CartesianProduct.product(args[0]));
    }

}
