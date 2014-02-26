package org.siemac.metamac.common.metadata.web.shared.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.common.metadata.web.client.enums.AppConfPropertyValueType;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;

public class AppConfigurationsUtils {

    private static final Map<String, AppConfPropertyValueType> propertyKeyExternalTypeMappings;

    private static final Set<String>                           potentialUrlPrefixes = new HashSet<String>(Arrays.asList("http://", "https://"));

    static {
        propertyKeyExternalTypeMappings = new HashMap<String, AppConfPropertyValueType>();
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DEFAULT_CODELIST_GEOGRAPHICAL_GRANULARITY_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CODELIST);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DEFAULT_CODELIST_TEMPORAL_GRANULARITY_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CODELIST);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DEFAULT_CODELIST_LANGUAGES_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CODELIST);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DEFAULT_CODE_LANGUAGE_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CODE);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DSD_PRIMARY_MEASURE_DEFAULT_CONCEPT_ID_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CONCEPT);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DSD_TIME_DIMENSION_OR_ATTRIBUTE_DEFAULT_CONCEPT_ID_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CONCEPT);
        propertyKeyExternalTypeMappings.put(ConfigurationConstants.DSD_MEASURE_DIMENSION_OR_ATTRIBUTE_DEFAULT_CONCEPT_ID_URN, AppConfPropertyValueType.EXTERNAL_ITEM_CONCEPT);
    }

    public static AppConfPropertyValueType guessPropertyViewValueType(String key, String value) {
        AppConfPropertyValueType type = guessPropertyValueTypeByKey(key);
        if (type == null) {
            type = guessPropertyValueTypeByValue(value);
        }
        return type;
    }

    public static AppConfPropertyValueType guessPropertyEditionValueType(String key, String value) {
        AppConfPropertyValueType type = guessPropertyValueTypeByKey(key);
        if (type == null) {
            return AppConfPropertyValueType.STRING;
        }
        return type;
    }

    public static AppConfPropertyValueType guessPropertyValueTypeByKey(String key) {
        return propertyKeyExternalTypeMappings.get(key);
    }

    private static AppConfPropertyValueType guessPropertyValueTypeByValue(String value) {
        if (value == null) {
            return AppConfPropertyValueType.STRING;
        }
        if (startsWithAny(value, potentialUrlPrefixes)) {
            return AppConfPropertyValueType.LINK;
        }
        return AppConfPropertyValueType.STRING;
    }

    private static boolean startsWithAny(String string, Collection<String> prefixes) {
        for (String prefix : prefixes) {
            if (string.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}