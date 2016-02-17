package jlssrc.statements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.2">
 *        14.14.2. The Enhanced <code>for</code> Statement
 *      </a>
 */
class TheEnhancedForStatement {
    void statements() {

        Object[] arr = new Object[10];
        Object[][][] trippleArr = new Object[2][2][2];

        for (Object obj : arr) { }

        for (@A @B Object annotated : arr) { }

        for (final Object obj : arr) { }

        // Apply two annotations to each of the dimensions of the declared double array elements:
        for (Object doubleArr @A @B[] @A @B[] : trippleArr) { }
    }

    @Target(ElementType.TYPE_USE)
    private @interface A { }

    @Target(ElementType.TYPE_USE)
    private @interface B { }
}