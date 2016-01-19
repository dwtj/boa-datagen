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
import boa.types.Ast.Declaration;
import boa.types.Ast.Declaration.Builder;
import boa.types.Ast.Modifier;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ModifiersTree;

/**
 * Makes and populates an {@link Declaration.Builder} from a given {@link ClassTree}.
 *
 * @author dwtj
 */
public class DeclarationFactory extends MessageFactory<Builder> {

    protected DeclarationFactory() {
        // Nothing needed here.
    }

    public static final DeclarationFactory _instance = new DeclarationFactory();

    public static DeclarationFactory instance() {
        return _instance;
    }

    public static Builder make(ClassTree tree) {
        Builder builder = Declaration.newBuilder();
        tree.accept(instance(), builder);
        return builder;
    }

    @Override
    public Void visitClass(ClassTree tree, Builder builder) {
        builder.setName(tree.getSimpleName().toString());
        switch (tree.getKind()) {
            case ANNOTATION:
                builder.setKind(Ast.TypeKind.ANNOTATION);
                break;
            case CLASS:
                builder.setKind(Ast.TypeKind.CLASS);
                break;
            case ENUM:
                builder.setKind(Ast.TypeKind.ENUM);
                break;
            case INTERFACE:
                builder.setKind(Ast.TypeKind.INTERFACE);
                break;
            default:
                throw new RuntimeException("Unknown kind of `ClassTree`: " + tree.getKind());
        }
        if (tree.getModifiers() != null) {
            tree.getModifiers().accept(this, builder);
        }
        // TODO: tree.getTypeParameters()
        // TODO: tree.getExtendsClause()
        // TODO: tree.getImplementsClause()
        // TODO: tree.getMembers(), which includes methods, fields, and nested declarations.
        // TODO: Everything else!

        return null;
    }

    @Override
    public Void visitModifiers(ModifiersTree tree, Builder builder) {
        for (Modifier.Builder subBuilder : ModifierFactory.makeAll(tree)) {
            builder.addModifiers(subBuilder);
        }
        return null;
    }
}
