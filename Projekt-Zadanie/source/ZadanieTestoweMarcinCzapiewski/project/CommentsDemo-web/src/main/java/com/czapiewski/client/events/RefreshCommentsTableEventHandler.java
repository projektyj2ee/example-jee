package com.czapiewski.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface RefreshCommentsTableEventHandler extends EventHandler {
	/**
	 * Event wywolywane w celu automatycznego odswiezenia tabeli z komentarzami
	 * 
	 * @param event
	 */
	void onRefreshCommentsTable(RefreshCommentsTableEvent event);
}
