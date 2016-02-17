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
 * <p>This package implements those aspects of Boa data generation which are specific to {@link
 * com.sun.source.tree}. This AST is used in Sun/Oracle's {@code javac} and also the NetBeans IDE.
 * Though this API is not in either {@code java} or {@code javax}, it is actually a part of the
 * official Oracle JDK. (See {@code jdk.Exported}.)
 *
 * <p>This package implements {@link boa.datagen.suntree.SunTreeAdapter} and the various message
 * factories to which it delegates. The purpose of {@code SunTreeAdapter} is to traverse a given
 * Java AST represented using {@link com.sun.source.tree Sun's Tree API} and to construct an
 * equivalent Java AST represented using {@link boa.types.Ast Boa's AST message types}.
 *
 * <p>The design of the {@code SunTreeAdapter} is complicated by the fact that the types derived
 * from {@link boa.types.Ast} are rather different from {@link com.sun.source.tree}. While it would
 * be possible to have a single visitor perform the entire AST building process, we believe it would
 * involve lots of dynamic casting. This is partly related to the fact that the various message
 * types defined under {@link boa.types.Ast} do not share a single common type which would be
 * useful.
 *
 * <p>This package implements a number of different {@link boa.datagen.suntree.MessageFactory
 * message factories}, in fact, one for each of the message types defined under {@link
 * boa.types.Ast} (e.g. {@code ASTRoot}, {@code Declaration}, {@code Expression}, etc.). The idea is
 * that each of these factories is responsible for knowing how to build its own message type from
 * all <em>relevant</em> {@link com.sun.source.tree.Tree Tree} types.
 *
 * <p>Each factory is implemented as a visitor. (The two terms are sometimes used here
 * interchangeably, depending on emphasis.) Each factory has one or more {@code make()} methods
 * which serve as the main entry points to making one or more builders builders. The {@code make()}
 * methods often initialize one or more message builders and start visiting the given {@code Tree},
 * passing down a builder the {@code Tree} is visited and the builder is populated.
 *
 * <p>The visitor is responsible for implementing the <em>relevant</em> subset of the full visitor
 * interface. For many message types, this will just be a few methods.
 *
 * <p>A general overview of a factory's responsibilities and behavior:
 *
 * <ol>
 *   <li>
 *     A factory traverses the given {@code Tree} and add its information to this protobuf builder.
 *   </li>
 *   <li>
 *     For any {@code Tree} found which has direct access to a terminating field in the {@code Tree}
 *     (i.e. some integer or string), the factory unpacks the {@code Tree} as appropriate and stores
 *     the information in the builder.
 *   </li>
 *   <li>
 *     For any non-terminating fields in a message (i.e. its sub-messages), delegate to an
 *     appropriate message factory. The delegating factory passes down some subtree, and the
 *     delegated-to factory is responsible for returning a sub-message containing all information
 *     from the passed down subtree. The delegating factory can then save this sub-message as part
 *     of its own message.
 *   </li>
 * </ol>
 *
 * <p><em>In Summary:</em> "Within" a particular message factory, a single protobuf builder is
 * passed downward (via the second argument to accept) and only {@code null} is returned. On the
 * other hand, "between" delegating and delegated message factories, only a {@link
 * com.sun.source.tree.Tree} is passed downward and a fully realized builder is returned.
 *
 * <p><em>Note:</em> One rule of thumb that should be applied across the code is that for a given
 * message type {@code X}, {@code X.newBuilder()} should only be called within the {@code
 * boa.datagen.suntree.XFactory} class.
 *
 * <p><em>Note:</em> For simplicity, these factories are all kept as stateless as possible. In
 * general, their fields only refer to those sub-message factories to which they must delegate.
 *
 * @see boa.types.Ast
 * @see com.sun.source.tree
 * @see com.sun.tools.javac.tree.JCTree
 *
 * @author dwtj
 */
package boa.datagen.suntree;
