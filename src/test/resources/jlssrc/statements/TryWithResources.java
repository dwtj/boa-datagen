package jlssrc.statements;

import java.io.Closeable;
import java.io.IOException;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20.3">
 *        14.20.3. <code>try</code>-with-resources
 *      </a>
 */
class TryWithResources {
    void statements() throws IOException {

        // Initialize one resource:
        try (Closeable res = null) { }

        // Block contains sub-statements:
        try (Closeable res = null) {
            ;
        }

        // Initialize two resources:
        try (Closeable resA = null; Closeable resB = null) { }

        // Enhanced try-with-resource with only `catch`:
        try (Closeable res = null) { }
        catch (IllegalArgumentException ex) { }

        // Enhanced try-with-resource with only `finally`:
        try (Closeable res = null) { }
        finally { }

        // Enhanced try-with-resource with both `catch` and `finally`:
        try (Closeable res = null) { }
        catch (IllegalArgumentException ex) { }
        finally { }

        // Note that the grammar described in JLS 8 Section 14.20.3 allows for a resource's
        // `VariableDeclaratorId` to be an array (i.e. the identifier can be suffixed with any
        // number of dimension brackets and their associated annotations). However, this can never
        // happen in practice. Such a program does not type check because no array can implement
        // `AutoCloseable`.

        // Resource with annotations:
        try (@A @B Closeable res = null) { }
    }

    private @interface A { }
    private @interface B { }
}