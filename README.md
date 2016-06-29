Cartesian Product of Input Strings
==============================

## PURPOSE

To expand input strings in the bash format `$ echo a{b,c}d{e,f,g}hi` into `abdehi abdfhi abdghi acdehi acdfhi acdghi`

## Packaging

Used maven as the builder/dependency manager, totally overkill but didn't want to give you a bare main class to run or something like that. 

Feels a little like the [JavaEE version of FizzBuzz](https://github.com/EnterpriseQualityCoding/FizzBuzzEnterpriseEdition) but there it is. 

## TO RUN

Install Java, Maven, and set environment in normal ways. 

```mvn test``` will run the test suite. 
 
```mvn package``` will compile the project and then you can run arbitrary input with 
 
```java -cp target/cartesianproduct-1.0-SNAPSHOT.jar com.haakenson.cartesian.product.Main "a{b,c}d{e,f,g}hi"```

## DEPDENCIES

Sole dependency is on JUnit.  This is resolved by maven.

## SEE ALSO

[Recursion](http://s2.quickmeme.com/img/db/db20539b342f1f578151061b27e636aaf6cd48b6e58e28327877f7b724dd79dd.jpg)
