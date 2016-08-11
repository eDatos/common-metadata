package org.siemac.metamac.common.metadata.web.client.widgets;

import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.ViewActionHandlers;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public abstract class AppPropertyNoExternalItemPanel extends AppPropertyPanel {

    @Override
    protected void retrieveConcepts(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
        // NOT USED
    }

    @Override
    protected void retrieveConceptSchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults) {
        // NOT USED
    }

    @Override
    protected void retrieveCodelists(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults, ViewActionHandlers viewHandler) {
        // NOT USED
    }

    @Override
    protected void retrieveCodes(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
        // NOT USED
    }
}
