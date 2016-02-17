package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.10.1">
 *        15.10.1. Array Creation Expressions
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html#jls-10.6">
 *        10.6. Array Initializers
 *      </a>
 */
class NewArrayExpressions {

    void referenceArrays() {
        Object[] initializedEmptyArray = {};
        Object[] initializedArray = { null, null, null, null };
        Object[] createdArray = new Object[4];
        Object[] createdInitializedArray = new Object[] { null, null, null, null };
        Object[] createdInitializedEmptyArray = new Object[] {};
    }

    void primitiveArrays() {
        int[] initializedEmptyArray = {};
        int[] initializedArray = { 1, 2, 3, 4 };
        int[] createdArray = new int[4];
        int[] createdInitializedArray = new int[] { 1, 2, 3, 4 };
        int[] createdInitializedEmptyArray = new int[] {};
    }

    void doubleReferenceArrays() {
        Object[][] initializedArrayWithEmptyArray = {{}};
        Object[][] initializedArrayWithEmptyArrays = {{}, {}};
        Object[][] initializedArray = { {null, null}, {null, null}, };
        Object[][] createdArray = new Object[2][2];
        Object[][] createdInitializedArray = new Object[][] { {null, null}, {null, null}, };
        Object[][] createdInitializedArrayWithEmptyArray = new Object[][] {{}};
        Object[][] createdInitializedArrayWithEmptyArrays = new Object[][] {{}, {}};
    }

    void doublePrimitiveArrays() {
        int[][] initializedArrayWithEmptyArray = {{}};
        int[][] initializedArrayWithEmptyArrays = {{}, {}};
        int[][] initializedArray = { {1, 2}, {3, 4}, };
        int[][] createdArray = new int[2][2];
        int[][] createdInitializedArray = new int[][] { {1, 2}, {3, 4}, };
        int[][] createdInitializedArrayWithEmptyArray = new int[][] {{}};
        int[][] createdInitializedArrayWithEmptyArrays = new int[][] {{}, {}};
    }

    // In these examples, only one of two dimensions are initialized.
    void partiallyInitializedArrays() {
        Object[][] initializedEmptyArray = {};
        Object[][] initializedArray = { null, null, };
        Object[][] createdArray = new Object[2][];
        Object[][] createdInitializedArray = new Object[][] { null, null, };
        Object[][] createdInitializedEmptyArray = new Object[][] {};
    }

    // TODO: Dim Annotations
}