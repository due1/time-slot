# Time Slot

This is Time Slot

## Introduction

This is a small Java library for dealing with time slots which are built upon Java 8's new time framework ```java.time```. Useful for the treatment of recurring events. See also:
http://martinfowler.com/apsupp/recurring.pdf.

## Project owner

Eric Dubuis, BFH, Switzerland

## Installation

In order to install this small library into you local Maven repository, checkout this project, and then type:

```
mvn clean install
```

Then, when using Maven from other projects, simply add the following dependency:

```
<dependency>
    <groupId>ch.bfh.due1</groupId>
    <artifactId>time-slot</artifactId>
    <version>VERSION</version>
</dependency>
```

You have to replace `VERSION` by the version number of the latest release. Consult `pom.xml`for the latest stable version number.
