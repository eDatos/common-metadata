package org.siemac.metamac.common.metadata.web.client.widgets.view;

import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;

import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public abstract class CommonMetadataSearchSectionStack extends SearchSectionStack {

    public CommonMetadataSearchSectionStack() {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                retrieveResources();
            }

        });

        addSearchItemKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (StringUtils.equalsIgnoreCase(event.getKeyName(), CommonWebConstants.ENTER_KEY)) {
                    retrieveResources();
                }
            }
        });
    }
    
    abstract void retrieveResources();

    public DataConfigurationWebCriteria getDataConfigurationWebCriteria() {
        DataConfigurationWebCriteria dataConfigurationWebCriteria = new DataConfigurationWebCriteria();
        dataConfigurationWebCriteria.setCriteria(getSearchCriteria());
        return dataConfigurationWebCriteria;
    }

}
