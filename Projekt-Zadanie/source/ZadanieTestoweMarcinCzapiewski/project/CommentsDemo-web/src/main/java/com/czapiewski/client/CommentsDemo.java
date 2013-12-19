package com.czapiewski.client;

import com.czapiewski.client.events.RefreshCommentsTableEvent;
import com.czapiewski.client.ui.AboutPanel;
import com.czapiewski.client.ui.CommentsListPanel;
import com.czapiewski.client.ui.MyDialog;
import com.czapiewski.client.ui.NewCommentsPanel;
import com.czapiewski.client.ui.UserCommentsPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CommentsDemo implements EntryPoint {

	/**
	 * Tworzy zdalny serwis proxy do komunikacji z częścią serwerowa.
	 */
	private final CommentsServiceAsync commentsService = GWT.create(CommentsService.class);

	private final Messages messages = GWT.create(Messages.class);

	private final VerticalPanel mainPanel = new VerticalPanel();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				new MyDialog("Błąd!", e.getMessage()).show();
			}
		});

		mainPanel.setSize("100%", "100%");
		mainPanel.setSpacing(30);
		mainPanel.add(onInitializeTabPanel());

		// Associate the Main panel with the HTML host page.
		RootPanel.get().add(mainPanel);

		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading"));

		getSchedulerInterval();
	}

	/**
	 * Inicjalizuje tab panel
	 * 
	 * @return
	 */
	private TabPanel onInitializeTabPanel() {
		TabPanel tabPanel = new TabPanel();
		tabPanel.setSize("100%", "100%");
		tabPanel.setAnimationEnabled(true);

		final CommentsListPanel commentsListPanel = new CommentsListPanel();

		tabPanel.add(new NewCommentsPanel(), messages.commentsTabName1());
		tabPanel.add(new UserCommentsPanel(), messages.commentsTabName2());
		tabPanel.add(commentsListPanel, messages.commentsTabName3());
		tabPanel.add(new AboutPanel(), messages.commentsTabName4());

		tabPanel.selectTab(0);

		tabPanel.addSelectionHandler(new SelectionHandler() {
			public void onSelection(SelectionEvent event) {
				if ((Integer) event.getSelectedItem() == 2) {
					commentsListPanel.getCommentsListFromServer();
				}

			}
		});

		return tabPanel;
	}

	/**
	 * Pobiera interwał czasowy, po którym odświeżana ma być list komentarzy
	 */
	private void getSchedulerInterval() {
		commentsService.getSchedulerInterval(new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				new MyDialog(messages.error(), caught.getMessage()).show();
			}

			public void onSuccess(Integer result) {
				startScheduler(result);
			}
		});
	}

	/**
	 * Inicjalizuje schedulera
	 * 
	 * @param schedulerInterval
	 */
	private void startScheduler(Integer schedulerInterval) {
		if (schedulerInterval == null || schedulerInterval <= 0) {
			new MyDialog(messages.info(), messages.noSchedulerDefFound()).show();
		} else {
			new Timer() {
				@Override
				public void run() {
					NewCommentsPanel.EVENT_BUS.fireEvent(new RefreshCommentsTableEvent());
					CommentsListPanel.EVENT_BUS.fireEvent(new RefreshCommentsTableEvent());
				}
			}.scheduleRepeating(schedulerInterval);
		}
	}
}
