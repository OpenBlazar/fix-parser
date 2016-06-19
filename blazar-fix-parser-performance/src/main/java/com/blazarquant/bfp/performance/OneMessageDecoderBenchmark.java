package com.blazarquant.bfp.performance;

import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Zankowski
 */
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Thread)
public class OneMessageDecoderBenchmark {

    private FixDefinitionProvider definitionProvider;
    private FixParser parser;

    @Setup
    public void init() throws Exception {
        parser = new FixParser();
        definitionProvider = new DefaultFixDefinitionProvider();
    }

    @Benchmark
    public void oneMessageDecoderBenchmark() {
        parser.parseInput(BenchmarkConstants.FIX_MESSAGE, definitionProvider);
    }

}
