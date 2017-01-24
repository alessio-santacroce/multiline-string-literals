package com.github.alessiosantacroce.multilinestring;

class MultilineStringLiteralException extends RuntimeException {

    MultilineStringLiteralException(final String message, final StackTraceElement stackTraceElement) {
        this(message, stackTraceElement, null);
    }

    MultilineStringLiteralException(final StackTraceElement stackTraceElement,
                                    final Throwable cause) {
        super("Error defining string at " + stackTraceElement.toString() + ": " + cause.getMessage(), cause);
    }

    private MultilineStringLiteralException(final String message, final StackTraceElement stackTraceElement,
                                            final Throwable cause) {
        super("Error defining string at " + stackTraceElement.toString() + ": " + message, cause);
    }
}
