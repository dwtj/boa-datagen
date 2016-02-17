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

import boa.types.Ast.Statement;
import boa.types.Ast.Statement.Builder;
import boa.types.Ast.Statement.StatementKind;
import boa.util.annotations.NoMutableState;
import boa.util.annotations.NoStaticMutableState;
import boa.util.annotations.Singleton;
import com.sun.source.tree.*;

import java.util.List;
import java.util.ArrayList;

/**
 * The {@code make()} method is the class's sole entry point for clients. The class's public {@code
 * visit()} methods should <em>not</em> to be used by clients.
 *
 * @author dwtj
 */
@NoMutableState
@NoStaticMutableState
@Singleton
public class StatementFactory extends ExceptingTreeVisitor<Void, Builder> implements MessageFactory<Builder> {

    private StatementFactory() {
        // Nothing needed here.
    }

    protected static StatementFactory _instance = new StatementFactory();

    public static StatementFactory instance() {
        return _instance;
    }

    public static Builder make(StatementTree statement) {
        Builder builder = Statement.newBuilder();
        statement.accept(instance(), builder);
        return builder;
    }

    public static List<Builder> makeAll(Iterable<? extends StatementTree> statements) {
        List<Builder> builders = new ArrayList<>();
        for (StatementTree statement : statements) {
            builders.add(make(statement));
        }
        return builders;
    }

    /*
     * Note that in JSL 8, a block is not necessarily a statement. For example, static and instance
     * initializer blocks are not statements. However, some statements are indeed block statements
     * (e.g. blocks within a method).
     */
    @Override
    public Void visitBlock(BlockTree block, Builder builder) {
        builder.setKind(StatementKind.BLOCK);
        assert block.getStatements() != null: "`BlockTree.getStatements()` shouldn't return null.";
        for (StatementTree statement : block.getStatements()) {
            builder.addStatements(make(statement));
        }
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementTree expr, Builder builder) {
        builder.setKind(StatementKind.EXPRESSION);
        builder.setExpression(ExpressionFactory.make(expr.getExpression()));
        return null;
    }

    /**
     * Use the `expression` field to save the label ID and add the sub-statement as the first (and
     * only) item in the `statements` field.
     */
    @Override
    public Void visitLabeledStatement(LabeledStatementTree tree, Builder builder) {
        builder.setKind(StatementKind.LABEL);
        builder.setLabel(tree.getLabel().toString());
        return null;
    }

    /**
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.3">
     *        14.3. Local Class Declarations
     *      </a>
     */
    @Override
    public Void visitClass(ClassTree clazz, Builder builder) {
        builder.setKind(StatementKind.TYPEDECL);
        builder.setTypeDeclaration(DeclarationFactory.make(clazz));
        return null;
    }

    @Override
    public Void visitEmptyStatement(EmptyStatementTree empty, Builder builder) {
        builder.setKind(StatementKind.EMPTY);
        return null;
    }

    /**
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.4">
     *        14.4. Local Variable Declaration Statements
     */
    @Override
    public Void visitVariable(VariableTree variable, Builder builder) {
        builder.setKind(StatementKind.VARDECL);
        builder.setVariableDeclaration(VariableFactory.make(variable));
        return null;
    }

    @Override
    public Void visitAssert(AssertTree assertTree, Builder builder) {
        builder.setKind(StatementKind.ASSERT);
        builder.setCondition(ExpressionFactory.make(assertTree.getCondition()));
        if (assertTree.getDetail() != null) {
            builder.setExpression(ExpressionFactory.make(assertTree.getDetail()));
        }
        return null;
    }

    @Override
    public Void visitReturn(ReturnTree returnTree, Builder builder) {
        builder.setKind(StatementKind.RETURN);
        if (returnTree.getExpression() != null) {
            builder.setExpression(ExpressionFactory.make(returnTree.getExpression()));
        }
        return null;
    }

    @Override
    public Void visitWhileLoop(WhileLoopTree whileLoop, Builder builder) {
        builder.setKind(StatementKind.WHILE);
        builder.setCondition(ExpressionFactory.make(whileLoop.getCondition()));
        builder.addStatements(StatementFactory.make(whileLoop.getStatement()));
        return null;
    }

