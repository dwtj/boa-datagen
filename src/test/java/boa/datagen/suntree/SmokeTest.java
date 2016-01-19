/*
 * Copyright 2016, Hridesh Rajan, Robert Dyer, David Johnston
 *                 and Iowa State University of Science and Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boa.datagen.suntree;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertEquals;

/**
 * @author dwtj
 */
public class SmokeTest {

    TestProcessor proc;

    @Before
    public void setUp() {
        proc = new TestProcessor();
    }

    @Test
    public void smoke() {
        assert_().about(javaSource())
                .that(forSourceString("HelloWorld", "@TestInput class HelloWorld {}"))
                .processedWith(proc)
                .failsToCompile();   // Because `@TestInput` wasn't imported or fully qualified.
    }

    @Test
    public void noSmokeWithoutAnnotation() {
        assert_().about(javaSource())
                .that(forSourceString("HelloWorld", "class HelloWorld {}"))
                .processedWith(proc)
                .compilesWithoutError();

        // Expect one source to be processed, even if it isn't annotated.
        assertEquals(1, proc.getASTRoots().size());
    }

    @Test
    public void noSmokeWithAnnotation() {
        assert_().about(javaSource())
                .that(forSourceLines("HelloWorld",
                        "import boa.util.annotations.TestInput;",
                        "@TestInput",
                        "class HelloWorld {}"
                ))
                .processedWith(proc)
                .compilesWithoutError();

        assertEquals(1, proc.getASTRoots().size());
    }


}
