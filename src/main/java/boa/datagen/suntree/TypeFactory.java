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

import boa.types.Ast.Type;
import boa.types.Ast.Type.TypeKind;
import boa.types.Ast.Type.Builder;
import com.sun.source.tree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dwtj
 */
public class TypeFactory implements MessageFactory<Builder> {

    private TypeFactory() {
        // Nothing needed here.
    }

    private static final TypeFactory _instance = new TypeFactory();

    public static TypeFactory instance() {
        return _instance;
    }

    /**
     * Makes a {@link boa.types.Ast.Type.Builder} which represents a (deep) copy of {@code tree}.
     *
     * @param tree An AST node from which the factory will try to copy information.
     * @return A fully-valid and ready-to-be-built Builder.
     * @throws IllegalArgumentException If {@code tree} has unexpected/unknown {@link Tree.Kind}.
     */
    public static Builder make(Tree tree) {
        Tree.Kind kind = tree.getKind();
        switch (kind) {
            case ARRAY_TYPE:
                return make((ArrayTypeTree) tree);
            case IDENTIFIER:
                return make((IdentifierTree) tree);
            case TYPE_PARAMETER:
                return make((TypeParameterTree) tree);
            case PARAMETERIZED_TYPE:
                return make((ParameterizedTypeTree) tree);
            case PRIMITIVE_TYPE:
                return make((PrimitiveTypeTree) tree);
            case UNION_TYPE:
                return make((UnionTypeTree) tree);
            case ANNOTATED_TYPE:
                return make((AnnotatedTypeTree) tree);
            case MEMBER_SELECT:
                return make((MemberSelectTree) tree);
            default:
                String errMsg = "Tried to make a type from an unexpected kind of tree: " + kind;
                throw new IllegalArgumentException(errMsg);
        }
    }

    public static Builder make(ArrayTypeTree arrType) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.ARRAY);
        builder.setComponent(TypeFactory.make(arrType.getType()));
        return builder;
    }

    public static Builder make(IdentifierTree id) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.REFERENCE);
        builder.setName(nameOf(id));
        return builder;
    }

    public static Builder make(TypeParameterTree typeParam) {
        assert typeParam.getName() != null: "A type parameter must be named.";
        assert typeParam.getAnnotations() != null: "Expects a (possibly empty) annotations list.";
        assert typeParam.getBounds() != null: "Expects a (possibly empty) bounds list.";
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.REFERENCE);
        builder.setName(nameOf(typeParam));
        typeParam.getAnnotations().forEach(a -> builder.addModifiers(ModifierFactory.make(a)));
        for (Tree bound : typeParam.getBounds()) {
            throw new UnsupportedOperationException("TODO: TypeParameterTree.getBounds()");
        }
        return builder;
    }

    public static Builder make(ParameterizedTypeTree parameterizedType) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.REFERENCE);
        builder.setName(nameOf(parameterizedType));
        for (Tree typeArg : parameterizedType.getTypeArguments()) {
            builder.addTypeArguments(make(typeArg));
        }
        return builder;
    }

    public static Builder make(PrimitiveTypeTree primitiveType) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.PRIMITIVE);
        builder.setName(nameOf(primitiveType));
        return builder;
    }

    public static Builder make(UnionTypeTree unionType) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.UNION);
        builder.setName(nameOf(unionType));
        return builder;
    }

    public static Builder make(AnnotatedTypeTree annotatedType) {
        Builder builder = make(annotatedType.getUnderlyingType());
        annotatedType.getAnnotations().forEach(a -> builder.addModifiers(ModifierFactory.make(a)));
        return builder;
    }

    /**
     * Makes a type from a qualified type.
     */
    public static Builder make(MemberSelectTree memberSelect) {
        Builder builder = Type.newBuilder();
        builder.setKind(TypeKind.REFERENCE);
        builder.setName(nameOf(memberSelect));
        return builder;
    }

    private static String nameOf(IdentifierTree id) {
        return id.getName().toString();
    }

    private static String nameOf(PrimitiveTypeTree primitiveType) {
        javax.lang.model.type.TypeKind kind = primitiveType.getPrimitiveTypeKind();
        switch (kind) {
            case BOOLEAN:
                return "boolean";
            case BYTE:
                return "byte";
            case SHORT:
                return "short";
            case INT:
                return "int";
            case LONG:
                return "long";
            case CHAR:
                return "char";
            case FLOAT:
                return "float";
            case DOUBLE:
                return "double";
            case VOID:
                return "";
            default:
                throw new IllegalArgumentException("Found unknown primitive type kind: " + kind);
        }
    }

    private static String nameOf(MemberSelectTree memberSelect) {
        ExpressionTree prefix = memberSelect.getExpression();
        String identifier = memberSelect.getIdentifier().toString();
        assert prefix != null;
        switch (prefix.getKind()) {
            case IDENTIFIER:
                return nameOf((IdentifierTree) prefix) + "." + identifier;
            case MEMBER_SELECT:
                return nameOf((MemberSelectTree) prefix) + "." + identifier;
            default:
                String errMsg = "Unexpected kind of member select prefix expr: " + prefix.getKind();
                throw new IllegalArgumentException(errMsg);
        }
    }

    private static String nameOf(UnionTypeTree unionType) {
        assert unionType.getTypeAlternatives() != null;
        int size = unionType.getTypeAlternatives().size();
        assert size > 0;
        List<String> names = new ArrayList<>(size);
        for (Tree alternative : unionType.getTypeAlternatives()) {
            Builder builder = make(alternative);
            names.add(builder.getName());
        }
        return String.join(" | ", names);
    }

    private static String nameOf(ParameterizedTypeTree parameterizedType) {
        return make(parameterizedType.getType()).getName();
    }

    private static String nameOf(TypeParameterTree typeParam) {
        return typeParam.getName().toString();
    }
}
