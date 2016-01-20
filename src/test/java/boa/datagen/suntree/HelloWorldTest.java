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

import boa.types.Ast.ASTRoot;
import boa.types.Ast.Declaration;
import boa.types.Ast.Modifier;
import boa.types.Ast.Modifier.ModifierKind;
import boa.types.Ast.Namespace;
import boa.types.Ast.TypeKind;

import com.google.protobuf.TextFormat;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertEquals;

/**
 * @author dwtj
 */
public class HelloWorldTest {

    private final static JavaFileObject SOURCE = forSourceLines("HelloWorld",
            "package boa.datagen.suntree.test;",
            "",
            "import boa.util.annotations.TestInput;",
            "",
            "@TestInput",
            "class HelloWorld { }",
            ""
    );

    private final static ASTRoot FROM_TEXT = parseProtobufTextAsRootAST(String.join("\n",
            "imports: `boa.util.annotations.TestInput`",
            "namespaces {",
            "  name: `boa.datagen.suntree.test`",
            "  declarations {",
            "    name: `HelloWorld`",
            "    kind: CLASS",
            "    modifiers {",
            "      kind: ANNOTATION",
            "      annotation_name: `TestInput`",
            "    }",
            "  }",
            "}",
            ""
    ).replace('`', '"'));

    private final static ASTRoot FROM_BUILDER = ASTRoot.newBuilder()
            .addImports("boa.util.annotations.TestInput")
            .addNamespaces(Namespace.newBuilder()                      // Add nested namespace
                    .setName("boa.datagen.suntree.test")
                    .addDeclarations(Declaration.newBuilder()          // Add nested class decl
                            .setName("HelloWorld")
                            .setKind(TypeKind.CLASS)
                            .addModifiers(Modifier.newBuilder()        // Add annotation as modifier
                                    .setKind(ModifierKind.ANNOTATION)
                                    .setAnnotationName("TestInput"))
                    )
            ).build();

    @Test
    public void expectFromTextEqualsFromBuilder() {
        assertEquals(FROM_TEXT, FROM_BUILDER);
    }

    @Test
    public void expectFromBuilderEqualsFromAdapter() {
        ASTRoot fromAdapter = getRootASTAdaptedWithinProcessor(SOURCE);
        assertEquals(FROM_BUILDER, fromAdapter);
    }

    @Test
    public void expectFromAdapterEqualsFromText() {
        ASTRoot fromAdapter = getRootASTAdaptedWithinProcessor(SOURCE);
        assertEquals(fromAdapter, FROM_TEXT);
    }

    public static ASTRoot parseProtobufTextAsRootAST(String protobufText) {
        try {
            ASTRoot.Builder builder = ASTRoot.newBuilder();
            TextFormat.merge(protobufText, builder);
            return builder.build();
        } catch (TextFormat.ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ASTRoot getRootASTAdaptedWithinProcessor(JavaFileObject source) {
        TestProcessor proc = new TestProcessor();
        assert_().about(javaSource())
                .that(source)
                .processedWith(proc)
                .compilesWithoutError();

        assertEquals(1, proc.getASTRoots().size());
        return proc.getASTRoot(0);
    }
}