    @Override
    public Void visitEnhancedForLoop(EnhancedForLoopTree forLoop, Builder builder) {
        assert forLoop.getVariable() != null;
        assert forLoop.getExpression() != null;
        assert forLoop.getStatement() != null;
        builder.setKind(StatementKind.ENHANCEDFOR)
            .setVariableDeclaration(VariableFactory.make(forLoop.getVariable()))
            .setExpression(ExpressionFactory.make(forLoop.getExpression()))
            .addStatements(make(forLoop.getStatement()));
        return null;
    }

    @Override
    public Void visitIf(IfTree ifTree, Builder builder) {
        builder.setKind(StatementKind.IF);
        builder.setCondition(ExpressionFactory.make(ifTree.getCondition()));
        builder.addStatements(StatementFactory.make(ifTree.getThenStatement()));
        if (ifTree.getElseStatement() != null) {
            builder.addStatements(StatementFactory.make(ifTree.getElseStatement()));
        }
        return null;
    }

    @Override
    public Void visitSwitch(SwitchTree switchTree, Builder builder) {
        builder.setKind(StatementKind.SWITCH);
        builder.setExpression(ExpressionFactory.make(switchTree.getExpression()));
        for (CaseTree caseTree : switchTree.getCases()) {
            builder.addStatements(makeCaseClause(caseTree));
        }
        return null;
    }


    @Override
    public Void visitBreak(BreakTree breakTree, Builder builder) {
        builder.setKind(StatementKind.BREAK);
        if (breakTree.getLabel() != null) {
            builder.setLabel(breakTree.getLabel().toString());
        }
        return null;
    }

    @Override
    public Void visitDoWhileLoop(DoWhileLoopTree loopTree, Builder builder) {
        assert loopTree.getCondition() != null: "A do-while loop must have a condition.";
        assert loopTree.getStatement() != null: "A do-while loop must have a statement.";
        builder.setKind(StatementKind.DO);
        builder.setCondition(ExpressionFactory.make(loopTree.getCondition()));
        builder.addStatements(StatementFactory.make(loopTree.getStatement()));
        return null;
    }

    /**
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14">
     *        14.14. The <code>for</code> Statement
     *      </a>
     */
    @Override
    public Void visitForLoop(ForLoopTree loopTree, Builder builder) {
        assert loopTree.getStatement() != null: "A for loop must have a statement.";
        builder.setKind(StatementKind.FOR);

        /*
         * Add any the initialization statements.
         *
         * JSL 8 specifies that there are three cases for the initializer: (1) no initializer, (2) a
         * variable declarations (maybe with multiple comma-separated variable declarators), and (3)
         * a sequence of expression statements. (See the grammar in JLS 8 Section 14.14.1.)
         *
         * The suntree API's representation of a for loop's initializer is returned via
         * `ForLoopTree.getInitializer()`, and this return value represents the above three cases
         * as: (1) an empty list, (2) a sequence of variable declaration statements, or (3) a
         * sequence of expression statements (i.e. statements which box an expression).
         */
        if (loopTree.getInitializer().size() == 0) {
            // Case 1: Never call `builder.addInitializations()`.
            // Nothing needed here.
        } else {
            // Decide between cases (1) and (2) according to the type of the first element.
            StatementTree firstInitialization = loopTree.getInitializer().get(0);
            if (firstInitialization instanceof VariableTree) {
                // Case 2: Add each of the local variable declarations as initializations.
                for (StatementTree varDecl : loopTree.getInitializer()) {
                    assert (varDecl instanceof VariableTree) :
                            "Each initialization statement must be a variable declaration.";
                    builder.addInitializations(StatementFactory.make(varDecl));
                }
            } else if (firstInitialization instanceof ExpressionStatementTree) {
                // Case 3: Add each of the statement expressions as initializations.
                for (StatementTree exprStatement : loopTree.getInitializer()) {
                    assert (exprStatement instanceof ExpressionStatementTree):
                            "Each initialization statement must be an expression statement.";
                    builder.addInitializations(StatementFactory.make(exprStatement));
                }
            }
        }

        // Set the termination condition (if any).
        if (loopTree.getCondition() != null) {
            builder.setCondition(ExpressionFactory.make(loopTree.getCondition()));
        }

        // Add any update statements.
        for (ExpressionStatementTree exprStatement : loopTree.getUpdate()) {
            builder.addUpdates(ExpressionFactory.make(exprStatement));
        }

        // Add the for loop's body.
        builder.addStatements(StatementFactory.make(loopTree.getStatement()));

        return null;
    }

