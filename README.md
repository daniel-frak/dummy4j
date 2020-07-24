# Dummy4j

![Code Soapbox logo](readme-images/logo.png)

![Build](https://github.com/daniel-frak/dummy4j/workflows/Build/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=alert_status)](https://sonarcloud.io/dashboard?id=dummy4j)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=dummy4j)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=coverage)](https://sonarcloud.io/dashboard?id=dummy4j)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=sqale_index)](https://sonarcloud.io/dashboard?id=dummy4j)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=dummy4j)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=dummy4j&metric=bugs)](https://sonarcloud.io/dashboard?id=dummy4j)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/daniel-frak/dummy4j)
![GitHub](https://img.shields.io/github/license/daniel-frak/dummy4j)

[![javadoc](https://javadoc.io/badge2/dev.codesoapbox/dummy4j/javadoc.svg)](https://javadoc.io/doc/dev.codesoapbox/dummy4j)

Dummy4j is an easy to use dummy data generator library for Java, designed for extensibility.

Dummy4j can be used in all projects using Java 8+.

## Getting started

Add the following dependency to your `pom.xml`:

```xml
<!-- https://mvnrepository.com/artifact/dev.codesoapbox/dummy4j -->
<dependency>
  <groupId>dev.codesoapbox</groupId>
  <artifactId>dummy4j</artifactId>
  <version>0.4.0</version>
</dependency>
```

Now you can start using Dummy4j:
```java
Dummy4j dummy = new Dummy4j();

String randomCountry = dummy.nation().country();
```

The default configuration of Dummy4j uses a file-based definition provider which reads data definitions from `.yml`
files inside the `resources/dummy4j` folder. Additionally, the default locale is `en`.

## Documentation

Reference documentation for Dummy4j is available at:\
https://daniel-frak.github.io/dummy4j

API documentation for Dummy4j is available at:\
https://javadoc.io/doc/dev.codesoapbox/dummy4j

## Motivation

Generating dummy data greatly simplifies development and testing of software.
While many such generators exist, none of those for Java seem to support easy extensibility, which is crucial for
many use cases.

While the ideas behind Dummy4j were first presented as a proposal for [Java Faker](https://github.com/DiUS/java-faker),
it turned out that it would be easier to write an entirely new library.

Dummy4j focuses on ease of use, clean code and effortless extensibility, while still taking inspiration from Ruby's 
[Faker](https://github.com/faker-ruby/faker) gem.

## Why extensibility is important

Not all data is going to be useful to the general public and not all data can be publicly shared due to company
policies. Therefore, it is imperative a dummy data library allows for easy and maintainable extension of its
data definitions.

Furthermore, edge cases may exist where the functionality of the library must be altered. It is easy to imagine
wanting to store data definitions in a central private repository from which the library would load them during
instantiation. The classes themselves must, therefore, be clean, well documented and extensible.

## Out-of-the-box dummies

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
* Medical *(since 0.4.0)*
* Nato Phonetic Alphabet *(since 0.4.0)*

## Usage examples:

```java
Dummy4j dummy = new Dummy4j();

String fullName = dummy.name().fullName();

String researchPaperTitle = dummy.researchPaper().title();

String sixSentenceParagraph = dummy.lorem().paragraph(6);

String romanNumeralsBetweenOneAndFifteen = dummy.numerals().roman(1, 15);

LocalDate birthdayBetween18And35 = dummy.dateAndTime().birthday(18, 35);

LocalDateTime upTo100YearsInThePast = dummy.dateAndTime().past(100, ChronoUnit.YEARS);

LocalDateTime upTo25YearsFrom1800 = dummy.dateAndTime().after(
        LocalDateTime.parse("1800-01-01T00:00:00"), 25, ChronoUnit.YEARS);

int betweenFiveAndTenInclusive = dummy.random().nextInt(5, 10);

MyEnum randomEnum = dummy.random().enumValue(MyEnum.class);

String thisValueMightBeNull = dummy.random().chance(1, 3, () -> "hello");

String thisValueWillBeUnique = dummy.unique().value("fullNameGroup", () -> dummy.name().fullName());

List<String> fiveNames = dummy.listOf(5, () -> dummy.name().fullName());

Set<String> fourCities = dummy.setOf(4, () -> dummy.address().city());

String nameOrCity = dummy.random().of(() -> dummy.name().fullName(), () -> dummy.address().city());

String elementFromArray = dummy.of(new String[]{ "one", "two", "three" });

String elementFromCollection = dummy.of(Arrays.asList("one", "two", "three"));
```

## Goals and contributing

The goal of Dummy4j is to become the most versatile, extensible and easy to use dummy data generation library.
To that end, all contributions are welcome - whether it's Pull Requests, architectural proposals or simply issues, feel
free to share them as only a continued conversation around the use of this tool can lead to innovation.

While care will always be taken to keep Dummy4j backwards compatible, some breaking changes might prove beneficial
enough to the project to include them in new releases. However, even in those cases the decision will not be taken
lightly and all possible measures will be taken to ensure a smooth transition to a new version of the library.

Finally, if you disagree with any details of how this project is maintained, feel free to create an issue! 
