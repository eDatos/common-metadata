package org.siemac.metamac.common_metadata.rest.internal;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CommonMetadataNamespacePrefixMapper extends NamespacePrefixMapper {

    private static final String COMMON_METADATA_V_1_0_URI    = "http://www.siemac.org/metamac/common-metadata/rest/internal/v1.0/domain";
    private static final String COMMON_METADATA_V_1_0_PREFIX = "cmetadata";

    private static final String COMMON_V_1_0_URI             = "http://www.siemac.org/metamac/rest/common/v1.0/domain";
    private static final String COMMON_V_1_0_PREFIX          = "common";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (COMMON_METADATA_V_1_0_URI.equals(namespaceUri)) {
            return COMMON_METADATA_V_1_0_PREFIX;
        } else if (COMMON_V_1_0_URI.equals(namespaceUri)) {
            return COMMON_V_1_0_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{COMMON_METADATA_V_1_0_URI, COMMON_V_1_0_URI};
    }

}