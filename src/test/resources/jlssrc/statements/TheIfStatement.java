package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.9">
 *        14.9. The <code>if</code> Statement
 *      </a>
 */
class TheIfStatement {
    void statements() {

        // If has statement:
        if (true)
            ;

        // If has block:
        if (true) {
            // Nothing needed here.
        }

        // Else has statement:
        if (true) {
            // Nothing needed here.
        } else
            ;

        // Else has block:
        if (true) {
            // Nothing needed here.
        } else {
            // Nothing needed here.
        }

        // Condition is slightly more interesting:
        if (1 < 2) {
            // Nothing needed here.
        }
    }
}