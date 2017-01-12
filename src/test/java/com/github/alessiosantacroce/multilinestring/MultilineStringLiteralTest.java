package com.github.alessiosantacroce.multilinestring;

import org.junit.Test;

import java.util.function.Supplier;

import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.S;
import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.newString;
import static org.assertj.core.api.Assertions.assertThat;

public class MultilineStringLiteralTest {

    @Test
    public void defineEmptyString() {
        // given

        // when
        final String s = newString(/**/);

        // then
        assertThat(s).isEqualTo("");
    }

    @Test
    public void defineSingleLineString() {
        // given

        // when
        final String s = S(/* xxx */);

        // then
        assertThat(s).isEqualTo(" xxx ");
    }

    @Test
    public void defineMultiLineJsonString() {
        // given

        // when
        final String s = newString(/*
            {
                "firstName": "John",
                "address": {
                    "streetAddress": "21 2nd Street",
                    "city": "New York"
                }
            }
        */);

        // then
        assertThat(s.trim()).isEqualTo("{\n" +
                "                \"firstName\": \"John\",\n" +
                "                \"address\": {\n" +
                "                    \"streetAddress\": \"21 2nd Street\",\n" +
                "                    \"city\": \"New York\"\n" +
                "                }\n" +
                "            }");
    }

    @Test
    public void defineMultiLineXMLString() {
        // given

        // when
        final String s = S(/*
            <person id="42">
                <firstName>John</firstName>
                <address>
                    <streetAddress>21 2nd Street</streetAddress>
                    <city>New York</city>
                </address>
            </person>
        */);

        // then
        assertThat(s.trim()).isEqualTo("<person id=\"42\">\n" +
                "                <firstName>John</firstName>\n" +
                "                <address>\n" +
                "                    <streetAddress>21 2nd Street</streetAddress>\n" +
                "                    <city>New York</city>\n" +
                "                </address>\n" +
                "            </person>");
    }

    @Test
    public void defineStringInInnerClass() {
        // given
        final Object obj = new Object() {
            @Override
            public String toString() {
                return S(/* yyy */);
            }
        };

        // when
        final String s = obj.toString();

        // then
        assertThat(s).isEqualTo(" yyy ");
    }

    @Test
    public void defineStringInFunction() {
        // given
        final Supplier<String> supplier = () -> S(/* zzz */);

        // when
        final String s = supplier.get();

        // then
        assertThat(s).isEqualTo(" zzz ");
    }
}
