package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17">
 *        15.17. Multiplicative Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">
 *        15.18. Additive Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">
 *        15.19. Shift Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.20">
 *        15.20. Relational Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.21">
 *        15.21. Equality Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22">
 *        15.22. Bitwise and Logical Operators
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">
 *        15.23. Conditional-And Operator <code>&&</code>
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">
 *        15.24. Conditional-Or Operator <code>||</code>
 *      </a>
 */
class BinaryOperators {
    void expressions() {
        int i;
        boolean b;
        String str;

        // 15.17. Multiplicative Operators
        i = 0 * 1;
        i = 0 / 1;
        i = 0 % 1;

        // 15.18. Additive Operators
        i = 0 + 1;
        i = 0 - 1;
        str = "foo" + "bar";

        // 15.19. Shift Operators
        i = 0 << 0;
        i = 0 >> 0;
        i = 0 >>> 0;

        // 15.20. Relational Operators
        b = 1 < 2;
        b = 1 > 2;
        b = 1 <= 2;
        b = 1 >= 2;
        b = "str" instanceof Object;

        // 15.21. Equality Operators
        b = 0 != 0;
        b = 0 == 0;
        b = null == null;
        b = null != null;

        // 15.22.1. Integer Bitwise Operators
        i = 0 & 0;
        i = 0 ^ 0;
        i = 0 | 0;

        // 15.22.2. Boolean Logical Operators
        b = false & false;
        b = false ^ false;
        b = false | false;

        // 15.23. Conditional-And Operator
        b = false && false;

        // 15.24. Conditional-Or Operator
        b = false || false;
    }
}

