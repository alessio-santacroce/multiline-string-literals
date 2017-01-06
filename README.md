# Multiline string literals in java
This library provides an utility to define multiline string literals in java as described in this post http://blog.efftinge.de/2008/10/multi-line-string-literals-in-java.html <br />
**The purpose it to make junit tests more readable** 

<br />
> **Question 1** - How does it work?

The following code:
```java
import static github.com.alessiosantacroce.multilinestring.MultilineStringLiteral.newString;
System.out.println(newString(/*
{
    "firstName": "John",
        "address": {
            "streetAddress": "21 2nd Street",
            "city": "New York"
        }
}
*/));
```
prints:
```
{
    "firstName": "John",
        "address": {
            "streetAddress": "21 2nd Street",
            "city": "New York"
        }
}
```

<br />
> **Question 2** - What the hell! A comment is given as parameter to a method?

Don't be silly. A comment can't be a parameter for a method.

<br />
> **Question 3** - mmm... what's going on here?

The method _newString_ gets, from the thread stack, the class name and the line of the caller.
It opens the java source file and reads the comment at the given line.

<br />
> **Question 4** - So... java source files have to be available at runtime!

Yep. Since the purpose to this library is for junit test, it shouldn't be an issue adding test source files to the jar.
If you use maven, simply add to the pom.xml (e.g.: see [pom.xml](pom.xml#L42))
```xml
<testResources>
    <testResource>
        <directory>${basedir}/src/test/java</directory>
    </testResource>
</testResources>
```

<br />
> **Question 5** - Fantastic, that works just fine. But it is still an hack, not a clean solution! Which alternatives do I have?

Unfortunately java doesn't provide a good support for multiline string literal. Here are some alternatives to make your code more readable.

- Explicitly load the string from an external file
- If what you need is simply to use a special character, you might consider to use the replace method:
```java
final String s = "{ 'name': 'John' }".replace('\'', '"');
```
- If you need to instantiate json or xml string, you could use a library like:
```java
final String json = Json.createObjectBuilder()
        .add("firstName", "John")
        .add("address", Json.createObjectBuilder()
                .add("streetAddress", "21 2nd Street")
                .add("city", "New York")
        .build()
        .toString();
```
- Consider writing your test with another language supported by the JVM like scala or kotlin.
- Check this other implementation for multiline string literals using javadoc comments: https://github.com/benelog/multiline