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

import boa.types.Ast.Expression;
import boa.types.Ast.Expression.Builder;
import boa.types.Ast.Expression.ExpressionKind;
import com.sun.source.tree.*;

import java.util.List;

/**
 * @author dwtj
 */
public class ExpressionFactory extends ExceptingTreeVisitor<Void, Builder> implements MessageFactory<Builder> {

    private ExpressionFactory() {
        // Nothing needed here.
    }

    protected static ExpressionFactory _instance = new ExpressionFactory();

    public static ExpressionFactory instance() {
        return _instance;
    }

    /**
     * Make a {@link Expression.Builder} from the given expression.
     */
    public static Builder make(ExpressionTree expression) {
        Builder builder = Expression.newBuilder();
        expression.accept(instance(), builder);
        return builder;
    }

    /**
     * Unbox the given expression statement and use the nested expression to make a {@link Builder}.
     */
    public static Builder make(ExpressionStatementTree exprStatement) {
        return make(exprStatement.getExpression());
    }

    /**
     * Make a {@link Expression.Builder} from the given variable declaration. (Note that in the Boa
     * AST representation, all local variable declarations are stored as expressions.)
     */
    public static Builder make(VariableTree variable) {
        Builder builder = Expression.newBuilder();
        variable.accept(instance(), builder);
        return builder;
    }

