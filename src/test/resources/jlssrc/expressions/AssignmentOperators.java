package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26">
 *        15.26. Assignment Operators
 *      </a>
 */
class AssignmentOperators {
    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26
    void expressions() {
        int x = 0;
        int y = 1;
        x = y;
        x *= y;
        x /= y;
        x %= y;
        x += y;
        x -= y;
        x <<= y;
        x >>= y;
        x >>>= y;
        x &= y;
        x ^= y;
        x |= y;
    }
}