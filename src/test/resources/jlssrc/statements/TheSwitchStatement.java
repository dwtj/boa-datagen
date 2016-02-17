package jlssrc.statements;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.11">
 *        14.11. The <code>switch</code> Statement
 *      </a>
 */
class TheSwitchStatement {

    enum Cases {A, B};

    void statements() {
        char ch = 'A';

        // Break statements in every case.
        switch (ch) {
            case 'A':
                break;
            case 'B':
                break;
            case 'C':
                break;
        }

        // In each case, do something (rotate A -> B -> C -> A) and then break.
        switch (ch) {
            case 'A':
                ch = 'B';
                break;
            case 'B':
                ch = 'C';
                break;
            case 'C':
                ch = 'A';
                break;
        }

        switch (ch) {
            case 'A':
                ;
            case 'B':
                ;
            default:
                ;
        }

        Cases c = Cases.A;
        switch (c) {
            case A:
                ;
            case B:
                ;
            default:
                ;
        }
    }
}