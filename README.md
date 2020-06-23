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

Dummy4j is an easy to use dummy data generator library for Java, designed for extensibility.

Dummy4j can be used in all projects using Java 8+.

## Motivation

Generating dummy data greatly simplifies development and testing of software.
While many such generators exist, none of those for Java seem to support easy extensibility, which is crucial for
many use cases.

While the ideas behind Dummy4j were first presented as a proposal for [Java Faker](https://github.com/DiUS/java-faker),
it turned out that it would be easier to write an entirely new library.

Dummy4j focuses on ease of use, clean code and effortless extensibility, while still taking inspiration from Ruby's 
[Faker](https://github.com/faker-ruby/faker) gem.

## Getting started

Add the following dependency to your `pom.xml`:

```xml
<!-- https://mvnrepository.com/artifact/dev.codesoapbox/dummy4j -->
<dependency>
  <groupId>dev.codesoapbox</groupId>
  <artifactId>dummy4j</artifactId>
  <version>0.2.0</version>
</dependency>
```

Now you can start using Dummy4j:
```java
Dummy4j dummy = new Dummy4j();

System.out.println(
    dummy.name().fullName()
);
```

The default configuration of Dummy4j uses a file-based definition provider which reads data definitions from `.yml`
files inside the `resources/dummy4j` folder. Additionally, the default locale is `en`.

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
* Book *(since 0.2.0)*
* Research Paper *(since 0.2.0)*
* Sci-fi

## Advanced usage

This section covers more advanced usage of Dummy4j, such as configuration and helpful methods.

### Simple configuration

A constructor is provided to define a seed and locales. Both of the parameters are nullable - Dummy4j will
use default values for any value which is passed as NULL.

Two additional constructors are provided for convenience.

```java
// Custom seed and locales
Dummy4j dummy = new Dummy4j(123456L, Collections.singletonList("en"));

// Custom seed
Dummy4j dummy = new Dummy4j(123456L);

// Custom locales
Dummy4j dummy = new Dummy4j(Collections.singletonList("en"));
```

### Advanced configuration

Advanced configuration can be performed by using Dummy4j's dedicated constructor, which allows for injecting custom
implementations of its dependencies. This allows, for example, to define custom paths from which definitions should
be loaded. 

```java
RandomService randomService = new RandomService(123456L);

YamlFileDefinitionProvider definitionProvider = YamlFileDefinitionProvider.withPaths(
        Arrays.asList("dummy4j", "customPath"));
ExpressionResolver expressionResolver = new ExpressionResolver(Collections.singletonList("en"),
        randomService, definitionProvider);
UniqueValues uniqueValues = new UniqueValues();

Dummy4j dummy = new Dummy4j(expressionResolver, randomService, Dummies::new, uniqueValues);
```

Keep in mind that this constructor directly exposes Dummy4j's internals, which might be altered in the future! 

### Chance method

You might want to randomize which fields should be filled and which should be left empty in an object.
You can do this using the `chance(...)` method:
```java
String thisValueMightBeNull = dummy4j.random().chance(1, 3, () -> "hello");
```    

In the above code, there is a one-in-three chance that the value will contain `"hello"` and a two-in-three chance that
it will be `null`. 

### Unique values *(experimental) (since 0.1.2)*

It is possible to generate unique values by wrapping a call with the `dummy.unique().value(...)` method:

```java
for (int i = 0; i < 10; i++) {
    System.out.println(
      dummy.unique().value("fullNameGroup", () -> dummy.name()).fullName())
    );
}
```

The above will print 10 names, all of which will be unique within the `fullNameGroup` uniqueness group.

Note that this is an experimental feature and its API may be subject to change if it proves to not be useful enough.

## Extending Dummy4j

### Custom definitions

One of the most important features of Dummy4j is the ability to easily provide custom definitions.

#### File structure

The structure of the dummy data definition file looks like the following:
```yaml
locale:
  path:
    to:
      key: [ "value1", "value2", "value3" ]
```

The root element is the `locale`, which simply defines to which locale the definitions belong. One file can have
definitions for multiple locales.

`path.to.key` is the key which will be resolvable within Dummy4j and its list are the values from which the library
will pick out a random element.

In the above example, `#{path.to.key}` would resolve to either `value1`, `value2` or `value3` (assuming the Dummy4j instance
would be configured for the specific locale).

Dummy4j internally merges all of the `.yml` files into one big map and then splits it into collections of localized
definitions. This means, among other things, that the name of the file is irrelevant. Keep in mind, however, that two
files of the same name within the same path may override each other.

If you choose to, you can place all of your definitions in one file. You can also spread them out over several files.

#### Parser

The parser recognizes two placeholders:
* `#{key.path}` - will resolve to a random value from the list of data definitions using the provided key path; The
 resolved value may itself be an expression
* `#` - will resolve to a random digit between 0 and 9 

Dummy4j can resolve expressions, which are a mix of the aforementioned placeholders and other alphanumeric characters
, e.g.:

* `#{name.male_first_name} #{name.last_name}`
* `##-###`

The parser will first try to resolve the key in the locale which is first on the list. Failing that, it will keep
going down the list until it resolves it or returns NULL.

#### Providing custom files

Let's say you have created the following definitions file:
```yaml
en:
  my_definitions:
    thing1: [ "abc", "def", "ghijk" ]
    thing2: [ "###-###" ]
    any_thing: [ "#{my_definitions.thing1}: #{my_definitions.thing2}" ]
``` 

To make dummy4j recognize these definitions, you must put them in the `resources/dummy4j` directory. Afterwards, you can
start using them immediately:
```java
Dummy4j dummy = new Dummy4j();
System.out.println(dummy.getExpressionResolver().resolve("#{my_definitions.any_thing}"));
```

The above code might print a value like `def: 455-827`.

It will, however, be easier to use these new definitions if an appropriate interface is provided in a Dummy4j
instance - you can achieve this by extending the `Dummy4j` class itself.

### Extending the Dummy4j class

Going further with the `my_definitions` example, it's easy to create methods for the new definitions in a custom
class extending
`Dummy4j`. Below is one (but not the only) way to do this:

```java
public class CustomDummy4j extends Dummy4j {

    private final MyDefinitionsDummy myDefinitions;

    public CustomDummy4j() {
        myDefinitions = new MyDefinitionsDummy(this);
    }

    public MyDefinitionsDummy myDefinitions() {
        return myDefinitions;
    }

    public static class MyDefinitionsDummy {

        private final CustomDummy4j dummy4j;

        public MyDefinitionsDummy(CustomDummy4j dummy4j) {
            this.dummy4j = dummy4j;
        }

        public String thing1() {
            return dummy4j.expressionResolver.resolveKey("my_definitions.thing1");
        }

        public String thing2() {
            return dummy4j.expressionResolver.resolveKey("my_definitions.thing1");
        }

        public String anyThing() {
            return dummy4j.expressionResolver.resolve("#{my_definitions.any_thing}");
        }
    }
}
```

You can now use the new API:
```java
CustomDummy4j dummy = new CustomDummy4j();
System.out.println(dummy.myDefinitions().anyThing());
```

### The core classes

The core classes can be configured or swapped out entirely as presented in the "Getting started" section.

#### DefinitionProvider

The `DefinitionProvider` interface represents the source of dummy data definitions. The default implementation is
`YamlFileDefinitionProvider` which loads `.yml` files and converts them to `LocalizedDummyDefinitions` instances.

#### ExpressionResolver

`ExpressionResolver` is responsible for all logic of parsing and resolving expressions into actual dummy data.

#### RandomService

`RandomService` encapsulates Java's `Random` and provides additional functionality, such as accessing the seed.

#### LocalizedDummyDefinitions

The `LocalizedDummyDefinitions` interface is an abstraction over a collection of dummy data definitions for a given
locale. The default implementation is `LocalizedDummyDefinitionsMap` which stores the definitions in-memory as a Java
Map. 

## Goals and contributing

The goal of Dummy4j is to become the most versatile, extensible and easy to use dummy data generation library.
To that end, all contributions are welcome - whether it's Pull Requests, architectural proposals or simply issues, feel
free to share them as only a continued conversation around the use of this tool can lead to innovation.

While care will always be taken to keep Dummy4j backwards compatible, some breaking changes might prove beneficial
enough to the project to include them in new releases. However, even in those cases the decision will not be taken
lightly and all possible measures will be taken to ensure a smooth transition to a new version of the library.

Finally, if you disagree with any details of how this project is maintained, feel free to create an issue! 

## API stability notice

While you are encouraged to start using it now, beware that the API of every version before 1.0.0 is considered less
stable than it will be after hitting that milestone - versions 0.X.X are intended to fine-tune the API for general
usability (with your input).

**The API of 0.X.X is not expected to change substantially**, however it is still important to be aware of this.

## Data sources

Below are given notable sources from which data was gathered for dummy4j.

* https://github.com/stympy/faker
* https://www.census.gov/topics/population/genealogy/data/2000_surnames.html
* https://www.ssa.gov/cgi-bin/popularnames.cgi
* https://en.wikipedia.org/wiki/List_of_book_titles_taken_from_literature
* https://en.wikipedia.org/wiki/List_of_writing_genres
* https://arxiv.org
