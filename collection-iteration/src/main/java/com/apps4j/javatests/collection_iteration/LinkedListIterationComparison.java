/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.apps4j.javatests.collection_iteration;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(LinkedListIterationComparison.N)
@Warmup(iterations = 5)
//@Measurement(iterations = 5)
@Fork(3)
public class LinkedListIterationComparison {

    public static final int N = 100_000;

    @State(Scope.Thread)
    public static class TestState {
        public Collection<Integer> sourceList = new LinkedList<>();

        @Setup(Level.Invocation)
        public void setup() {
            for (int i = 0; i < N; i++) {
                sourceList.add(i);
            }
        }
    }

    @Benchmark
    public void testVanilla(TestState state, Blackhole bh) {
        for (Integer b : state.sourceList) {
            bh.consume(b);
        }
    }

    @Benchmark
    public void testLambda(TestState state, Blackhole bh) {
        state.sourceList.forEach(bh::consume);
    }

    @Benchmark
    public void testArray(TestState state, Blackhole bh) {
        for (int i = 0; i < state.sourceList.size(); i++) {
            bh.consume(state.sourceList);

        }
    }
}
