package jlssrc.expressions;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.9">
 *        15.9. Class Instance Creation Expressions
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.9.2">
 *        15.9.2. Determining Enclosing Instances
 *      </a>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.9.5">
 *        15.9.5. Anonymous Class Declarations
 *      </a>
 */
class ClassInstanceCreationExpressions {

    private @interface Anno { }

    class Inner {}

    void expressions() {

        // Class instance creation:
        Object obj = new Object();

        // Type parameter:
        List<String> list1 = new ArrayList<String>();

        // Diamond operator:
        List<String> list2 = new ArrayList<>();

        // Fully qualified type identifier.
        List<String> list3 = new java.util.ArrayList<>();

        // Annonymous class declaration:
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Nothing needed here.
            }
        };

        // TODO: Add more examples with explicit enclosing instance. (See Sections 15.9 and 15.9.2)
        this.new Inner();

        // Annotated fully qualified type identifier.

        // TODO: What's wrong with this? Where can the annotations go?
        //List<String> list4 = new @Anno java . @Anno util . @Anno ArrayList<>();

        // TODO: Add example where identifier is qualified.

        // TODO: Add more?
    }
}

