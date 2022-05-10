---
layout: default
title: Convenience methods
nav_order: 3
---

# Contents
{:.no_toc}

* TOC
{:toc}

# Find method *(since SNAPSHOT)*

If you need a particular method but aren't sure where to find it, you can use the `find(...)` method
to quickly locate it:

```java
String message = dummy4j.find("name");
```

The above code will return a message like this, listing all methods containing the given search
string:

```
[...]
Dummy4j.color().name()
Dummy4j.color().primaryName()
Dummy4j.finance().creditCard().getOwnerName()
[...]
Dummy4j.finance().currencyName()
Dummy4j.internet().username()
Dummy4j.name()
Dummy4j.name().firstName()
[...]
```

# Chance method *(since 0.5.0)*

You might want to randomize which fields should be filled and which should be left empty in an object.
You can do this using the `chance(...)` method:
```java
String thisValueMightBeNull = dummy4j.chance(1, 3, () -> "hello");
```    

In the above code, there is a one-in-three chance that the value will contain `"hello"` and a two-in-three chance that
it will be `null`.

# Random element from an array, collection or array of suppliers (since 0.5.0)

The `of(...)` methods return a random element from a given array, collection or an array of suppliers.

```java
String elementFromArray = dummy.of(new String[]{ "one", "two", "three" });

String elementFromCollection = dummy.of(Arrays.asList("one", "two", "three"));

String nameOrCity = dummy.of(() -> dummy.name().fullName(), () -> dummy.address().city());
```

# Globally unique values *(experimental) (since 0.1.2)*

It is possible to generate globally unique values by wrapping a call with the `dummy.unique().value(...)` method:

```java
for (int i = 0; i < 10; i++) {
    System.out.println(
      dummy.unique().value("fullNameGroup", () -> dummy.name().fullName())
    );
}
```

The above will print 10 names, all of which will be unique within the `fullNameGroup` uniqueness group.
The uniqueness is guaranteed for the entire lifetime of a Dummy4j instance.

*Note that this is an experimental feature and its API may be subject to change.*

# Collections of locally unique values *(experimental)* (since 0.7.0)

It is possible to generate locally unique values by wrapping a call with the `dummy.unique().of(...)` method:
```java
List<String> names = dummy.unique().of(() -> dummy.name().fullName(), name -> dummy.listOf(10, name));
System.out.println(names);
```

The above will print 10 names, all of which will be unique within their list.

*Note that this is an experimental feature and its API may be subject to change.*

# Locally unique values within a code block *(experimental)* (since 0.7.0)
It is possible to generate locally unique values by wrapping a code block with the `dummy.unique().within(...)` method:

```java
dummy.unique().within(() -> dummy.name().fullName(), name -> {
    for (int i = 0; i < 10; i++) {
        System.out.println(name.get());
    }
});
```

The above will print 10 names, all of which will be unique within the wrapped consumer code block.

*Note that this is an experimental feature and its API may be subject to change.*

# Generating collections (since 0.4.0)

Dummy4j provides convenience methods to quickly create collections of items.

```java
List<String> fiveNames = dummy.listOf(5, () -> dummy.name().fullName());
Set<String> fourCities = dummy.setOf(4, () -> dummy.address().city());
```

# Random Enum values (since 0.5.0)

You can generate a random enum value by providing an enum class.

```java
MyEnum randomEnum = dummy.nextEnum(MyEnum.class);
```