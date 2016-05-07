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

package com.apps4j.javatests.iteration;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(IterationComparison.N)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@State(Scope.Thread)
@Fork(3)
public class IterationComparison {

    public static final int N = 10000;

    public static List<Person> sourceList = new ArrayList<>();

    static {
        for (int i = 0; i < N; i++) {
            sourceList.add(new Person("test " + i, "test " + i, i, "test " + i, "test " + i, "test " + i));
        }
    }

    @Benchmark
    public void vanilla(Blackhole blackhole) {
        long totalAge = 0;
        for (Person p : sourceList) {
            if (p.getAge() >= 23 && p.getAge() <= 80) {
                totalAge += p.getAge();
            }
        }
        blackhole.consume(totalAge);
    }

    @Benchmark
    public void stream(Blackhole blackhole) {
        long totalAge = sourceList
                .stream()
                .filter(p -> p.getAge() >= 23 && p.getAge() <= 80)
                .mapToInt(Person::getAge)
                .sum();
        blackhole.consume(totalAge);
    }

    @Benchmark
    public void parallelStream(Blackhole blackhole) {
        long totalAge = sourceList
                .parallelStream()
                .filter(p -> p.getAge() >= 23 && p.getAge() <= 80)
                .mapToInt(Person::getAge)
                .sum();
        blackhole.consume(totalAge);
    }
}
