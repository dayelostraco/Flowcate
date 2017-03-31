package com.notiflowcate.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;

/**
 * Custom Orika Type converter for {@link LocalDateTime}
 */
public class LocalDateTimeToLocalDateTime extends BidirectionalConverter<LocalDateTime, LocalDateTime> {

    /**
     * Converts a {@link LocalDateTime} to another {@link LocalDateTime}. Yeah, yeah... this is ridiculous
     * but required for Orika to handle the new Java 1.8 time API.
     *
     * @param source {@link LocalDateTime}
     * @param destinationType Orika Type wrapped {@link LocalDateTime}
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType) {
        return source;
    }

    /**
     * Converts a {@link LocalDateTime} to another {@link LocalDateTime}. Yeah, yeah... this is ridiculous
     * but required for Orika to handle the new Java 1.8 time API.
     *
     * @param source {@link LocalDateTime}
     * @param destinationType Orika Type wrapped {@link LocalDateTime}
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType) {
        return source;
    }
}
