package com.github.alessiosantacroce.multilinestring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.function.Function;

/**
 * For more details see For more details see git hub project
 * <a href="https://github.com/alessio-santacroce/multiline-string-literals">multiline-string-literals</a>
 */
public class MultilineStringLiteral {

    private static final Function<String, String> NO_STRIP_MARGIN = line -> line;
    private static final Function<String, String> STRIP_MARGIN = new StripMarginFunction();
    private static final String START_COMMENT = "/*";
    private static final String END_COMMENT = "*/";

    private MultilineStringLiteral() {
    }

    /**
     * <pre>
     * System.out.println(MultilineStringLiteral.newString(/*
     *       Wow, we finally have
     *       multiline strings in
     *       Java! HOOO!
     * &#42;/));
     * </pre>
     * prints:
     * <pre>
     *       Wow, we finally have
     *       multiline strings in
     *       Java! HOOO!
     * </pre>
     *
     * @return the string inside the comment. Comment can start with /* or /**
     */
    public static String newString() {
        return _newString(NO_STRIP_MARGIN);
    }

    /**
     * Short alias for {@link com.github.alessiosantacroce.multilinestring.MultilineStringLiteral#newString()}
     */
    public static String S() {
        return _newString(NO_STRIP_MARGIN);
    }

    /**
     * <pre>
     * System.out.println(MultilineStringLiteral.stripMargin(/**
     *       |Wow, we finally have
     *        multiline strings in
     *       |Java! HOOO!
     * &#42;/));
     * </pre>
     * prints:
     * <pre>
     * Wow, we finally have
     *        multiline strings in
     * Java! HOOO!
     * </pre>
     *
     * @return the string inside the java multi line comment and strips the margins delimited by '|',
     * similar to groovy or scala. Comment can start with /* or /**
     */
    public static String stripMargin() {
        return _newString(STRIP_MARGIN);
    }

    private static String _newString(final Function<String, String> stripLine) {
        final StackTraceElement stackTraceElement = new Exception().getStackTrace()[2];
        final String resource = stackTraceElement.getClassName().split("\\$")[0].replace('.', '/') + ".java";
        final InputStream in = MultilineStringLiteral.class.getClassLoader().getResourceAsStream(resource);
        if (in == null) {
            throw new MultilineStringLiteralException("Resource '" + resource + "' not found in classpath",
                    stackTraceElement);
        }
        try {
            return readMultiLineComment(in, stackTraceElement.getLineNumber(), stripLine);
        } catch (final Exception e) {
            throw new MultilineStringLiteralException(stackTraceElement, e);
        } finally {
            try {
                in.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readMultiLineComment(final InputStream is, final int lineNum,
                                               final Function<String, String> stripLine) throws IOException {
        final LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        final StringBuilder buff = new StringBuilder();
        String line = reader.readLine();
        // moving to the target line of the stack
        for (; line != null; line = reader.readLine()) {
            if (reader.getLineNumber() >= lineNum) {
                break;
            }
        }
        // getting the first line of the comment
        for (; line != null; line = reader.readLine()) {
            final int endOfMethodIdx = line.indexOf(");");
            final int startCommentIdx = line.indexOf(START_COMMENT);
            if (startCommentIdx == -1 && endOfMethodIdx <= startCommentIdx) {
                throw new RuntimeException("String definition not found");
            }
            if (startCommentIdx >= 0) {
                // removing what is out of the comment
                line = line.substring(startCommentIdx + 2, line.length());
                if (line.startsWith(END_COMMENT)) {
                    return ""; // comment opened and immediately closed
                }
                if (line.startsWith("*")) {
                    line = line.substring(1);
                }
                break;
            }
        }
        // continue until the end of the comment
        for (; line != null; line = reader.readLine()) {
            line = stripLine.apply(line);
            final int idx = line.indexOf(END_COMMENT);
            if (idx >= 0) {
                // removing what is out of the comment
                buff.append(line.substring(0, idx));
                break;
            }
            buff.append(line);
            buff.append('\n');
        }
        return buff.toString();
    }

}
