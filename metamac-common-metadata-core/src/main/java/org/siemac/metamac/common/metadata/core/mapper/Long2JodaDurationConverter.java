package org.siemac.metamac.common.metadata.core.mapper;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.joda.time.Duration;

public class Long2JodaDurationConverter implements CustomConverter {

    // TODO JODA TIME CONVERTERS, ADD TIMEZONE!!!!!!!!!
    @SuppressWarnings("rawtypes")
    public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

        if (source == null) {
            return null;
        }

        if (source instanceof Long) {
            return new Duration(source);
        } else if (source instanceof org.joda.time.Duration) {

            return ((org.joda.time.Duration) source).getMillis();
        } else {
            throw new MappingException("Converter Long2JodaDurationConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
        }

    }
}