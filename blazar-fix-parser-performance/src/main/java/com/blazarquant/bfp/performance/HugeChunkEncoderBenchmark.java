package com.blazarquant.bfp.performance;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.*;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Zankowski
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@State(Scope.Thread)
public class HugeChunkEncoderBenchmark {

    private List<FixMessage> messageList;
    private FixMessageConverter converter;

    @Setup
    public void init() throws Exception {
        InputStream fis = getClass().getClassLoader().getResourceAsStream("hugechunk.txt");
        String hugeChunk = new String(IOUtils.toByteArray(fis), "UTF-8");
        FixParser parser = new FixParser();
        FixDefinitionProvider definitionProvider = new DefaultFixDefinitionProvider();
        messageList = parser.parseInput(hugeChunk, definitionProvider);

        converter = new FixMessageConverter();
    }

    @Benchmark
    public void hugeChunkEncoderBenchmark() {
        for (FixMessage message : messageList) {
            converter.convertToString(message);
        }
    }

}
