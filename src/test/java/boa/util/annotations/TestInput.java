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

package boa.util.annotations;

/**
 * Annotation to tell a testing {@link javax.annotation.processing.Processor annotation processor}
 * where processing should begin. This can help with testing, especially since an annotation of some
 * kind is needed to trigger an annotation processor.
 *
 * @author dwtj
 */
public @interface TestInput {
    // Nothing needed here.
}
