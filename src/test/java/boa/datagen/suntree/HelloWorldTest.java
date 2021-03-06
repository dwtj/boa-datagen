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
import boa.types.Ast.Namespace;
import boa.types.Ast.Declaration.DeclarationKind;

import boa.util.CompilationUnitsProcessor;
import com.google.protobuf.TextFormat;
import org.junit.Test;

import javax.tools.JavaFileObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static org.junit.Assert.assertEquals;

/**
 * @author dwtj
 */
public class HelloWorldTest {

    private final static JavaFileObject SOURCE = forSourceLines("HelloWorld",
            "package boa.datagen.suntree.test;",
            "",
            "class HelloWorld { }",
            ""
    );

    private final static ASTRoot FROM_TEXT = parseProtobufTextAsRootAST(String.join("\n",
            "namespaces {",
            "  name: `boa.datagen.suntree.test`",
            "  declarations {",
            "    name: `HelloWorld`",
            "    kind: CLASS",
            "    methods {",
            "      name: `<init>`",
            "      statements {",
            "        kind: EXPRESSION",
            "        expression {",
            "          kind: METHODCALL",
            "          expressions {",
            "            kind: VARACCESS",
            "            variable: `super`",
            "          }",
            "        }",
            "      }",
            "    }",
            "  }",
            "}",
            ""
    ).replace('`', '"'));

    // TODO: Add the implicit default constructor to this:
    private final static ASTRoot FROM_BUILDER = ASTRoot.newBuilder()
            .addNamespaces(Namespace.newBuilder()                      // Add nested namespace
                    .setName("boa.datagen.suntree.test")
                    .addDeclarations(Declaration.newBuilder()          // Add nested class decl
                            .setName("HelloWorld")
                            .setKind(DeclarationKind.CLASS)
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
        List<ASTRoot> roots = new ArrayList<>(1);
        SunTreeAdapter adapter = new SunTreeAdapter();
        CompilationUnitsProcessor.fromJavaFileObjects(cu -> roots.add(adapter.adapt(cu)), source);
        assertEquals(roots.size(), 1);
        return roots.get(0);
    }
}
