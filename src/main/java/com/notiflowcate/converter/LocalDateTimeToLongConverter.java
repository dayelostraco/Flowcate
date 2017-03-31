package com.notiflowcate.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Custom Orika Converter for Long time in milliseconds to {@link LocalDateTime} making sure to use UTC
 * as the {@link ZoneOffset}.
 */
public class LocalDateTimeToLongConverter extends BidirectionalConverter<Long, LocalDateTime> {

    /**
     * Converts a Long UTC time in milliseconds to an Orika Type wrapped {@link LocalDateTime} with
     *
     * @param source Long UTC time in milliseconds
     * @param destinationType Orika Type wrapped {@link LocalDateTime} using the UTC {@link ZoneOffset}.
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convertTo(Long source, Type<LocalDateTime> destinationType) {
        Instant instant = Instant.ofEpochMilli(source);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    /**
     * Converts a {@link LocalDateTime} to a Long UTC time in milliseconds using the UTC {@link ZoneOffset}.
     *
     * @param source {@link LocalDateTime}
     * @param destinationType Orika Type wrapped Long UTC time in milliseconds.
     * @return Long UTC time in milliseconds.
     */
    @Override
    public Long convertFrom(LocalDateTime source, Type<Long> destinationType) {
        return source.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
