package com.blazarquant.bfp.performance;

import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.*;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Zankowski
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@State(Scope.Thread)
public class HugeChunkDecoderBenchmark {

    private String hugeChunk = "";

    private FixParser parser;
    private FixDefinitionProvider definitionProvider;

    @Setup
    public void init() throws Exception {
        InputStream fis = getClass().getClassLoader().getResourceAsStream("hugechunk.txt");
        hugeChunk = new String(IOUtils.toByteArray(fis), "UTF-8");
        parser = new FixParser();
        definitionProvider = new DefaultFixDefinitionProvider();
    }

    @Benchmark
    public void hugeChunkDecoderBenchmark() {
        parser.parseInput(hugeChunk, definitionProvider);
    }

}
