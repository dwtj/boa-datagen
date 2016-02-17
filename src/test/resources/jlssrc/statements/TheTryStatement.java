package jlssrc.statements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20">
 *        14.20. The <code>try</code> statement
 *      </a>
 */
class TheTryStatement {
    void statements() {

        // Minimal example:
        try {}
        catch (Exception ex) {}

        // Finally without catch:
        try {}
        finally {}

        // With both catch and finally:
        try {}
        catch (Exception ex) {}
        finally {}

        // With two catch types:
        try {}
        catch (IllegalArgumentException | NullPointerException ex) {}

        // With two catch clauses:
        try {}
        catch (IllegalArgumentException ex) {}
        catch (NullPointerException ex) {}


        // With annotations on the catch's formal parameter:
        try {}
        catch (@A @B IllegalArgumentException ex) {}

        // With `final` on catch's formal parameter:
        try {}
        catch (final IllegalArgumentException ex) {}

        // With (empty) sub-statements:
        try {
            ;
        } catch (IllegalArgumentException ex) {
            ;
        } catch (NullPointerException ex) {
            ;
        } finally {
            ;
        }
    }

    private @interface A { }

    private @interface B { }
}