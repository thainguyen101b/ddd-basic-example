package com.example.ecommerce.core;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import com.fasterxml.jackson.databind.Module;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryFormats;
import java.io.IOException;

@Configuration
public class JacksonMoneyConfig {

    @Bean
    public Module moneyModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(MonetaryAmount.class, new MonetaryAmountSerializer());
        module.addDeserializer(MonetaryAmount.class, new MonetaryAmountDeserializer());

        return module;
    }

    static class MonetaryAmountSerializer extends StdSerializer<MonetaryAmount> {
        public MonetaryAmountSerializer() {
            super(MonetaryAmount.class);
        }

        @Override
        public void serialize(MonetaryAmount amount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (amount != null) {
                jsonGenerator.writeString(MonetaryFormats.getAmountFormat(LocaleContextHolder.getLocale()).format(amount));
            } else {
                jsonGenerator.writeNull();
            }
        }
    }

    static class MonetaryAmountDeserializer extends StdDeserializer<MonetaryAmount> {
        public MonetaryAmountDeserializer() {
            super(MonetaryAmount.class);
        }

        @Override
        public MonetaryAmount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            String value = jsonParser.getText();
            if (value == null || value.isEmpty()) {
                return null;
            }
            return Money.parse(value, MonetaryFormats.getAmountFormat(LocaleContextHolder.getLocale()));
        }
    }

}
