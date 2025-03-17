package com.example.ecommerce.core;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Converter
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {

    private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

    @Override
    public String convertToDatabaseColumn(MonetaryAmount amount) {
        return amount == null ? null
                : String.format("%s %s", amount.getCurrency().toString(), amount.getNumber().toString());
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String source) {
        if (source == null)
            return null;
        try {
            return Money.parse(source);
        } catch (RuntimeException e) {
            try {
                return Money.parse(source, FORMAT);
            } catch (RuntimeException fallbackE) {
                throw e;
            }
        }
    }
}
