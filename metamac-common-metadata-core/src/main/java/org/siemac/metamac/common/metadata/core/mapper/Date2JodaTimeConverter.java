package org.siemac.metamac.common.metadata.core.mapper;

import java.util.Date;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.joda.time.DateTime;

public class Date2JodaTimeConverter implements CustomConverter {

    // TODO JODA TIME CONVERTERS, ADD TIMEZONE!!!!!!!!!

    @SuppressWarnings("rawtypes")
    public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

        if (source == null) {
            return null;
        }

        if (source instanceof Date) {
            return new DateTime(source);
        } else if (source instanceof DateTime) {

            // Calendar calendar = ((AbstractDateTime) source).toCalendar(Locale.getDefault());
            // Date date = ((AbstractInstant) source).toDate();

            // return date;
            return ((DateTime) source).toDate();

            // Calendar result;
            // if (destination == null) {
            // result = Calendar.getInstance();
            // }
            // else {
            // result = (Calendar) destination;
            // }
            // result.setTimeInMillis(((DateTime) source).getMillis());
            // return result;
        } else {
            throw new MappingException("Converter Date2JodaTimeConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
        }

    }
}