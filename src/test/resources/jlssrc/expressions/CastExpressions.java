package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">
 *        15.16. Cast Expressions
 *      </a>
 */
class CastExpressions {
    void expressions() {
        String empty = "";
        Object obj = (String) empty;
        String str = ((Object) empty).toString();
        empty = (String) obj;
    }
}