    @Override
    public Void visitAnnotatedType(AnnotatedTypeTree annotatedType, Builder builder) {
        // TODO: Everything!
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Void visitAnnotation(AnnotationTree annotation, Builder builder) {
        // TODO: Everything!
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * The Boa Data AST represents an array access by adding the array expression (i.e. the LHS
     * expression which needs to evaluate to an array) as the 0th element of the `expressions` field
     * and by then adding the indexing expression (which needs to evaluate to an integer) as the 1th
     * element of the `expressions` field.
     */
    @Override
    public Void visitArrayAccess(ArrayAccessTree arrayAccess, Builder builder) {
        assert arrayAccess.getExpression() != null: "Array access must have an array expression.";
        assert arrayAccess.getIndex() != null: "Array access must have an index expression.";
        builder.setKind(ExpressionKind.ARRAYINDEX);
        builder.addExpressions(make(arrayAccess.getExpression()));
        builder.addExpressions(make(arrayAccess.getIndex()));
        return null;
    }

    @Override
    public Void visitAssignment(AssignmentTree assignment, Builder builder) {
        builder.setKind(ExpressionKind.ASSIGN);
        builder.addExpressions(ExpressionFactory.make(assignment.getVariable()));
        builder.addExpressions(ExpressionFactory.make(assignment.getExpression()));
        return null;
    }

    /**
     * Handles all binary expressions.
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17">
     *        15.17. Multiplicative Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">
     *        15.18. Additive Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">
     *        15.19. Shift Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.20">
     *        15.20. Relational Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.21">
     *        15.21. Equality Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22">
     *        15.22. Bitwise and Logical Operators
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">
     *        15.23. Conditional-And Operator <code>&&</code>
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">
     *        15.24. Conditional-Or Operator <code>||</code>
     *      </a>
     */
    @Override
    public Void visitBinary(BinaryTree binary, Builder builder) {

        ExpressionKind kind = null;
        switch (binary.getKind()) {

            // JSL 8: Section 15.17. Multiplicative Operators
            case DIVIDE:
                kind = ExpressionKind.OP_DIV;
                break;
            case MULTIPLY:
                kind = ExpressionKind.OP_MULT;
                break;
            case REMAINDER:
                kind = ExpressionKind.OP_MOD;
                break;

            // JSL 8: Section 15.18. Additive Operators
            case PLUS:
                kind = ExpressionKind.OP_ADD;
                break;
            case MINUS:
                kind = ExpressionKind.OP_SUB;
                break;

            // JSL 8: Section 15.19. Shift Operators
            case LEFT_SHIFT:
                kind = ExpressionKind.BIT_LSHIFT;
                break;
            case RIGHT_SHIFT:
                kind = ExpressionKind.BIT_RSHIFT;
                break;
            case UNSIGNED_RIGHT_SHIFT:
                kind = ExpressionKind.BIT_UNSIGNEDRSHIFT;
                break;

            // JSL 8: Section 15.20. Relational Operators (though `instanceof` is not included)
            case LESS_THAN:
                kind = ExpressionKind.LT;
                break;
            case LESS_THAN_EQUAL:
                kind = ExpressionKind.LTEQ;
                break;
            case GREATER_THAN:
                kind = ExpressionKind.GT;
                break;
            case GREATER_THAN_EQUAL:
                kind = ExpressionKind.GTEQ;
                break;

            // 15.21. Equality Operators
            case EQUAL_TO:
                kind = ExpressionKind.EQ;
                break;
            case NOT_EQUAL_TO:
                kind = ExpressionKind.NEQ;
                break;

            // JLS 8: Section 15.22.1. Integer Bitwise Operators
            // JLS 8: Section 15.22.2. Boolean Logical Operators
            case AND:
                kind = ExpressionKind.BIT_AND;
                break;
            case OR:
                kind = ExpressionKind.BIT_OR;
                break;
            case XOR:
                kind = ExpressionKind.BIT_XOR;
                break;

            // JLS 8: Section 15.23. Conditional-And Operator
            case CONDITIONAL_AND:
                kind = ExpressionKind.LOGICAL_AND;
                break;

            // JLS 8: Section 15.24. Conditional-Or Operator
            case CONDITIONAL_OR:
                kind = ExpressionKind.LOGICAL_OR;
                break;

            default:
                throw new IllegalArgumentException("Unknown binary tree kind: " + binary.getKind());
        }

        builder.setKind(kind);
        builder.addExpressions(ExpressionFactory.make(binary.getLeftOperand()));
        builder.addExpressions(ExpressionFactory.make(binary.getRightOperand()));
        return null;
    }

    @Override
    public Void visitCompoundAssignment(CompoundAssignmentTree assignment, Builder builder) {
        ExpressionKind kind = null;
        switch (assignment.getKind()) {

            case AND_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_BITAND;
                break;
            case XOR_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_BITXOR;
                break;
            case OR_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_BITOR;
                break;

            case PLUS_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_ADD;
                break;
            case MINUS_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_SUB;
                break;

            case MULTIPLY_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_MULT;
                break;
            case DIVIDE_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_DIV;
                break;
            case REMAINDER_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_MOD;
                break;

            case LEFT_SHIFT_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_LSHIFT;
                break;
            case RIGHT_SHIFT_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_RSHIFT;
                break;
            case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
                kind = ExpressionKind.ASSIGN_UNSIGNEDRSHIFT;
                break;

            default:
                String msg = "Unknown kind of compound assignment: " + assignment.getKind();
                throw new IllegalArgumentException(msg);
        }
        builder.setKind(kind);
        builder.addExpressions(ExpressionFactory.make(assignment.getVariable()));
        builder.addExpressions(ExpressionFactory.make(assignment.getExpression()));
        return null;
    }

    @Override
    public Void visitConditionalExpression(ConditionalExpressionTree conditional, Builder builder) {
        builder.setKind(ExpressionKind.CONDITIONAL);
        builder.addExpressions(ExpressionFactory.make(conditional.getCondition()));
        builder.addExpressions(ExpressionFactory.make(conditional.getTrueExpression()));
        builder.addExpressions(ExpressionFactory.make(conditional.getFalseExpression()));
        return null;
    }

    @Override
    public Void visitErroneous(ErroneousTree erroneous, Builder builder) {
        // TODO: Everything!
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Void visitIdentifier(IdentifierTree id, Builder builder) {
        builder.setKind(ExpressionKind.VARACCESS);
        builder.setVariable(id.getName().toString());
        return null;
    }

    @Override
    public Void visitInstanceOf(InstanceOfTree instanceOf, Builder builder) {
        builder.setKind(ExpressionKind.TYPECOMPARE);
        builder.addExpressions(ExpressionFactory.make(instanceOf.getExpression()));
        builder.setType(TypeFactory.make(instanceOf.getType()));
        return null;
    }

    @Override
    public Void visitLambdaExpression(LambdaExpressionTree lambda, Builder builder) {
        // TODO: Everything!
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Void visitLiteral(LiteralTree literal, Builder builder) {
        builder.setKind(ExpressionKind.LITERAL);
        Object value = literal.getValue();
        String valueStr = (value != null) ? value.toString() : "null";
        builder.setLiteral(valueStr);
        return null;
    }

    @Override
    public Void visitMemberReference(MemberReferenceTree memberReference, Builder builder) {
        // TODO: Everything!
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Void visitMemberSelect(MemberSelectTree memberSelect, Builder builder) {
        builder.setKind(ExpressionKind.MEMBERSELECT);
        builder.setVariable(memberSelect.getIdentifier().toString());
        builder.addExpressions(ExpressionFactory.make(memberSelect.getExpression()));
        return null;
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree invocation, Builder builder) {
        builder.setKind(ExpressionKind.METHODCALL);
        builder.addExpressions(ExpressionFactory.make(invocation.getMethodSelect()));
        invocation.getTypeArguments().forEach(a -> builder.addGenericParameters(TypeFactory.make(a)));
        invocation.getArguments().forEach(a -> builder.addExpressions(ExpressionFactory.make(a)));
        return null;
    }

    /**
     * Populates the given {@link Builder} using information extracted from the given {@link
     * NewArrayTree}. Note that the {@link NewArrayTree} may represent either an array declaration's
     * initializer or an array creation expression (maybe with an initializer).
     *
     * A new array expression either (1) has an array initializer which specifies initial data and
     * (implicitly) dimension lengths or (2) has explicit dimension lengths in dimension expressions
     * (inside the brackets). These two cases are mutually exclusive.
     *
     * Note that in the case of (1), the array initializer may be empty (i.e. of the form `{}`).
     * Note also that case (1) has two syntactic variants: (1a) has an explicit `new`, type, and
     * (empty) dimension brackets followed by an initializer; (1b) is just an initializer.
     *
     * In the case of dimension expressions, one or more of the leading (left-most) dimensions
     * brackets have a dimension expression. Some trailing (right-most) dimensions need not have
     * dimension expressions.
     *
     * A Boa Data AST represents case (2) whenever one or more expressions are added. (In this case,
     * initializer fields are illegal.) Otherwise, a Boa Data AST represents case (1), and the
     * Expression message contain zero or more `expressions`.
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.10.1">
     *          Array Creation Expressions
     *      </a>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html#jls-10.6">
     *          Array Initializers
     *      </a>
     */
    @Override
    public Void visitNewArray(NewArrayTree newArray, Builder builder) {

        // Check that returned lists are not null.
        assert newArray.getDimensions() != null;
        assert newArray.getAnnotations() != null;
        assert newArray.getDimAnnotations() != null;
        for (List<? extends AnnotationTree> annotations : newArray.getDimAnnotations()) {
            assert annotations != null;
        }

        builder.setKind(ExpressionKind.NEWARRAY);
        List<? extends ExpressionTree> initializers = newArray.getInitializers();
        if (initializers != null) {
            // Case 1: initializer; no dimension expressions, maybe explicit type.
            assert newArray.getDimensions().isEmpty():
                    "A new array expression with initializer must not have dimension expressions.";

            if (newArray.getType() != null) {
                builder.setType(TypeFactory.make(newArray.getType()));
            }
            // If source has an empty initializer, `{}`, then the initializer list will have size 0.
            for (ExpressionTree initializer : newArray.getInitializers()) {
                builder.addInitializers(make(initializer));
            }
        } else {
            // Case 2: no initializer; dimension expressions; explicit type.
            assert newArray.getDimensions().size() >= 1:
                    "A new array expr without an initializer must have at least one dim expr.";
            assert newArray.getType() != null: "New array expr from dim expr must have type.";

            builder.setType(TypeFactory.make(newArray.getType()));
            for (ExpressionTree dimExpr : newArray.getDimensions()) {
                builder.addExpressions(ExpressionFactory.make(dimExpr));
            }
        }

        for (AnnotationTree annotation : newArray.getAnnotations()) {
            builder.addAnnotation(ModifierFactory.make(annotation));
        }

        for (List<? extends AnnotationTree> annotations : newArray.getDimAnnotations()) {
            for (AnnotationTree annotation : annotations) {
                throw new UnsupportedOperationException("TODO: `NewArrayTree.getDimAnnotations()`");
            }
        }

        return null;
    }

    /**
     * Populates given {@link Expression.Builder} with information collected from
     */
    @Override
    public Void visitNewClass(NewClassTree tree, Builder builder) {
        builder.setKind(ExpressionKind.NEW);
        builder.setType(TypeFactory.make(tree.getIdentifier()));
        for (ExpressionTree arg : tree.getArguments()) {
            builder.addExpressions(ExpressionFactory.make(arg));
        }
        if (tree.getEnclosingExpression() != null) {
            // TODO: Explicit enclosing instance.
            throw new UnsupportedOperationException("TODO: NewClassTree.getEnclosingExpression()");
        }
        if (tree.getClassBody() != null) {
            builder.setAnonDeclaration(DeclarationFactory.make(tree.getClassBody()));
        }
        for (Tree typeArg : tree.getTypeArguments()) {
            // TODO: Type arguments to constructor.
            throw new UnsupportedOperationException("TODO: NewClassTree.getTypeArguments()");
        }
        return null;
    }

    @Override
    public Void visitParenthesized(ParenthesizedTree tree, Builder builder) {
        builder.setKind(ExpressionKind.PARENTHESIZED);
        builder.addExpressions(ExpressionFactory.make(tree.getExpression()));
        return null;
    }

    @Override
    public Void visitTypeCast(TypeCastTree typeCast, Builder builder) {
        builder.setKind(ExpressionKind.CAST);
        builder.setType(TypeFactory.make(typeCast.getType()));
        builder.addExpressions(ExpressionFactory.make(typeCast.getExpression()));
        return null;
    }

    @Override
    public Void visitUnary(UnaryTree tree, Builder builder) {
        ExpressionKind kind = null;
        switch (tree.getKind()) {
            case BITWISE_COMPLEMENT:
                kind = ExpressionKind.BIT_NOT;
                break;
            case LOGICAL_COMPLEMENT:
                kind = ExpressionKind.LOGICAL_NOT;
                break;
            case POSTFIX_DECREMENT:
                kind = ExpressionKind.OP_DEC;
                builder.setIsPostfix(true);
                break;
            case POSTFIX_INCREMENT:
                kind = ExpressionKind.OP_INC;
                builder.setIsPostfix(true);
                break;
            case PREFIX_DECREMENT:
                kind = ExpressionKind.OP_DEC;
                builder.setIsPostfix(false);
                break;
            case PREFIX_INCREMENT:
                kind = ExpressionKind.OP_INC;
                builder.setIsPostfix(false);
                break;
            case UNARY_MINUS:
                kind = ExpressionKind.UNARY_MINUS;
                break;
            case UNARY_PLUS:
                kind = ExpressionKind.UNARY_PLUS;
                break;
            default:
                throw new IllegalArgumentException("Unknown unary tree kind: " + tree.getKind());
        }
        builder.setKind(kind);
        builder.addExpressions(ExpressionFactory.make(tree.getExpression()));
        return null;
    }

    /**
     * This is visited as part of a class literal expression when the left-hand-side of the dot
     * is a primitive type (e.g. `int` and `void`).
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.8.2">
     *        15.8.2. Class Literals
     *      </a>
     */
    @Override
    public Void visitPrimitiveType(PrimitiveTypeTree tree, Builder builder) {
        builder.setKind(ExpressionKind.LITERAL);
        builder.setType(TypeFactory.make(tree));
        return null;
    }

    private static String methodSelectExpressionTerminal(ExpressionTree tree) {
        Tree.Kind kind = tree.getKind();
        if (kind == Tree.Kind.IDENTIFIER) {
            return ((IdentifierTree) tree).getName().toString();
        } else {
            throw new RuntimeException("Method select expression has unknown tree kind: " + kind);
        }
    }
}
