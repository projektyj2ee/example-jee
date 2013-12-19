package com.czapiewski.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event wywolywany w celu odświeżenie tabeli z listą komentarzy
 * 
 * @author Marcin
 * 
 */
public class RefreshCommentsTableEvent extends GwtEvent<RefreshCommentsTableEventHandler> {
	private static final Type<RefreshCommentsTableEventHandler> TYPE = new Type<RefreshCommentsTableEventHandler>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RefreshCommentsTableEventHandler> getAssociatedType() {
		return TYPE;
	}

	public static com.google.gwt.event.shared.GwtEvent.Type<RefreshCommentsTableEventHandler> getType() {
		return TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
	 * .EventHandler)
	 */
	@Override
	protected void dispatch(RefreshCommentsTableEventHandler handler) {
		handler.onRefreshCommentsTable(this);
	}

}
