package com.github.alessiosantacroce.multilinestring;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StripMarginFunctionTest {
    @Test
    public void stripMarginTest() {
        // given
        final StripMarginFunction smf = new StripMarginFunction();

        // when

        // then
        assertThat(smf.apply("")).isEqualTo("");
        assertThat(smf.apply("|")).isEqualTo("");
        assertThat(smf.apply("  |")).isEqualTo("");
        assertThat(smf.apply("\t|")).isEqualTo("");
        assertThat(smf.apply("\t|aaa")).isEqualTo("aaa");
        assertThat(smf.apply("  |222")).isEqualTo("222");
        assertThat(smf.apply("  ||33")).isEqualTo("|33");
        assertThat(smf.apply("  | 44")).isEqualTo(" 44");
        assertThat(smf.apply("  |\t55")).isEqualTo("\t55");
    }
}