    @Override
    public Void visitContinue(ContinueTree continueTree, Builder builder) {
        builder.setKind(StatementKind.CONTINUE);
        if (continueTree.getLabel() != null) {
            builder.setLabel(continueTree.getLabel().toString());
        }
        return null;
    }


    @Override
    public Void visitThrow(ThrowTree throwTree, Builder builder) {
        assert throwTree.getExpression() != null: "A throw statement needs expression to throw.";
        builder.setKind(StatementKind.TRY);
        builder.setExpression(ExpressionFactory.make(throwTree.getExpression()));
        return null;
    }

    @Override
    public Void visitSynchronized(SynchronizedTree syncTree, Builder builder) {
        assert syncTree.getExpression() != null: "synchronized statement needs a lock expression.";
        assert syncTree.getBlock() != null: "synchronized statement needs a block to execute.";
        assert syncTree.getBlock().isStatic() == false: "synchronized block cannot be static.";
        builder.setKind(StatementKind.SYNCHRONIZED);
        builder.setExpression(ExpressionFactory.make(syncTree.getExpression()));
        builder.addStatements(StatementFactory.make(syncTree.getBlock()));
        return null;
    }

    @Override
    public Void visitTry(TryTree tryTree, Builder builder) {
        assert tryTree.getBlock() != null: "try statement needs a block to execute.";
        builder.setKind(StatementKind.TRY);
        builder.addStatements(StatementFactory.make(tryTree.getBlock()));

        for (Tree tree : tryTree.getResources()) {
            throw new UnsupportedOperationException("TODO: `TryTree.getResources()`");
        }
        for (CatchTree catchTree : tryTree.getCatches()) {
            // Note: The Boa Data AST (somewhat questionably) represents a catch clause as a
            // `Statement` added to the `statements` field.
            builder.addStatements(makeCatchClause(catchTree));
        }
        if (tryTree.getFinallyBlock() != null) {
            builder.addStatements(makeFinallyClause(tryTree.getFinallyBlock()));
        }
        return null;
    }

    /**
     * Populates and returns a newly created {@link Builder} from the given {@link CaseTree}, a
     * representation of a case clause within a switch statement.
     *
     * The case may either have an expression to match against or it may be a `default` case. The
     * sequence of statements under the case (if any) will be added one by one to the returned
     * value's `statements` field.
     *
     * Note that this functionality is here because the Boa Data AST represents each of a switch
     * statement's case clauses as a statement.
     */
    private static Builder makeCaseClause(CaseTree caseTree) {
        assert caseTree.getStatements() != null: "A case needs a (maybe empty) list of statements.";
        Builder builder = Statement.newBuilder().setKind(StatementKind.CASE);
        if (caseTree.getExpression() != null) {
            builder.setExpression(ExpressionFactory.make(caseTree.getExpression()));
        } else {
            // Given `caseTree` represents `default` case; `builder.setExpression()` is not called.
        }
        makeAll(caseTree.getStatements()).forEach(builder::addStatements);
        return builder;
    }

    /**
     * Populates and returns a newly created {@link Builder} from the given {@link CatchTree}, a
     * representation of a catch statement within a try statement.
     *
     * Note that this functionality is here because the Boa Data AST represents each of a try
     * statement's catch clauses as a statement.
     */
    private static Builder makeCatchClause(CatchTree catchTree) {
        assert catchTree.getParameter() != null: "catch clause needs a formal parameter.";
        assert catchTree.getBlock() != null: "catch clause needs a block to execute.";
        assert catchTree.getBlock().isStatic() == false: "catch clause block can't be static.";
        Builder builder = Statement.newBuilder().setKind(StatementKind.CATCH);
        builder.setVariableDeclaration(VariableFactory.make(catchTree.getParameter()));
        makeAll(catchTree.getBlock().getStatements()).forEach(builder::addStatements);
        return builder;
    }

    /**
     * Populates and returns a newly created {@link Builder} from the given {@link BlockTree}, a
     * representation of a finally clause within a try statement.
     *
     * Note that this functionality is here because the Boa Data AST represents a try statement's
     * finally clause as a statement.
     */
    private static Builder makeFinallyClause(BlockTree block) {
        assert block.isStatic() == false: "finally clause cannot have a static block";
        Builder builder = Statement.newBuilder().setKind(StatementKind.FINALLY);
        makeAll(block.getStatements()).forEach(builder::addStatements);
        return builder;
    }
}
