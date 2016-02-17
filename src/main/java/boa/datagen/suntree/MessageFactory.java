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
import boa.util.annotations.Singleton;
import com.google.protobuf.Message;

/**
 * This is an interface to be implemented by all concrete message factory types (e.g. {@link
 * ASTRootFactory} and {@link ExpressionFactory}).
 *
 * TODO: Describe the expected design pattern.
 *
 * @see boa.datagen.suntree
 * @author dwtj
 */
@NoMutableState
@NoStaticMutableState
@Singleton
public interface MessageFactory<T extends Message.Builder>  {
    // Nothing needed here (yet).
}
