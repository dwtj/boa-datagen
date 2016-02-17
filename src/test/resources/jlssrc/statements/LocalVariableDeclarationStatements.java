package jlssrc.statements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.4">
 *        14.4. Local Variable Declaration Statements
 *      </a>
 */
class LocalVariableDeclarationStatements {
    void scalarDeclarations() {
        int a;
        int b = 0;
        int c, d;
        int e = 0, f = 0;
        final int g = 0;
        @A int h;
        @A @B int i;
        @A Object j;
        @A @B Object k;
    }

    void arrayDeclarations() {
        int[] arr0;
        int[] arr1, arr2;
        int arr3 @A [];
        int arr4 @A @B [];
        int arr5 @A[] @B[];
    }

    @Target(ElementType.TYPE_USE)
    private @interface A { }

    @Target(ElementType.TYPE_USE)
    private @interface B { }
}