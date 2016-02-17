package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.12">
 *        14.12. The <code>while</code> Statement
 *      </a>
 */
class TheWhileStatement {
    void infiniteLoopWithStatement() {
        while (true)
            ;
    }

    void infiniteLoopWithBlock() {
        while (true) {
            // Nothing needed here.
        }
    }

    void infiniteLoopWithSlightlyMoreInterestingCondition() {
        while (1 < 2) {
            // Nothing needed here.
        }
    }
}