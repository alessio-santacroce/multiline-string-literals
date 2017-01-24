package com.github.alessiosantacroce.multilinestring;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.Arrays;

import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MultilineStringLiteralTest {

    @Test
    public void defineMultiLineJsonString() throws ParseException {
        // given
        final JSONParser parser = new JSONParser();

        final JSONObject person = new JSONObject();
        person.put("name", "John");
        person.put("hobbies", Arrays.asList("travel", "make pizza"));
        final JSONObject address = new JSONObject();
        address.put("streetAddress", "21 2nd Street");
        address.put("city", "New York");
        person.put("address", address);

        // when

        // then
        assertThat(parser.parse(newString(/*
         {
            "name": "John",
            "hobbies": ["travel", "make pizza"],
            "address": {
              "streetAddress": "21 2nd Street",
              "city": "New York"
            }
          }
         */))).isEqualTo(person);
        assertThat(parser.parse(newString(/**
         {
         "name": "John",
         "hobbies": ["travel", "make pizza"],
         "address": {
         "streetAddress": "21 2nd Street",
         "city": "New York"
         }
         }
         */))).isEqualTo(person);
        assertThat(parser.parse(S(/*
         {
            "name": "John",
            "hobbies": ["travel", "make pizza"],
             "address": {
                 "streetAddress": "21 2nd Street",
                  "city": "New York"
             }
         }
         */))).isEqualTo(person);
        assertThat(parser.parse(S(/**
         {
         "name": "John",
         "hobbies": ["travel", "make pizza"],
         "address": {
         "streetAddress": "21 2nd Street",
         "city": "New York"
         }
         }
         */))).isEqualTo(person);
        assertThat(parser.parse(stripMargin(/*
         | {
         |   "name": "John",
         |   "hobbies": ["travel", "make pizza"],
         |   "address": {
         |     "streetAddress": "21 2nd Street",
         |     "city": "New York"
         |   }
         | }
         */))).isEqualTo(person);
        assertThat(parser.parse(stripMargin(/**
         | {
         |   "name": "John",
         |   "hobbies": ["travel", "make pizza"],
         |   "address": {
         |     "streetAddress": "21 2nd Street",
         |     "city": "New York"
         |   }
         | }
         */))).isEqualTo(person);
    }

    @Test
    public void defineMultiLineString() throws ParseException {
        // given
        final String expected = "\n" +
                "         Wow, we finally have\n" +
                "         multiline strings in\n" +
                "         Java! HOOO!";

        // when

        // then
        assertThat(newString(/*
         Wow, we finally have
         multiline strings in
         Java! HOOO!*/)).isEqualTo(expected);
        assertThat(newString(/**
         Wow, we finally have
         multiline strings in
         Java! HOOO!*/)).isEqualTo(expected);
        assertThat(S(/*
         Wow, we finally have
         multiline strings in
         Java! HOOO!*/)).isEqualTo(expected);
        assertThat(S(/**
         Wow, we finally have
         multiline strings in
         Java! HOOO!*/)).isEqualTo(expected);
        assertThat(stripMargin(/*
         |         Wow, we finally have
         |         multiline strings in
         |         Java! HOOO!*/)).isEqualTo(expected);
        assertThat(stripMargin(/**
         |         Wow, we finally have
         |         multiline strings in
         |         Java! HOOO!*/)).isEqualTo(expected);

    }

    @Test
    public void defineEmptyString() {
        // given

        // when

        // then
        assertThat(newString(/**/)).isEqualTo("");
        assertThat(newString(/***/)).isEqualTo("");
        assertThat(S(/**/)).isEqualTo("");
        assertThat(S(/***/)).isEqualTo("");
        assertThat(stripMargin(/**/)).isEqualTo("");
        assertThat(stripMargin(/***/)).isEqualTo("");
    }

    @Test
    public void defineSingleLineString() {
        // given

        // when

        // then
        assertThat(newString(/*111*/)).isEqualTo("111");
        assertThat(newString(/**222*/)).isEqualTo("222");
        assertThat(S(/*333*/)).isEqualTo("333");
        assertThat(S(/**444*/)).isEqualTo("444");
        assertThat(stripMargin(/*555*/)).isEqualTo("555");
        assertThat(stripMargin(/**666*/)).isEqualTo("666");
    }

    @Test(expected = MultilineStringLiteralException.class)
    public void ifCommentIsMissingRaiseException_S() {
        S();
    }

    @Test(expected = MultilineStringLiteralException.class)
    public void ifCommentIsMissingRaiseException_newString() {
        newString();
    }

    @Test(expected = MultilineStringLiteralException.class)
    public void ifCommentIsMissingRaiseException_stripMargin() {
        stripMargin();
    }

    @Test
    public void defineStringInInnerClass() {
        // given

        // when

        // then
        assertThat(new Object() {
            @Override
            public String toString() {
                return S(/* aaa */);
            }
        }.toString()).isEqualTo(" aaa ");
        assertThat(new Object() {
            @Override
            public String toString() {
                return newString(/* bbb */);
            }
        }.toString()).isEqualTo(" bbb ");
        assertThat(new Object() {
            @Override
            public String toString() {
                return stripMargin(/*|ccc */);
            }
        }.toString()).isEqualTo("ccc ");
    }
}
