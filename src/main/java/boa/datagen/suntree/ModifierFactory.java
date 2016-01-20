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

import boa.types.Ast.Modifier;
import boa.types.Ast.Modifier.Visibility;
import boa.types.Ast.Modifier.Builder;
import boa.types.Ast.Modifier.ModifierKind;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes and populates a {@link Modifier.Builder} from a given {@link ModifiersTree}.
 *
 * @author dwtj
 */
public class ModifierFactory extends MessageFactory<Builder> {

    protected ModifierFactory() {
        // Nothing needed here.
    }

    public final static ModifierFactory _instance = new ModifierFactory();

    public static ModifierFactory instance() {
        return _instance;
    }

    public static List<Builder> makeAll(ModifiersTree tree) {
        List<Builder> builders = new ArrayList<Builder>();
        for (AnnotationTree subtree : tree.getAnnotations()) {
            builders.add(make(subtree));
        }
        for (javax.lang.model.element.Modifier modifier : tree.getFlags()) {
            builders.add(make(modifier));
        }
        return builders;
    }

    public static Builder make(AnnotationTree tree) {
        Builder builder = Modifier.newBuilder();
        tree.accept(instance(), builder);
        return builder;
    }

    public static Builder make(javax.lang.model.element.Modifier modifier) {
        Builder builder = Modifier.newBuilder();
        switch (modifier) {

            case PUBLIC:
                builder.setKind(ModifierKind.VISIBILITY).setVisibility(Visibility.PUBLIC);
                break;
            case PROTECTED:
                builder.setKind(ModifierKind.VISIBILITY).setVisibility(Visibility.PROTECTED);
                break;
            case PRIVATE:
                builder.setKind(ModifierKind.VISIBILITY).setVisibility(Visibility.PRIVATE);
                break;

            case ABSTRACT:
                builder.setKind(ModifierKind.ABSTRACT);
                break;
            case STATIC:
                builder.setKind(ModifierKind.STATIC);
                break;
            case FINAL:
                builder.setKind(ModifierKind.FINAL);
                break;
            case SYNCHRONIZED:
                builder.setKind(ModifierKind.SYNCHRONIZED);
                break;

            // Note: These fall through.
            case STRICTFP:
                builder.setOther("strictfp");
            case NATIVE:
                builder.setOther("native");
            case VOLATILE:
                builder.setOther("volatile");
            case TRANSIENT:
                builder.setOther("transient");
            case DEFAULT:
                builder.setOther("default");
            default:
                builder.setKind(ModifierKind.OTHER);
        }
        return builder;
    }

    @Override
    public Void visitAnnotation(AnnotationTree tree, Builder builder) {
        builder.setKind(ModifierKind.ANNOTATION);
        builder.setAnnotationName(annotationTypeTerminal(tree.getAnnotationType()));
        for (ExpressionTree subtree : tree.getArguments()) {
            subtree.accept(this, builder);
        }
        return null;
    }

    public Void visitExpression(ExpressionTree tree, Builder builder) {
        // TODO: Delegate to an `ExpressionFactory`?
        // Maybe not: we might first need custom behavior to get member names.
        throw new UnsupportedOperationException("TODO");
    }

    public String annotationTypeTerminal(Tree annotationType) {
        return annotationType.toString();
    }
}
