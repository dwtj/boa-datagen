package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.1">
 *        15.8.1. Lexical Literals
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10">
 *        3.10. Literals
 *      </a>
 */
class Literals {

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1
    int decimalInt = 0;
    int hexadecimalInt = 0x0;
    int octalInt = 00;
    int binaryInt = 0b0;
    long decimalLong = 0l;
    long hexadecimalLong = 0x0l;
    long octalLong = 00l;
    long binaryLong = 0b0l;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2
    float f = 0.0f;
    double d = 0.0d;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3
    boolean b = false;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.4
    char ch = '0';

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.5
    String str = "one";

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.6
    String strEsc = "\n";

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.7
    Object nil = null;
}