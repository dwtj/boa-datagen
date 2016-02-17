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
import boa.types.Ast.Variable;
import boa.types.Ast.Variable.Builder;
import boa.util.annotations.NoMutableState;
import boa.util.annotations.NoStaticMutableState;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

import javax.lang.model.type.TypeKind;

/**
 * @author dwtj
 */
@NoMutableState
@NoStaticMutableState
public class VariableFactory implements MessageFactory<Builder> {

    private VariableFactory() {
        // Nothing needed here.
    }

    /**
     * <p>Returns a newly-created {@link Builder} populated with information from the given {@link
     * VariableTree}.</p>
     *
     * <h3>A note on the suntree API's representation of variables</h3>
     *
     * <p>According to JSL 8, a single local variable declaration statement or a single field
     * declaration may have multiple declarators. However, the suntree API does not directly
     * represent multiple declarators within a single declaration. Instead, the suntree AST creation
     * process (used within <code>javac</code>) converts single declaration with multiple
     * declarators to a sequence of distinct variable declarations.</p>
     *
     * <p>This means that even though the original Java source code might have some local
     * declaration or field declaration of the form <code>int i, j;</code>, the suntree generated
     * from this will actually be an AST which would suggest that the original source code is of the
     * form <code>int i; int j;</code>.</p>
     *
     * <p>The suntree API doesn't even have any types to represent multiple declarators within a
     * single variable declaration node. There is just a {@link VariableTree}, and this only ever
     * holds a single declarator/identifier.</p>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3">
     *        8.3. Field Declarations
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.4">
     *        14.4. Local Variable Declaration Statements
     *      </a>
     */

    public static Builder make(VariableTree variable) {
        Builder builder = Variable.newBuilder();
        setName(variable, builder);
        setType(variable, builder);
        addAnyModifiers(variable, builder);
        setInitializerIfAny(variable, builder);
        return builder;
    }

    /**
     * Set the builder's name via either the {@code VariableTree.getName()} or {@code
     * VariableTree.getNameExpression()}.
     */
    private static void setName(VariableTree variable, Builder builder) {
        if (variable.getName() != null) {
            assert variable.getNameExpression() == null;
            String name = variable.getName().toString();
            builder.setName(name);
        } else if (variable.getNameExpression() != null) {
            // TODO: Use variable.getNameExpression()
            throw new UnsupportedOperationException("TODO: `VariableTree.getNameExpression()`");
        } else {
            throw new AssertionError("Unreachable code.");
        }
    }

    private static void setType(VariableTree variable, Builder builder) {
        try {
            builder.setVariableType(TypeFactory.make(variable.getType()));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);  // TODO: For now, just propagate-up any errors.
        }
    }

    private static void addAnyModifiers(VariableTree variable, Builder builder) {
        for (Ast.Modifier.Builder modifier : ModifierFactory.makeAll(variable.getModifiers())) {
            builder.addModifiers(modifier);
        }
    }

    private static void setInitializerIfAny(VariableTree variable, Builder builder) {
        if (variable.getInitializer() != null) {
            builder.setInitializer(ExpressionFactory.make(variable.getInitializer()));
        }
    }
}
