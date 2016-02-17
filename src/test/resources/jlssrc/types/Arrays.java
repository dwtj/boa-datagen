package jlssrc.types;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

// https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html#jls-10.2

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html#jls-10.2">
 *        10.2. Array Variables
 *      </a>
 */
class Arrays {

    int[][] array1;

    Object[][] array2;

    int[] array3;

    Object[] array4;

    @A @B Object[] array5;

    // Annotate each of the dimensions of the array with two annotations:
    Object @A @B[] @A @B[] array6;

    @Target({ElementType.TYPE_USE, ElementType.FIELD})
    private @interface A { }

    @Target({ElementType.TYPE_USE, ElementType.FIELD})
    private @interface B { }
}