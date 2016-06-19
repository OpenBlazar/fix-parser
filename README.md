# Blazar FIX Parser

[![Build Status](https://travis-ci.org/OpenBlazar/blazar-fix-parser.svg?branch=master)](https://travis-ci.org/OpenBlazar/blazar-fix-parser) 
[![codecov.io](https://codecov.io/github/OpenBlazar/blazar-fix-parser/coverage.svg?branch=master)](https://codecov.io/github/OpenBlazar/blazar-fix-parser?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/56ac0d077e03c7003ba40de7/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56ac0d077e03c7003ba40de7)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Live preview: [BlazarQuant](http://blazarquant.com/parser)

# Introduction

Main goal of this project was to get familiar with different technologies than I already had opportunity to learn. I chose FIX parser because FIX itself is something I've already worked with, it is fairly simple topic and I wanted to create project at least little useful for someone. Web application is used in one company that use FIX daily in their software. Making money is not purpose of this site, but there is PayPal subscription added to help covering server expenses. If you would like to develop Blazar FIX Parser then pull requests are welcomed. 

# Stack

Technology stack may feel odd for some developers, but as I said choice was made to choose older or less common technologies, so no Spring, Hibernate and AngularJS. What I decided to use is:

* [JSF 2](https://javaserverfaces.java.net/) - Heavyweight web framework. People say it is relic technology and javascript frameworks with REST services are way to go for the future, but with Java EE8 and JSF 2.3 it all can turn around. With extensions like primefaces and prettyfaces it wasn't hard to work with it.
* [Primefaces](https://github.com/primefaces/primefaces) - UI extension that really eases up working with JSF, with new 6.0 version it makes JSF looks modern. 
* [Prettyfaces](https://github.com/ocpsoft/prettyfaces) - I used it to short URLs, cannot say bad thing.
* [Shiro](https://github.com/apache/shiro) - Alternative to Spring Security and PicketLink. It has some security pitfalls with default configuration, but they can be patched after little googling. Configuration file can be avoided if we mix framework with e.g. Google Guice. 
* [MyBatis](https://github.com/mybatis/mybatis-3) - More alternative to Spring JDBC than Hibernate, but it is decent framework for database connection. I used their Annotation API instead of configuration file and it worked really well. Some people say that clear SQL queries in Java code can be hard to mantain and I would agree with them after all.
* [Guice](https://github.com/google/guice) - In-Java lightweight dependency injection framework. Before I had only experience with Spring XML DI, so it was quite a new thing for me. After first little confusion I really enjoyed working with Guice, definetly will use it in future projects.

# FIX Parser

FIX Parser was meant to find FIX messages in large chunks of application logs without any user's input. That's why parser has couple steps to perform before giving an output. First step is to find any FIX message in given input, then based on the message field delimiter is resolved and then we parse it to readable form.

1. To find first FIX message we use regular expression `"[^0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?\\s]*8=FIX(.*?)[^0-9]10=\\d{3}.?"`. To understand this regexp we have to know some basic facts about FIX messages strucutre. Every FIX message starts with header and ends with tail, so it looks like this:

  ``` 8=FIX.4.2#9=41#35=0 ... #10=052 ``` 

  First field of the FIX messages is always BeginString (tag 8) indicating used FIX Specification version used in message. The last field is Checksum (tag 10).

2. If we found the message then field delimiter can be resolved. Once again resolver relies on FIX message specification

  ``` 8=FIX.4.2#9=41#35=0 ...```
  
  Delimiter is extracted based on fields BodyLength (tag 9) and MsgType (tag 35). As BodyLength value is always numerical and those tags are always in the same sequence we can easily extract delimiter. In case of this example delimiter is `#`.
  
3. Last thing to do is transform FIX messages to readable form. FIX message are splitted by field delimiter and then using DefinitionProvider every field is described with name and value description.
 
# Custom dictionaries

Blazar FIX Parse supports custom dictionaries. It is extremly helpful to work with brokers that provides extended FIX definitions and probably every FIX broker did extend its FIX specification. At the moment Blazar FIX parser supports custom dictionaries in QuickFIX format. Whole dictionaries are not required, `<messages>` and `<components>` tags are not interpreted, only `<fields>` ones.

```
<fields>
    <field number="1" name="Account" type="STRING"/>
    <field number="2" name="AdvId" type="STRING"/>
    .
    .
    .
</fields>
```

# Benchmark

There are available 4 benchmarks for FIX parser. 2 benchmarks for 'Huge Chunk' i.e. file with 800 lines and 400 messages inside and 2 benchmarks for single fix message. For closer look up to benchmarks code see blazar-fix-parser-performance module.

```
Benchmark                                               Mode  Cnt        Score      Error  Units
HugeChunkDecoderBenchmark.hugeChunkDecoderBenchmark    thrpt   15      157,784 ±   12,881  ops/s
HugeChunkEncoderBenchmark.hugeChunkEncoderBenchmark    thrpt   15     4273,936 ±  266,361  ops/s
OneMessageDecoderBenchmark.oneMessageDecoderBenchmark  thrpt   50   126210,834 ± 3619,314  ops/s
OneMessageEncoderBenchmark.oneMessageEncoderBenchmark  thrpt   50  1092471,295 ± 7138,211  ops/s
```

# JavaDocs

To see JavaDocs click [here](http://openblazar.github.io/blazar-fix-parser/).

# TODO

* Increase unit test coverage
* Integration tests
* Clean up
* Admin Panel
* FAST support
* SBE FIX support
* Improve UX
