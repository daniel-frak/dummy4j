---
layout: default
title: Home
nav_order: 1
---

Dummy4j is an easy to use dummy data generator library for Java, designed for extensibility.

Dummy4j can be used in all projects using Java 8+.

[View project on GitHub](https://github.com/daniel-frak/dummy4j)

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
  <version>0.9.0</version>
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

* [Name](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/NameDummy.html)
* [Nation](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/NationDummy.html) *(since 0.2.0)*
* [Address](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/AddressDummy.html)
* [Lorem](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/LoremDummy.html)
* [Date and Time](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/DateAndTimeDummy.html) *(since 0.4.0)*
* [Identifier](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/IdentifierDummy.html) *(since 0.5.0)*
* [Education](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/EducationDummy.html) *(since 0.3.0)*
* [Book](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/BookDummy.html) *(since 0.2.0)*
* [Research Paper](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/ResearchPaperDummy.html) *(since 0.2.0)*
* [Sci-fi](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/ScifiDummy.html)
* [Color](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/color/ColorDummy.html) *(since 0.4.0)*
* [Numerals](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/NumeralsDummy.html) *(since 0.4.0)*
* [Medical](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/MedicalDummy.html) *(since 0.4.0)*
* [Nato Phonetic Alphabet](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/NatoPhoneticAlphabetDummy.html) *(since 0.4.0)*
* [Internet](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/internet/InternetDummy.html) *(since 0.5.0)*
* [Finance](https://javadoc.io/doc/dev.codesoapbox/dummy4j/latest/dev/codesoapbox/dummy4j/dummies/finance/FinanceDummy.html) *(since 0.6.0)*

# API stability notice

While you are encouraged to start using it now, beware that the API of every version before 1.0.0 is considered less
stable than it will be after hitting that milestone - versions 0.X.X are intended to fine-tune the API for general
usability (with your input).

**The API of 0.X.X is not expected to change substantially**, however it is still important to be aware of this.
