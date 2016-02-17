package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.10">
 *        14.10. The <code>assert</code> Statement
 *      </a>
 */
class TheAssertStatement {
    void assertWithoutMessage() {
        assert false;
    }
    void assertWithMessage() {
        assert false: "message";
    }
}