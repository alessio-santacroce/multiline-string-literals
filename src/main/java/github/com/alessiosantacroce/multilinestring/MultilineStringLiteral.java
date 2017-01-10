package github.com.alessiosantacroce.multilinestring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * For more details see <a href="https://github.com/alessio-santacroce/multiline-string-literals">https://github.com/alessio-santacroce/multiline-string-literals</a>
 */
public class MultilineStringLiteral {

    private MultilineStringLiteral() {
    }

    /**
     * Returns the string inside the java multi line comment.
     * E.g.:
     * <pre>
     *   {@code
     *   import static github.com.alessiosantacroce.multilinestring.MultilineStringLiteral.newString;
     *     ...
     *   System.out.println(newString(/*
     *   {
     *      "firstName": "John",
     *      "address": {
     *         "streetAddress": "21 2nd Street",
     *         "city": "New York"
     *      }
     *   }
     *   &#42;/));
     * </pre>
     * prints:
     * <pre>
     *   {
     *      "firstName": "John",
     *      "address": {
     *         "streetAddress": "21 2nd Street",
     *         "city": "New York"
     *      }
     *   }
     * </pre>
     *
     * @return the string inside the java multi line comment.
     */
    public static String newString() {
        return _newString();
    }

    /**
     * short alias for newString
     */
    public static String S() {
        return _newString();
    }

    private static String _newString() {
        final StackTraceElement stackTraceElement = new Exception().getStackTrace()[2];
        final String resource = stackTraceElement.getClassName().split("\\$")[0].replace('.', '/') + ".java";
        final InputStream in = MultilineStringLiteral.class.getClassLoader().getResourceAsStream(resource);
        if (in == null) {
            throw new RuntimeException("Resource '" + resource + "' not found in classpath");
        }
        try {
            return readMultiLineComment(in, stackTraceElement.getLineNumber());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readMultiLineComment(final InputStream is, final int lineNum) throws IOException {
        final LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        final StringBuilder buff = new StringBuilder();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (reader.getLineNumber() < lineNum) {
                continue;
            }
            buff.append(line);
            if (line.contains("*/")) {
                break;
            }
            buff.append('\n');
        }
        return buff.substring(buff.indexOf("/*") + 2, buff.indexOf("*/"));
    }
}
