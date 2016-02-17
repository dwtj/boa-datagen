package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.5">
 *        15.8.5. Parenthesized Expressions
 *      </a>
 */
class ParenthesizedExpressions {
    void expressions() {
        int i = (1);

        // Note an `if` or `while` condition may be considered a parenthesized expression (e.g.
        // `com.sun.source.tree` API as of Java 8).
        if (true) { }
        if ((true)) { }

        while (true)
            break;
        while ((true))
            break;

        // Not parenthesized by `com.sun.source.tree`:
        for (;;)
            break;

        // TODO: More?
    }
}