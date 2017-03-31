package com.notiflowcate.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Custom Orika converter for {@link Date} and {@link LocalDateTime} making sure to use UTC as
 * the {@link ZoneOffset}.
 */
public class LocalDateTimeToDateConverter extends BidirectionalConverter<Date, LocalDateTime> {

    /**
     * Converts a {@link Date} to a {@link LocalDateTime}.
     *
     * @param source {@link Date}
     * @param destinationType Orika Type wrapper for {@link LocalDateTime}
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convertTo(Date source, Type<LocalDateTime> destinationType) {
        Instant instant = Instant.ofEpochMilli(source.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    /**
     * Converts a {@link LocalDateTime} to an Orika wrapped {@link Date}
     *
     * @param source {@link LocalDateTime}
     * @param destinationType Orika Type wrapper for {@link Date}
     * @return Date
     */
    @Override
    public Date convertFrom(LocalDateTime source, Type<Date> destinationType) {
        Instant instant = source.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }
}
