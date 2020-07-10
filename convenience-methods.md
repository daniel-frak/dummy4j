---
layout: default
title: Convenience methods
nav_order: 3
---

# Contents
{:.no_toc}

* TOC
{:toc}

# Chance method

You might want to randomize which fields should be filled and which should be left empty in an object.
You can do this using the `chance(...)` method:
```java
String thisValueMightBeNull = dummy4j.random().chance(1, 3, () -> "hello");
```    

In the above code, there is a one-in-three chance that the value will contain `"hello"` and a two-in-three chance that
it will be `null`. 

# Unique values *(experimental) (since 0.1.2)*

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

# Generating collections (since 0.4.0)

Dummy4j provides convenience methods to quickly create collections of items.

```java
List<String> fiveNames = dummy.listOf(5, () -> dummy.name().fullName());
Set<String> fourCities = dummy.setOf(4, () -> dummy.address().city());
```