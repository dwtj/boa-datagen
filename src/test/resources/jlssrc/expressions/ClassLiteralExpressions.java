package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.2">
 *        15.8.2. Class Literal Expressions
 *      </a>
 */
class ClassLiteralExpression {
    void expressions() {
        Class<?> cls = null;
        cls = Object.class;
        cls = Object[].class;
        cls = int.class;
        cls = boolean.class;
        cls = boolean[].class;
        cls = void.class;
    }
}