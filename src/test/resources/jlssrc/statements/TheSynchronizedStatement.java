package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.19">
 *        14.19. The <code>synchronized</code> Statement
 *      </a>
 */
class TheSynchronizedStatement {
    void statements() {
        synchronized(this) {
            ;
        }
    }
}