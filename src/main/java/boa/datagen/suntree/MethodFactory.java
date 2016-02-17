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
import boa.types.Ast.Method;
import boa.types.Ast.Method.Builder;
import com.sun.source.tree.*;

import javax.lang.model.type.TypeKind;

/**
 * @author dwtj
 */
public class MethodFactory implements MessageFactory<Builder> {

    private MethodFactory() {
        // Nothing needed here.
    }

    public static final MethodFactory _instance = new MethodFactory();

    public static MethodFactory instance() {
        return _instance;
    }

    public static Builder make(MethodTree method) {
        Builder builder = Method.newBuilder();
        instance().visitMethod(method, builder);
        return builder;
    }

    private void visitMethod(MethodTree tree, Builder builder) {
        setName(tree, builder);
        setReturnTypeIfAny(tree, builder);
        addAnyModifiers(tree, builder);
        addAnyParameters(tree, builder);
        addAnyTypeParameters(tree, builder);
        setBodyIfAny(tree, builder);
        setAnnotationElementDefaultValueIfAny(tree, builder);
        addAnyThrows(tree, builder);
        setReceiverParameterIfAny(tree, builder);
    }

    private void setName(MethodTree method, Builder builder) {
        assert method.getName() != null: "`MethodTree.getName()` shouldn't return null.";
        builder.setName(method.getName().toString());
    }

    /**
     * If the given {@link MethodTree} has a return type, then an appropriate {@link Ast.Type} is
     * created using a {@link TypeFactory} and this type used to set the {@code return_type} field
     * of the given {@link Builder}. Note that nothing is set if the method is either
     *
     * <ul>
     *     <li>actually a constructor</li>
     *     <li>actually a void method</li>
     * </ul>
     */
    private void setReturnTypeIfAny(MethodTree method, Builder builder) {
        Tree retType = method.getReturnType();

        if (retType == null) {
            // No return type because this "method" is actually a constructor.
            String name = method.getName().toString();
            assert name.equals("<init>"): "Expected to find constructor, but found " + name;
            return;
        }

        if (hasVoidReturnType(method)) {
            return;
        }

        try {
            builder.setReturnType(TypeFactory.make(method.getReturnType()));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);  // TODO: For now, propagate up unexpected cases.
        }
    }

    private static boolean hasVoidReturnType(MethodTree method) {
        Tree retType = method.getReturnType();
        return retType != null
                && retType.getKind() == Tree.Kind.PRIMITIVE_TYPE
                && ((PrimitiveTypeTree) retType).getPrimitiveTypeKind() == TypeKind.VOID;
    }

    /**
     * Add all modifiers (including annotations) declared on the given {@link MethodTree} (if any)
     * to the given {@link Builder}.
     */
    private void addAnyModifiers(MethodTree method, Builder builder) {
        ModifiersTree modifiers = method.getModifiers();
        assert modifiers != null : "`MethodTree.getModifiers()` should always return non-null.";
        for (Ast.Modifier.Builder subBuilder : ModifierFactory.makeAll(modifiers)) {
            builder.addModifiers(subBuilder);
        }
    }

    /**
     * Add all formal parameters declared on the given {@link MethodTree} (if any) to the given
     * {@link Builder}.
     */
    private void addAnyParameters(MethodTree method, Builder builder) {
        for (VariableTree param : method.getParameters()) {
            builder.addArguments(VariableFactory.make(param));
        }
    }

    private void addAnyTypeParameters(MethodTree tree, Builder builder) {
        assert tree.getTypeParameters() != null: "`MethodTree.getTypeParameters()` shouldn't return null.";
        for (TypeParameterTree param : tree.getTypeParameters()) {
            builder.addGenericParameters(TypeFactory.make(param));
        }
    }

    private void setBodyIfAny(MethodTree method, Builder builder) {
        BlockTree block = method.getBody();
        if (block != null) {
            assert !block.isStatic() : "A method's body cannot be a static block.";
            for (StatementTree statement : block.getStatements()) {
                builder.addStatements(StatementFactory.make(statement));
            }
        }
    }

    private void setAnnotationElementDefaultValueIfAny(MethodTree method, Builder builder) {
        Tree defaultValue = method.getDefaultValue();
        if (defaultValue != null) {
            // TODO: Everything!
            throw new UnsupportedOperationException("TODO: Annotation element default values.");
        }
    }

    private void addAnyThrows(MethodTree method, Builder builder) {
        assert method.getThrows() != null: "`MethodTree.getThrows()` shouldn't return null.";
        for (ExpressionTree throwExpr : method.getThrows()) {
            // TODO: Everything!
            throw new UnsupportedOperationException("TODO: MethodFactory: methodTree.getThrows()");
        }
    }

    private void setReceiverParameterIfAny(MethodTree method, Builder builder) {
        if (method.getReceiverParameter() != null) {
            // TODO
            throw new UnsupportedOperationException("TODO: MethodFactory: methodTree.getReceiverParameter()");
        }
    }
}
