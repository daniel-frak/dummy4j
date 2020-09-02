---
layout: default
title: Configuration
nav_order: 2
---

# Contents
{:.no_toc}

* TOC
{:toc}

# Simple configuration

A constructor is provided to define a seed, locales and file paths (either directories or specific files).
All of the parameters are nullable - Dummy4j will use default values for any value which is passed as NULL.

Additionally, a builder is provided for convenience.

```java
// Constructor
Dummy4j dummy = new Dummy4j(123456L, Collections.singletonList("en"), 
    Arrays.asList("my/custom/path", "dummy4j/some_specific_file.yml"));

// Builder
Dummy4j dummy = new Dummy4jBuilder()
                .seed(123456L)
                .locale(Collections.singletonList("en"))
                .paths(Arrays.asList("my/custom/path", "dummy4j"))
                .build();
```

# Advanced configuration

Advanced configuration can be performed by using Dummy4j's dedicated constructor, which allows for injecting custom
implementations of some of its dependencies.

```java
RandomService randomService = new DefaultRandomService(123456L);

YamlFileDefinitionProvider definitionProvider = YamlFileDefinitionProvider.withPaths(
        Arrays.asList("dummy4j", "my/custom/path"));
ExpressionResolver expressionResolver = new DefaultExpressionResolver(Collections.singletonList("en"),
        randomService, definitionProvider);

Dummy4j dummy = new Dummy4j(expressionResolver, randomService);
```
