package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.15">
 *        14.15. The <code>break</code> Statement
 *      </a>
 */
class TheBreakStatement {
    void statements() {
        while (true) {
            break;
        }

outer:
        while (true) {
            while (true) {
                break outer;
            }
        }
    }
}