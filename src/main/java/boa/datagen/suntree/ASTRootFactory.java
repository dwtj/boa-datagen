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
import boa.types.Ast.ASTRoot.Builder;
import com.sun.source.tree.*;

/**
 * Makes and populates an {@link ASTRoot.Builder} from a given {@link CompilationUnitTree}.
 *
 * @author dwtj
 */
public class ASTRootFactory extends MessageFactory<Builder> {

    protected ASTRootFactory() {
        // Nothing needed here.
    }

    protected static final ASTRootFactory _instance = new ASTRootFactory();

    public static ASTRootFactory instance() {
        return _instance;
    }

    public static Builder make(CompilationUnitTree tree) {
        Builder builder = ASTRoot.newBuilder();
        tree.accept(instance(), builder);
        return builder;
    }

    @Override
    public Void visitCompilationUnit(CompilationUnitTree tree, Builder builder) {
        for (ImportTree subtree: tree.getImports()) {
            subtree.accept(this, builder);
        }
        builder.addNamespaces(NamespaceFactory.make(tree));
        return null;
    }

    @Override
    public Void visitImport(ImportTree tree, Builder builder) {
        // TODO: tree.isStatic()
        builder.addImports(qualifiedIdentifierTerminal(tree.getQualifiedIdentifier()));
        return null;
    }

    public static String qualifiedIdentifierTerminal(Tree tree) {
        switch (tree.getKind()) {
        case MEMBER_SELECT:
            // For some reason, this seems to be the kind used to represent the imports. I think that the idea comes
            // from the fact that every valid import has to have at least one `.` in the name.
            return tree.toString();
        default:
            throw new RuntimeException("Unreachable code.");
        }
    }
}
