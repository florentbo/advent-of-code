package be.bonamis.advent.utils;

import java.util.Arrays;

public class EqualArrays {

    public static void main(String[] args) {

        /*
         *  Please note that the same API applies to any type of arrays e.g.
         *  boolean[][], byte[][], char[][], double[][], float[][], long[][], short[][], String[][] etc
         */





        int[][] a1 = {{1, 2, 3}, {5, 19, 56}, {289, 100, 30}};


        int[][] a2 = {{1, 2, 3}, {5, 19, 56}, {289, 100, 30}};




        if (equal(a1, a2)) {


            System.out.println("The arrays are equal!");

        } else {


            System.out.println("The arrays are not equal");

        }

    }

    public static boolean equal(final int[][] arr1, final int[][] arr2) {

        if (arr1 == null) {


            return (arr2 == null);

        }


        if (arr2 == null) {


            return false;

        }


        if (arr1.length != arr2.length) {


            return false;

        }


        for (int i = 0; i < arr1.length; i++) {


            if (!Arrays.equals(arr1[i], arr2[i])) {


                return false;


            }

        }

        return true;
    }
}
