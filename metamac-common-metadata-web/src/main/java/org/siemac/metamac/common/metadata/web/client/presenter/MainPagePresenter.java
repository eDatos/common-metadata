package org.siemac.metamac.common.metadata.web.client.presenter;

import org.siemac.metamac.common.metadata.web.client.NameTokens;
import org.siemac.metamac.common.metadata.web.client.view.handlers.MainPageUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.MasterHead;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class MainPagePresenter extends Presenter<MainPagePresenter.MainPageView, MainPagePresenter.MainPageProxy> implements MainPageUiHandlers {

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;

	private static MasterHead masterHead;

	@ProxyStandard
	@NameToken(NameTokens.mainPage)
	public interface MainPageProxy extends Proxy<MainPagePresenter>, Place {

	}
	
	public interface MainPageView extends View, HasUiHandlers<MainPageUiHandlers> {
		MasterHead getMasterHead();
	}

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 * Is used to define a type to use in child presenters when you want to
	 * include them inside this page.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContent = new Type<RevealContentHandler<?>>();

	@Inject
	public MainPagePresenter(EventBus eventBus, MainPageView view, MainPageProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		MainPagePresenter.masterHead = getView().getMasterHead();
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public void onNavigationPaneSectionHeaderClicked(String place) {
		if (place.length() != 0) {
			PlaceRequest placeRequest = new PlaceRequest(place);
			placeManager.revealPlace(placeRequest);
		}
	}

	@Override
	public void onNavigationPaneSectionClicked(String place) {
		if (place.length() != 0) {
			PlaceRequest placeRequest = new PlaceRequest(place);
			placeManager.revealPlace(placeRequest);
		}
	}

	public static MasterHead getMasterHead() {
		return masterHead;
	}

}