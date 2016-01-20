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

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
import static org.junit.Assert.assertEquals;

/**
 * @author dwtj
 */
public class FromResourcesTest {

    private final static String JAVA_TEST_SRC_RESOURCE_PREFIX = "java-test-src/";

    TestProcessor proc;

    @Before
    public void setUp() {
        proc = new TestProcessor();
    }

    @Test
    public void itWorks() {
        runProc("foo/Foo.java");
    }

    @Test
    public void itFailsWithoutResource() {
        try {
            runProc("NonExistentResource.java");
            String errMsg = "Expected that an error would be thrown when looking up the resource.";
            throw new RuntimeException(errMsg);
        } catch (IllegalArgumentException ex) {
            // It failed, as expected.
        }
    }

    @Test
    public void itFailsWithBadSource() {
         assert_().about(javaSources())
                .that(lookupJavaTestSrcResources("bad/BadSyntax.java"))
                .processedWith(proc)
                .failsToCompile();
    }

    @Test
    public void defaultPackage() {
        runProc("ClassInDefaultPackage.java");
    }

    @Test
    public void hasNestedClass() {
        runProc("declarations/nested/HasNestedClass.java");
    }

    @Test
    public void hasNestedInterface() {
        runProc("declarations/nested/HasNestedInterface.java");
    }

    private void runProc(String javaTestSrcFile) {
        assert_().about(javaSources())
                .that(lookupJavaTestSrcResources(javaTestSrcFile))
                .processedWith(proc)
                .compilesWithoutError();
        assertEquals(1, proc.getASTRoots().size());
    }

    private static Iterable<JavaFileObject> lookupJavaTestSrcResources(String... testSrcFile) {
        List<JavaFileObject> files = new ArrayList<>();
        for (String resource : testSrcFile) {
            files.add(forResource(JAVA_TEST_SRC_RESOURCE_PREFIX + resource));
        }
        return files;
    }
}
