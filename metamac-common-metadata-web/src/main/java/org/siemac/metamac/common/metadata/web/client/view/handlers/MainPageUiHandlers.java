package org.siemac.metamac.common.metadata.web.client.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MainPageUiHandlers extends UiHandlers {

    void openHelpUrl();
    void closeSession();

    void onNavigationPaneSectionHeaderClicked(String name);
    void onNavigationPaneSectionClicked(String name);
}
