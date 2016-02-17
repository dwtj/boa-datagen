package jlssrc.expressions;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.3">
 *        15.8.3. <code>this</code>
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.4">
 *        15.8.4. Qualified <code>this</code>
 *      </a>
 */
class ThisExpressions {
    class Inner {
        void expressions() {
            Inner i = this;
            ThisExpressions outer = ThisExpressions.this;
        }
    }
}

