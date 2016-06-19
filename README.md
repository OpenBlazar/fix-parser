# Blazar FIX Parser

[![Build Status](https://travis-ci.org/OpenBlazar/blazar-fix-parser.svg?branch=master)](https://travis-ci.org/OpenBlazar/blazar-fix-parser) 
[![codecov.io](https://codecov.io/github/OpenBlazar/blazar-fix-parser/coverage.svg?branch=master)](https://codecov.io/github/OpenBlazar/blazar-fix-parser?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/56ac0d077e03c7003ba40de7/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56ac0d077e03c7003ba40de7)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Live preview: [BlazarQuant](http://blazarquant.com/parser)

# Introduction

Main goal of this project was to get familiar with different technologies than I already had opportunity to learn. I chose FIX parser because FIX itself is something I've already worked with, it is fairly simple topic and I wanted to create project at least little useful for someone. Web application is used in one company that use FIX daily in their software. Making money is not purpose of this site, but there is PayPal subscription added to help covering server expenses. If you would like to develop Blazar FIX Parser then pull requests are welcomed. 

# Stack

Technology stack may feel odd for more experience developers, but as I said choice was made to choose older or less common technologies, so no Spring, Hibernate and AngularJS. What I decided to use is:

* [JSF 2](https://javaserverfaces.java.net/) - Heavyweight web framework. People say it is relic technology and javascript frameworks with REST services are way to go for the future, but with Java EE8 and JSF 2.3 it all can turn around. With extensions like primefaces and prettyfaces it wasn't hard to work with it.
* [Primefaces](https://github.com/primefaces/primefaces) - UI extension that really eases up working with JSF, with new 6.0 version it makes JSF looks modern. 
* [Prettyfaces](https://github.com/ocpsoft/prettyfaces) - I used it to short URLs, cannot say bad thing.
* [Shiro](https://github.com/apache/shiro) - Alternative to Spring Security and PicketLink. It has some security pitfalls with default configuration, but they can be patched after little googling. Configuration file can be avoided if we mix framework with e.g. Google Guice. 
* [MyBatis](https://github.com/mybatis/mybatis-3) - More alternative to Spring JDBC than Hibernate, but it is decent framework for database connection. I used their Annotation API instead of configuration file and it worked really well. Some people say that clear SQL queries in Java code can be hard to mantain and I would agree with them after all.
* [Guice](https://github.com/google/guice) - In-Java lightweight dependency injection framework. Before I had only experience with Spring XML DI, so it was quite a new thing for me. After first little confusion I really enjoyed working with Guice, definetly will use it in future projects.

# FIX Parser

# TODO

* Increase unit test coverage
* Integartion tests
* Clean up
* Admin Panel
* FAST support
* SBE FIX support
* Improve UX
