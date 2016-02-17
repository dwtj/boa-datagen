package jlssrc.expressions;

/**
 *  * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14">
 *        15.14. Postfix Expressions
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15">
 *        15.15. Unary Operators
 *      </a>
 */

class UnaryOperators {
    void expressions() {
        int x = 0;
        int y = 0;

        // Section 15.14. Postfix Expressions
        x++;
        x--;

        // Section 15.15. Unary Expressions
        ++x;
        --x;
        x = +y;
        x = -y;
        x = ~y;

        boolean b = !false;
    }
}