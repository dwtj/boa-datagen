package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.18">
 *        14.18. The <code>throw</code> Statement
 *      </a>
 */
class TheThrowStatement {
    void throwNull() {
        throw null;
    }
    void throwRuntimeException() {
        throw new RuntimeException("Oh, no!");
    }
}