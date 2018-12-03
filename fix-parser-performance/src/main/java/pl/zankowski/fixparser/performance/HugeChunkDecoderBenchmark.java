package pl.zankowski.fixparser.performance;

import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.loader.QuickFixDictionaryLoader;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptorBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionaryBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;
import pl.zankowski.fixparser.messages.fix.FixParser;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("FIX50SP2.xml");
        final QuickFixDictionaryLoader fixXMLLoader = new QuickFixDictionaryLoader();

        final Map<Integer, FixFieldDefinition> values = fixXMLLoader.parseDocument(inputStream);

        final FixDictionary dictionary = new FixDictionaryBuilder()
                .withDictionaryDescriptorEntity(new DictionaryDescriptorBuilder()
                        .build())
                .withDictionaryMap(values)
                .build();
        definitionProvider = new DefaultFixDefinitionProvider(dictionary);

        InputStream fis = getClass().getClassLoader().getResourceAsStream("hugechunk.txt");
        hugeChunk = new String(IOUtils.toByteArray(fis), "UTF-8");
        parser = new FixParser();
    }

    @Benchmark
    public void hugeChunkDecoderBenchmark() {
        parser.parseInput(hugeChunk, definitionProvider);
    }

}
