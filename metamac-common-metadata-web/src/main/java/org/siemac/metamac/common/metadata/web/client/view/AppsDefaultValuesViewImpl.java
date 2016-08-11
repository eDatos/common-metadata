package org.siemac.metamac.common.metadata.web.client.view;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.AppConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.AppsDefaultValuesView;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.ViewActionHandlers;
import org.siemac.metamac.common.metadata.web.client.utils.AppDataConfigClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsDefaultValuesUiHandlers;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsSystemPropertiesUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.AppPropertyPanel;
import org.siemac.metamac.common.metadata.web.client.widgets.AppsConfigurationsListGrid;
import org.siemac.metamac.common.metadata.web.client.widgets.view.DefaultValuesSearchSectionStack;
import org.siemac.metamac.common.metadata.web.client.widgets.view.SystemPropertiesSearchSectionStack;
import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AppsDefaultValuesViewImpl extends ViewWithUiHandlers<AppsDefaultValuesUiHandlers> implements AppsDefaultValuesView {

    private VLayout                    panel;
    private DefaultValuesSearchSectionStack searchSectionStack;
    private AppsConfigurationsListGrid listGrid;
    private AppPropertyPanel           detailPanel;

    public AppsDefaultValuesViewImpl() {

        listGrid = new AppsConfigurationsListGrid();

        listGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                AppConfigurationRecord record = (AppConfigurationRecord) event.getRecord();
                detailPanel.selectDtoForView(record.getDto());
            }
        });

        detailPanel = createDetailPanel();
        createSearchSectionStack();

        panel = new VLayout();
        panel.addMember(searchSectionStack);
        panel.addMember(listGrid);
        panel.addMember(detailPanel);
    }
    
    private void createSearchSectionStack() {
        searchSectionStack = new DefaultValuesSearchSectionStack();
    }
    
    @Override
    public DataConfigurationWebCriteria getDataConfigurationWebCriteria() {
        if (searchSectionStack == null) {
            return new DataConfigurationWebCriteria();
        }
        return searchSectionStack.getDataConfigurationWebCriteria();
    }

    private AppPropertyPanel createDetailPanel() {
        AppPropertyPanel propertyPanel = new AppPropertyPanel() {

            @Override
            protected void retrieveConcepts(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
                getUiHandlers().retrieveConcepts(itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveConceptSchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults) {
                getUiHandlers().retrieveConceptSchemes(itemSchemeRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveCodelists(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults, ViewActionHandlers viewHandler) {
                getUiHandlers().retrieveCodelists(itemSchemeRestCriteria, firstResult, maxResults, viewHandler);
            }

            @Override
            protected void retrieveCodes(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
                getUiHandlers().retrieveCodes(itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void saveProperty(DataConfigurationDto configurationProperty) {
                getUiHandlers().saveDataCondiguration(configurationProperty);
            }
        };
        propertyPanel.setCanEdit(AppDataConfigClientSecurityUtils.canEditDefaultValues());
        return propertyPanel;
    }

    @Override
    public void setAppConfigurations(List<DataConfigurationDto> properties) {
        listGrid.setData(RecordUtils.getAppConfigurationRecords(properties));
    }

    @Override
    public void selectAppConfiguration(DataConfigurationDto appConfiguration) {
        if (appConfiguration != null) {
            detailPanel.selectDtoForView(appConfiguration);
        } else {
            detailPanel.hideFormLayout();
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
    
    @Override
    public void setUiHandlers(AppsDefaultValuesUiHandlers handlers) {
        super.setUiHandlers(handlers);
        searchSectionStack.setUiHandlers(handlers);
    }

    // ASYNC ACTION RESULTS

    @Override
    public void setConcepts(ExternalItemsResult result) {
        detailPanel.setConcepts(result);
    }

    @Override
    public void setConceptSchemes(ExternalItemsResult result) {
        detailPanel.setConceptSchemes(result);
    }

    @Override
    public void setCodes(ExternalItemsResult result) {
        detailPanel.setCodes(result);
    }

    @Override
    public void setCodelists(ExternalItemsResult result, ViewActionHandlers viewHandler) {
        detailPanel.setCodelists(result, viewHandler);
    }

}
