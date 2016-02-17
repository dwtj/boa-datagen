package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.10.3">
 *        15.10.3. Array Access Expressions
 *      </a>
 */
class ArrayAccessExpressions {

    int[] array = new int[4];
    int[][] doubleArray = new int[2][2];

    void expressions() {
        int x;
        int[] xs;
        x = array[0];
        x = doubleArray[0][0];
        xs = doubleArray[0];
    }
}