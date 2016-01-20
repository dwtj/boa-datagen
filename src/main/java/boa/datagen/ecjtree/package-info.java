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

/**
 * <p>This package implements those aspects of Boa data generation which are specific to the {@code
 * ecj} AST, {@link org.eclipse.jdt.core.dom}. This AST is notable for its use in {@code ecj} and
 * the Eclipse IDE for Java Developers.
 *
 * <p>This package implements {@link boa.datagen.ecjtree.ECJTreeAdapter}, which traverses a Java AST
 * represented in {@link boa.datagen.ecjtree.ECJTreeAdapter ecj AST} and to generate an equivalent
 * Java AST represented in {@link boa.types.Ast Boa's AST message types}.
 *
 * @author dwtj
 */
package boa.datagen.ecjtree;