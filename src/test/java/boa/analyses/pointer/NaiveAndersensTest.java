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

package boa.analyses.pointer;

import boa.datagen.suntree.SunTreeAdapter;
import boa.util.CompilationUnitsProcessor;
import me.dwtj.dacapo.Benchmarks;
import org.junit.Test;

import static me.dwtj.dacapo.BenchmarkSources.getResourceNames;

/**
 * Used to drive {@link NaiveAndersens}, a simple Andersen's style points-to analysis. It is both
 * context insensitive and flow insensitive.
 *
 * @author dwtj
 */
public class NaiveAndersensTest {

    @Test
    public void naiveAndersensTest() {
        SunTreeAdapter adapter = new SunTreeAdapter();
        CompilationUnitsProcessor.fromResourceNames(cu -> NaiveAndersens.analyze(adapter.adapt(cu)),
                                                    getResourceNames(Benchmarks.SUNFLOW));
    }
}
