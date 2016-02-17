package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.13">
 *        14.13. The <code>do</code> Statement
 *      </a>
 */
class TheDoStatement {
    void doWithStatement() {
        do
            ;
        while (true);
    }

    void doWithBlock() {
        do {
            ;
        } while (true);
    }
}