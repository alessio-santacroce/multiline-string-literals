# Multiline string literals in java
This library provides an utility to define multiline string literals in java as described by Sven Efftinge in this [post](http://blog.efftinge.de/2008/10/multi-line-string-literals-in-java.html).
 <br />
The purpose is to make **junit tests more readable** when there are json, sql or xml strings.
__________

> **Question 0** - How can I import this library?

This library has been released on the central maven with [MIT license](https://en.wikipedia.org/wiki/MIT_License). 
Simply add the dependency in your pom.xml:
```xml
<dependency>
  <groupId>com.github.alessio-santacroce</groupId>
  <artifactId>multiline-string-literals</artifactId>
  <version>1.0.1</version>
</dependency>
```

> **Question 1** - How does it work?

<table>
<tr>
<td><b>Usage sample</b></td>
<td><b>Groovy equivalent</b></td>
<td><b>Result</b></td>
</tr>
<tr>
<td><pre><code>
System.out.println(newString(/*
      Wow, we finally have
      multiline strings in
      Java! HOOO!
*/));
</code></pre></td>
<td><pre><code>
println """
      Wow, we finally have
      multiline strings in
      Java! HOOO!
"""
</code></pre></td>
<td><pre>
      Wow, we finally have
      multiline strings in
      Java! HOOO!
</pre></td>
</tr>



<tr>
<td><pre><code>
System.out.println(stripMargin(/*
      |Wow, we finally have
      multiline strings in
      |Java! HOOO!
*/));
</code></pre></td>
<td><pre><code>
println """
      |Wow, we finally have
      multiline strings in
      |Java! HOOO!
"""
</code></pre></td>
<td><pre>
Wow, we finally have
      multiline strings in
Java! HOOO!
</pre></td>
</tr>
</table>

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
If you use maven, simply add to the pom.xml (e.g.: see [pom.xml](pom.xml#L55))
```xml
<testResources>
    <testResource>
        <directory>${basedir}/src/test/java</directory>
    </testResource>
</testResources>
```

<br />
> **Question 5** - Can I see a sample of a test written with this library?

Of course... see: [MultilineStringLiteralTest.java](src/test/java/com/github/alessiosantacroce/multilinestring/MultilineStringLiteralTest.java)


<br />
> **Question 6** - Fantastic, that works just fine. But it is still an hack, not a clean solution! Which alternatives do I have?

Unfortunately java doesn't provide a good support for multiline string literal. Here are some alternatives to make your code more readable.

- Explicitly load the string from an external file
- If you need to instantiate json or xml string, you could use a library: [example1](https://docs.oracle.com/javaee/7/api/javax/json/JsonObjectBuilder.html), [example2](src/test/java/com/github/alessiosantacroce/multilinestring/MultilineStringLiteralTest.java#18)
- Consider writing your test with another language supported by the JVM like scala or kotlin.
- Check out this other implementation for multiline string literals using javadoc comments: https://github.com/benelog/multiline
- If what you need is simply to use a special character, you might consider to use the replace method:
```java
final String s = "{ 'name': 'John' }".replace('\'', '"');
```
