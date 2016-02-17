package jlssrc.expressions;

import java.lang.reflect.Field;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.11">
 *        15.11. Field Access Expressions
 *      </a>
 */
class FieldAccessExpressions extends Supertype {

    class Inner extends Supertype {
        int field = 1;

        void expressions() {
            int i;
            i = this.field;  // 2
            i = super.field; // 1
            i = FieldAccessExpressions.super.field;
        }
    }

}

class Supertype {
    int field = 2;
}
