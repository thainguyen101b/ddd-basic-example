package com.example.ecommerce.core;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MonetaryAmountTest {

    MonetaryAmountConverter converter = new MonetaryAmountConverter();

    @Test
    void handlesNullValues() {

        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void handlesSimpleValue() {

        assertThat(converter.convertToDatabaseColumn(Money.of(1.23, "EUR"))).isEqualTo("EUR 1.23");
        assertThat(converter.convertToEntityAttribute("EUR 1.23")).isEqualTo(Money.of(1.23, "EUR"));
    }
    
    @Test
    void handlesVieValue() {
        assertThat(converter.convertToDatabaseColumn(Money.of(60000, "VND"))).isEqualTo("VND 60000");
        assertThat(converter.convertToEntityAttribute("VND 60000")).isEqualTo(Money.of(60000, "VND"));
    }

    @Test
    void handlesNegativeValues() {

        assertThat(converter.convertToDatabaseColumn(Money.of(-1.20, "USD"))).isEqualTo("USD -1.2");
        assertThat(converter.convertToEntityAttribute("USD -1.2")).isEqualTo(Money.of(-1.20, "USD"));
    }

    @Test
    void doesNotRoundValues() {
        assertThat(converter.convertToDatabaseColumn(Money.of(1.23456, "EUR"))).isEqualTo("EUR 1.23456");
    }

    @Test
    void doesNotFormatLargeValues() {
        assertThat(converter.convertToDatabaseColumn(Money.of(123456, "EUR"))).isEqualTo("EUR 123456");
    }

    @Test
    void deserializesFormattedValues() {
        assertThat(converter.convertToEntityAttribute("EUR 123,456.78")).isEqualTo(Money.of(123456.78, "EUR"));
    }

}
