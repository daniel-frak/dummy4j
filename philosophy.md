---
layout: default
title: Dummy4j design philosophy
nav_order: 500
---

# Contents
{:.no_toc}

* TOC
{:toc}

# Introduction

Whether you intend to contribute to Dummy4j or extend it in your own project, it's good to know about the ideas
guiding it. This document explains some of the core concepts which make Dummy4j what it is and should help you
understand how to align your code with the existing architecture. 

# Relying on .yml

Dummy4j's core principle is for it to be extendable and configurable. That means values should not be hardcoded if 
possible.

As an example `dummy4j.internet().url()` creates a URL like:

`https://www.expedita.fr`

The list of top-level domains could easily be an array in Java. That wouldn't be very customizable, though, so
the TLDs are retrieved from the definition map under the key `internet.top_level_domain`. This allows them to easily
be replaced (or extended) by the client. This functionality is especially useful for localizations, as clients need
only to provide definition files for the respective locales for them to work automatically. 

# Providing variety

Data generators tend to provide random records from a simple list of pre-made, hard-coded values.
While this is, in essence, what a lot of Dummy4j does, we strive to give as much as possible with
as little as possible, which means getting a little creative with the source material.
As an example, `research_paper.title_history` could just be defined as:
```yaml
title_history: [
    "An Introduction to the History of France",
    "Causes of World War II",
    "..."
]
```

However, this severely limits the possibilities of data generation.
France is not the only country whose history can be introduced and WWII is not the only event
whose causes could be analysed. It's better to modularise `title_history` and rely upon other
data definitions for more variety:  

```yaml
title_history: [
  "An Introduction to the History of #{research_paper.title_history_of}",
  "Causes of #{research_paper.title_history_cause_of}",
  "..."
]
title_history_of: [
  "#{nation.country}",
  "#{address.city}",
  "..."
]
title_history_cause_of: [
  "World War I",
  "World War II",
  "the Battle of #{address.city}",
  "..."
]
```  

# Providing choice

A client might need to generate a password. A simple method like the one below will usually do:

```java
dummy.internet().password();
``` 

Nevertheless, sometimes more advanced configuration is necessary - perhaps the password must contain digits and
special characters, as well as be exactly 20 characters in length. The builder pattern is a great way to provide choice
for clients:

```java
dummy.internet().passwordBuilder()
        .withDigits()
        .withSpecialChars()
        .withLength(20)
        .build();
```

As demonstrated above, the standard practice for providing choice in Dummy4j is to expose a method for generating
a default output and another (with a `Builder` suffix) for supplying a builder.

# Coupling between dummies

Generally, a project should aim for low coupling - this is also a philosophy behind Dummy4j's core.

Nonetheless, there are great rewards to be reaped from multiple dummy modules working together.
Using existing data generation modules when creating a new one allows it to "stand on the shoulders of giants" and
become more useful than it otherwise would have been.

A good example of it is the aforementioned `title_history` definition, which uses both `nation.country`
and `address.city` to provide a much greater variety of titles than could otherwise be achieved.

Make no mistake, though, as this is the only part of Dummy4j where high coupling is allowed!