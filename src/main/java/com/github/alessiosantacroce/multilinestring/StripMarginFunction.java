package com.github.alessiosantacroce.multilinestring;

import java.util.function.Function;

class StripMarginFunction implements Function<String, String> {
    private final char marginSeparator;

    StripMarginFunction() {
        this('|');
    }

    private StripMarginFunction(final char marginSeparator) {
        this.marginSeparator = marginSeparator;
    }

    @Override
    public String apply(final String line) {
        for (int i = 0; i < line.length(); i++) {
            final char c = line.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            } else if (c == marginSeparator) {
                return line.substring(i + 1);
            }
            break;
        }
        return line;
    }
}
