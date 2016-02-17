package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.16">
 *        14.16. The <code>continue</code> Statement
 *      </a>
 */
class TheContinueStatement {
    void continueWhile() {
        while (true) {
            continue;
        }
    }

    void continueToLabel() {
        label: while (true) {
            while (true) {
                continue label;
            }
        }
    }
}