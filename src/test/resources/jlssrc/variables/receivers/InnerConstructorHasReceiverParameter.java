package jlssrc.variables.receivers;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.1">
 *         JLS 8 Section 8.4.1</a>
 */
class InnerConstructorHasReceiverParameter {
    class Inner {
        Inner(InnerConstructorHasReceiverParameter InnerConstructorHasReceiverParameter.this) {
            // Nothing needed here.
        }
    }
}