package org.siemac.metamac.common.metadata.core.serviceapi;

import java.util.ArrayList;
import java.util.List;


public class CommonMetadataTestConfiguration {

    public static String getDataSetFile() {
        return "dbunit/CommonMetadataServiceTest.xml";
    }

    public static  List<String> getTablesToRemoveContent() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_CONFIGURATIONS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_LOCALISED_STRINGS");
        tables.add("TB_INTERNATIONAL_STRINGS");
        return tables;
    }

    public static  List<String> getSequencesToRestart() {
        List<String> sequences = new ArrayList<String>();
        sequences.add("SEQ_EXTERNAL_ITEMS");
        sequences.add("SEQ_L10NSTRS");
        sequences.add("SEQ_I18NSTRS");
        sequences.add("SEQ_CONFIGURATION");
        return sequences;
    }
}
