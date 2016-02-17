package jlssrc.statements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.1">
 *        14.14.1. The Basic <code>for</code> Statement
 *      </a>
 */
class TheBasicForStatement {

    void infiniteLoopWithStatement() {
        for (;;)
            ;
    }

    void infiniteLoopWithBlock() {
        for (;;) {
            // Nothing needed here.
        }
    }

    void basicExample() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
    }

    // See [the grammar](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.1).
    void localVariableDeclarationInLoopInitializer() {
        // Dummy variable over which loops can iterate. It is used to prevent infinite loop
        // detection (and therefore unreachable code compile-errors).
        int i = 0;

        // Local variable declaration without initializer
        for (int j;;)
            break;

        // Local variable declaration with initializer
        for (int j = 0;;)
            break;

        // Local variable declaration has variable declarators list.
        // See: https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.1
        for (int j, k; ;)
            break;
    }

    void statementExpressionListInInitializer() {
        int i, j;

        // One statement expression.
        for (i = 0;;)
            break;

        // Two statement expressions.
        for (i = 0, j = 0;;)
            break;
    }
}