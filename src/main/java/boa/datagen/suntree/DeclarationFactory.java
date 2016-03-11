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

import boa.types.Ast.Declaration;
import boa.types.Ast.Declaration.DeclarationKind;
import boa.types.Ast.Declaration.Builder;
import boa.types.Ast.Modifier;

import com.sun.source.tree.*;

import javax.lang.model.element.Name;

/**
 * Makes and populates an {@link Declaration.Builder} from a given {@link ClassTree}.
 *
 * @author dwtj
 */
public class DeclarationFactory extends ExceptingTreeVisitor<Void, Builder> implements MessageFactory<Builder> {

    private DeclarationFactory() {
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

    /**
     * Visit the declaration. Note that this is used to visit not just class declarations, but also
     * interface, enum, and annotation declarations.
     *
     * @see ClassTree
     */
    @Override
    public Void visitClass(ClassTree tree, Builder builder) {

        // Name (required)
        builder.setName(classSimpleNameTerminal(tree.getSimpleName()));

        // Kind (required)
        switch (tree.getKind()) {
            case ANNOTATION_TYPE:
                builder.setKind(DeclarationKind.ANNOTATION);
                break;
            case CLASS:
                builder.setKind(DeclarationKind.CLASS);
                break;
            case ENUM:
                builder.setKind(DeclarationKind.ENUM);
                break;
            case INTERFACE:
                builder.setKind(DeclarationKind.INTERFACE);
                break;
            default:
                throw new RuntimeException("Unknown kind of `ClassTree`: " + tree.getKind());
        }

        // Modifiers (if any)
        Tree modifiers = tree.getModifiers();
        if (modifiers != null) {
            modifiers.accept(this, builder);
        }

        // Type Parameters (if any)
        for (TypeParameterTree typeParam : tree.getTypeParameters()) {
            builder.addGenericParameters(TypeFactory.make(typeParam));
        }

        // Extends Clause (if any)
        if (tree.getExtendsClause() != null) {
            try {
                builder.addParents(TypeFactory.make(tree.getExtendsClause()));
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);  // TODO: For now, propagate up unexpected cases.
            }
        }

        // Implemented Interfaces (if any)
        for (Tree implemented : tree.getImplementsClause()) {
            try {
                builder.addParents(TypeFactory.make(implemented));
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);  // TODO: For now, propagate up unexpected cases.
            }
        }

        // Members (if any). This includes methods, fields, and nested declarations.
        for (Tree member : tree.getMembers()) {
            member.accept(this, builder);
        }

        return null;
    }

    /**
     * Visit one of the declaration's fields (either static or instance).
     */
    @Override
    public Void visitVariable(VariableTree tree, Builder builder) {
        builder.addFields(VariableFactory.make(tree));
        return null;
    }

    /**
     * Visit one of the declaration's methods (either static or instance). This is also used to
     * handle constructors.
     */
    @Override
    public Void visitMethod(MethodTree tree, Builder builder) {
        builder.addMethods(MethodFactory.make(tree));
        return null;
    }

    /**
     * Visit the declaration's set of modifiers.
     */
    @Override
    public Void visitModifiers(ModifiersTree tree, Builder builder) {
        for (Modifier.Builder subBuilder : ModifierFactory.makeAll(tree)) {
            builder.addModifiers(subBuilder);
        }
        return null;
    }

    /**
     * Visit a declaration's instance initialization block or static initialization block.
     */
    @Override
    public Void visitBlock(BlockTree block, Builder builder) {
        if (block.isStatic()) {
            builder.addStaticInitBlocks(StatementFactory.make(block));
        } else {
            builder.addInitBlocks(StatementFactory.make(block));
        }
        return null;
    }

    private static String classSimpleNameTerminal(Name name) {
        return name.toString();
    }
}
