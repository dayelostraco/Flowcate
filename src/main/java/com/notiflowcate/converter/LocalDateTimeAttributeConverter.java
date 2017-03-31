package com.notiflowcate.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Custom Hibernate converters for {@link Timestamp} and {@link LocalDateTime}.
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Converts a MySQL {@link Timestamp} to a {@link LocalDateTime}.
     *
     * @param locDateTime {@link LocalDateTime}
     * @return Timestamp
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
    	return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    /**
     * Converts a MySQL {@link Timestamp} to a {@link LocalDateTime}.
     *
     * @param sqlTimestamp {@link Timestamp}
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
}
