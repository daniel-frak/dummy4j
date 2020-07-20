---
layout: default
title: Home
nav_order: 1
---

Dummy4j is an easy to use dummy data generator library for Java, designed for extensibility.

Dummy4j can be used in all projects using Java 8+.

# Contents
{:.no_toc}

* TOC
{:toc}

# Getting started

Add the following dependency to your `pom.xml`:

```xml
<!-- https://mvnrepository.com/artifact/dev.codesoapbox/dummy4j -->
<dependency>
  <groupId>dev.codesoapbox</groupId>
  <artifactId>dummy4j</artifactId>
  <version>0.3.1</version>
</dependency>
```

Now you can start using Dummy4j:
```java
Dummy4j dummy = new Dummy4j();

String randomCountry = dummy.nation().country();
```

The default configuration of Dummy4j uses a file-based definition provider which reads data definitions from `.yml`
files inside the `resources/dummy4j` folder. The default locale is `en`.

# Out-of-the-box dummies

While you can easily add your own dummy data definitions, the following are available out of the box: 

* Name
* Nation *(since 0.2.0)*
* Address
* Lorem
* Date and Time *(since 0.4.0)*
* Education *(since 0.3.0)*
* Book *(since 0.2.0)*
* Research Paper *(since 0.2.0)*
* Sci-fi
* Color *(since 0.4.0)*
* Numerals *(since 0.4.0)*

# API stability notice

While you are encouraged to start using it now, beware that the API of every version before 1.0.0 is considered less
stable than it will be after hitting that milestone - versions 0.X.X are intended to fine-tune the API for general
usability (with your input).

**The API of 0.X.X is not expected to change substantially**, however it is still important to be aware of this.