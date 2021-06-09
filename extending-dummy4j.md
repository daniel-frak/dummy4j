---
layout: default
title: Extending Dummy4j
nav_order: 4
---

# Contents
{:.no_toc}

* TOC
{:toc}

# Custom definitions

One of the most important features of Dummy4j is the ability to easily provide custom definitions.

## File structure

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

## Expression resolver

The expression resolver recognizes three placeholders:
* `#{path.to.key}` (single-locale placeholder) - will resolve to a random value from the list of data definitions using
 the provided key path; The resolved value may itself be an expression. If the path resolves to a list of keys
 (instead of values), a random one of them will be returned.
 The value will always be taken from a single locale - the first one which contains values for the path, unless the
 placeholder is part of a nested expression in which case it will always resolve only to its parent expression's 
 locale. The parent expression's locale is the locale of the first placeholder that was resolved within it.
* `#{{path.to.key}}` (multi-locale placeholder, since SNAPSHOT) - like above, but will return a random value from the
  superset of all locales' values, regardless of whether it is part of a nested expression.
* `#` (digit) - will resolve to a random digit between 0 and 9

Dummy4j can resolve expressions, which are a mix of the aforementioned placeholders and other characters, e.g.:

* `#{name.male_first_name} #{name.last_name}`
* `##-###`

The expression resolver will first try to resolve the path in the locale which is first on the list. Failing that,
it will keep going down the list until it resolves it or returns NULL.

It is also possible to resolve nested expressions, e.g.:
`#{key1.#{key2}}`.
In that case, the placeholder `#{key2}` will be resolved first and its result will be used to resolve
the root placeholder. This can be especially useful for picking a random key
(`#{key1.#{key1}}` will resolve a random key from `key1`).

## Providing custom files

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
System.out.println(dummy.expressionResolver().resolve("#{my_definitions.any_thing}"));
```

The above code might print a value like `def: 455-827`.

It will, however, be easier to use these new definitions if an appropriate interface is provided in a Dummy4j
instance - you can achieve this by extending the `Dummy4j` class itself.

# Extending the Dummy4j class

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
            return dummy4j.expressionResolver.resolve("#{my_definitions.thing1}");
        }

        public String thing2() {
            return dummy4j.expressionResolver.resolve("#{my_definitions.thing2}");
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

For convenience, you might want to extend `AbstractDummy4jBuilder` as well. The `Dummy4jBuilder` class serves as an
example of this.

# The core classes

The core classes can be configured or swapped out entirely as presented in the "Configuration" section.

## DefinitionProvider

The `DefinitionProvider` interface represents the source of dummy data definitions. The default implementation is
`YamlFileDefinitionProvider` which loads `.yml` files and converts them to `LocalizedDummyDefinitions` instances.

## ExpressionResolver

`ExpressionResolver` is responsible for all logic of parsing and resolving expressions into actual dummy data.

## RandomService

`RandomService` encapsulates Java's `Random` and provides additional functionality, such as accessing the seed.

## LocalizedDummyDefinitions

The `LocalizedDummyDefinitions` interface is an abstraction over a collection of dummy data definitions for a given
locale. The default implementation is `LocalizedDummyDefinitionsMap` which stores the definitions in-memory as a Java
Map.