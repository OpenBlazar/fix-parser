package com.blazarquant.bfp.performance;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Zankowski
 */
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Thread)
public class OneMessageEncoderBenchmark {

    private FixMessageConverter converter;
    private FixMessage message;

    @Setup
    public void init() throws Exception {
        converter = new FixMessageConverter();
        FixParser parser = new FixParser();
        FixDefinitionProvider definitionProvider = new DefaultFixDefinitionProvider();
        message = parser.parseInput(BenchmarkConstants.FIX_MESSAGE, definitionProvider).get(0);
        System.out.println(message);
    }

    @Benchmark
    public void oneMessageEncoderBenchmark() {
        converter.convertToString(message);
    }

}
