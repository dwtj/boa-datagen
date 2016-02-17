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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

import static boa.datagen.suntree.TestProcessorRunner.processJavaSourceResourceFiles;
import static java.util.Arrays.asList;

/**
 * @author dwtj
 */
@RunWith(Parameterized.class)
public class JlsSrcTest {

    private final static String JLSSRC_DIR_PREFIX = "jlssrc/";

    private final String resourceFile;

    public JlsSrcTest(String resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Parameters(name = "{0}")
    public static Collection<String> knownTestResource() {
        List<String> resources = asList(
            "annotations/ArrayElement.java",
            "annotations/ElementHasDefault.java",
            "annotations/SimpleAnnotation.java",
            "annotations/ZeroElements.java",
            "annotations/OneElement.java",
            "annotations/TwoElements.java",
            "comments/ClassDocComment.java",
            "classes/body/declarations/Constructor.java",
            "classes/body/declarations/InstanceInitializer.java",
            "classes/body/declarations/StaticInitializer.java",
            "classes/body/members/AbstractMethod.java",
            "classes/body/members/InnerClass.java",
            "classes/body/members/InnerClasses.java",
            "classes/body/members/InnerClassWithTypeParam.java",
            "classes/body/members/InnerInterface.java",
            "classes/body/members/MethodThrows.java",
            "classes/header/modifiers/AbstractClass.java",
            "classes/header/modifiers/FinalClass.java",
            "classes/header/superclasses/Generic.java",
            "classes/header/superclasses/Simple.java",
            "classes/header/superinterfaces/Generic.java",
            "classes/header/superinterfaces/One.java",
            "classes/header/superinterfaces/TakesTypeArgumentFromClass.java",
            "classes/header/superinterfaces/Two.java",
            "classes/header/typeparams/OneBound.java",
            "classes/header/typeparams/OneParam.java",
            "classes/header/typeparams/TwoBounds.java",
            "classes/header/typeparams/TwoParams.java",
            "classes/SimpleClass.java",
            "classes/SimpleEnum.java",
            "expressions/LiteralExpressions.java",
            "expressions/ClassLiteralExpressions.java",
            "expressions/ThisExpressions.java",
            "expressions/ParenthesizedExpressions.java",
            "expressions/ClassInstanceCreationExpressions.java",  // TODO: support enclosing expression. More?
            "expressions/ArrayAccessExpressions.java",
            "expressions/MemberSelect.java",
            "expressions/NewArrayExpressions.java",  // TODO: Annotations on dims!
            "expressions/FieldAccessExpressions.java",
            "expressions/MethodReferenceExpressions.java",  // TODO: Add some method references!
            "expressions/UnaryOperators.java",
            "expressions/CastExpressions.java",  // TODO: Everything?
            "expressions/BinaryOperators.java",
            "expressions/ConditionalOperator.java",
            "expressions/AssignmentOperators.java",
            "expressions/LambdaExpressions.java",  // TODO: Everything!
            "expressions/invocations/Simple.java",
            "expressions/invocations/Arguments.java",     // TODO: More?
            "expressions/invocations/TypeArguments.java", // TODO: More?
            "expressions/invocations/SuperKeyword.java",  // TODO: More?
            "interfaces/members/DefaultMember.java",
            "interfaces/members/Member.java",
            "interfaces/SimpleAnnotation.java",
            "interfaces/SimpleInterface.java",
            "packages/ClassInDefaultPackage.java",
            "packages/TwoDeclarationsInCompilationUnit.java",
            "packages/WildcardImports.java",
            "statements/Blocks.java",
            "statements/LocalClassDeclarations.java",
            "statements/LocalVariableDeclarationStatements.java",
            "statements/TheEmptyStatement.java",
            "statements/LabeledStatements.java",
            "statements/ExpressionStatements.java",
            "statements/TheIfStatement.java",
            "statements/TheAssertStatement.java",
            "statements/TheSwitchStatement.java",
            "statements/TheWhileStatement.java",
            "statements/TheDoStatement.java",
            "statements/TheBasicForStatement.java",
            "statements/TheEnhancedForStatement.java",
            "statements/TheBreakStatement.java",
            "statements/TheContinueStatement.java",
            "statements/TheReturnStatement.java",
            "statements/TheThrowStatement.java",
            "statements/TheSynchronizedStatement.java",
            "statements/TheTryStatement.java",
            "statements/TryWithResources.java",
            "types/AnnotatedTypeParameter.java",  // TODO: More?
            "types/Arrays.java",
            "types/Bounds.java", // TODO: More?
            "types/Enums.java",  // TODO: More?
            "types/FullyQualifiedTypes.java",  // TODO: More?
            "types/Wildcards.java",  // TODO: More!
            "variables/receivers/InnerConstructorHasReceiverParameter.java",
            "variables/receivers/InstanceMethodHasReceiverParameter.java",
            "variables/FieldHasInitializer.java"
        );

        // Add prefix to each resource.
        for (int i = 0; i < resources.size(); i++) {
            resources.set(i, JLSSRC_DIR_PREFIX + resources.get(i));
        }

        return resources;
    }

    @Test
    public void processResourceFile() {
        processJavaSourceResourceFiles(resourceFile);
    }
}
