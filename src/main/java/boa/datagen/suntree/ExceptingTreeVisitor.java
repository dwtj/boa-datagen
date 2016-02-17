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

import boa.util.annotations.NoMutableState;
import boa.util.annotations.NoStaticMutableState;
import com.sun.source.tree.*;

/**
 * A class implementing {@link TreeVisitor} which throws a {@link TreeVisitorException} whenever any
 * of its visit methods are called. This is meant to be sub-classed by tree visitors which are only
 * designed to implement some subset of the interface, and (for safety) want all calls to non-
 * overridden visit methods to be reported loudly.
 *
 * @author dwtj
 */
@NoMutableState
@NoStaticMutableState
public class ExceptingTreeVisitor<R, P> implements TreeVisitor<R, P> {

    @Override
    public R visitAnnotatedType(AnnotatedTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitAnnotation(AnnotationTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitMethodInvocation(MethodInvocationTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitAssert(AssertTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitAssignment(AssignmentTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitCompoundAssignment(CompoundAssignmentTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitBinary(BinaryTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitBlock(BlockTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitBreak(BreakTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitCase(CaseTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitCatch(CatchTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitClass(ClassTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitConditionalExpression(ConditionalExpressionTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitContinue(ContinueTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitDoWhileLoop(DoWhileLoopTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitErroneous(ErroneousTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitExpressionStatement(ExpressionStatementTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitEnhancedForLoop(EnhancedForLoopTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitForLoop(ForLoopTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitIdentifier(IdentifierTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitIf(IfTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitImport(ImportTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitArrayAccess(ArrayAccessTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitLabeledStatement(LabeledStatementTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitLiteral(LiteralTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitMethod(MethodTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitModifiers(ModifiersTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitNewArray(NewArrayTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitNewClass(NewClassTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitLambdaExpression(LambdaExpressionTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitParenthesized(ParenthesizedTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitReturn(ReturnTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitMemberSelect(MemberSelectTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitMemberReference(MemberReferenceTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitEmptyStatement(EmptyStatementTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitSwitch(SwitchTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitSynchronized(SynchronizedTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitThrow(ThrowTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitCompilationUnit(CompilationUnitTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitTry(TryTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitParameterizedType(ParameterizedTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitUnionType(UnionTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitIntersectionType(IntersectionTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitArrayType(ArrayTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitTypeCast(TypeCastTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitPrimitiveType(PrimitiveTypeTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitTypeParameter(TypeParameterTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitInstanceOf(InstanceOfTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitUnary(UnaryTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitVariable(VariableTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitWhileLoop(WhileLoopTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitWildcard(WildcardTree tree, P p) {
        throw new TreeVisitorException();
    }

    @Override
    public R visitOther(Tree tree, P p) {
        throw new TreeVisitorException();
    }

    /**
     * Indicates that the tree visitor reached a type of {@link com.sun.source.tree.Tree tree node} which the tree
     * visitor was not designed to reach.
     */
    public static class TreeVisitorException extends RuntimeException {
        // Nothing needed here, because this exception just serves as a typed alias for `RuntimeException`.
    }
}
