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

import boa.types.Ast;
import boa.types.Ast.Namespace;
import boa.types.Ast.Namespace.Builder;
import com.sun.source.tree.*;

/**
 * Makes and populates an {@link Ast.Namespace.Builder} from a {@link CompilationUnitTree}.
 *
 * @author dwtj
 */
public class NamespaceFactory extends MessageFactory<Builder> {

    protected NamespaceFactory() {
        // Nothing needed here.
    }

    protected static final NamespaceFactory _instance = new NamespaceFactory();

    public static NamespaceFactory instance() {
        return _instance;
    }

    public static Namespace.Builder make(CompilationUnitTree tree) {
        Namespace.Builder builder = Namespace.newBuilder();
        tree.accept(instance(), builder);
        return builder;
    }

    @Override
    public Void visitCompilationUnit(CompilationUnitTree tree, Namespace.Builder builder) {
        builder.setName(packageNameTerminal(tree.getPackageName()));
        for (AnnotationTree subtree : tree.getPackageAnnotations()) {
            subtree.accept(this, builder);
        }
        for (Tree subtree : tree.getTypeDecls()) {
            subtree.accept(this, builder);
        }
        return null;
    }

    @Override
    public Void visitClass(ClassTree tree, Namespace.Builder builder) {
        builder.addDeclarations(DeclarationFactory.make(tree));
        return null;
    }

    @Override
    public Void visitModifiers(ModifiersTree tree, Namespace.Builder builder) {

        return null;
    }

    @Override
    public Void visitAnnotation(AnnotationTree tree, Namespace.Builder builder) {
        // TODO: Delegate to a `ModifierFactory`.
        return null;
    }

    protected String packageNameTerminal(ExpressionTree packageName) {
        // When classes are in the default package, their package name is represented as null in the
        // part of the JCTree implementation which we are calling on. We, however, represent the
        // default package with the empty string.
        return (packageName == null) ? "" : packageName.toString();
    }
}
