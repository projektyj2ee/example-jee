package com.czapiewski.client.ui;

import java.util.List;

import com.czapiewski.client.CommentsService;
import com.czapiewski.client.CommentsServiceAsync;
import com.czapiewski.client.Messages;
import com.czapiewski.client.events.RefreshCommentsTableEvent;
import com.czapiewski.client.events.RefreshCommentsTableEventHandler;
import com.czapiewski.common.dto.CommentsDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Kontener dla zakładki 'Ostatnie komentarze'
 * 
 * @author Marcin
 * 
 */
public class CommentsListPanel extends VerticalPanel implements RefreshCommentsTableEventHandler {

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	/**
	 * Tworzy zdalny serwis proxy do komunikacji z częścią serwerowa.
	 */
	private final CommentsServiceAsync commentsService = GWT.create(CommentsService.class);

	private final Messages messages = GWT.create(Messages.class);

	private final FlexTable commentsTable = new FlexTable();
	
	private Integer schedulerInterval = new Integer(0);

	/**
	 * Konstruktor
	 */
	public CommentsListPanel() {
		super();

		CommentsListPanel.EVENT_BUS.addHandler(RefreshCommentsTableEvent.getType(), this);

		this.setSize("100%", "100%");
		this.setSpacing(5);

		this.add(createCommentsTable());

		getCommentsListFromServer();

		// pobierz interwal czasowy z serwera
		commentsService.getSchedulerInterval(new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				new MyDialog(messages.error(), caught.getMessage()).show();
			}

			public void onSuccess(Integer result) {
				schedulerInterval = result;
			}
		});

		// Return the content
		this.ensureDebugId("cwCommentsListPanel");
	}

	/**
	 * Tworzy tabele z komentarzami uzytkownikow
	 * 
	 * @return
	 */
	private HorizontalPanel createCommentsTable() {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setStyleName("gwt-TablePanel");
		hPanel.setWidth("650px");

		commentsTable.setCellPadding(5);
		hPanel.add(commentsTable);
		return hPanel;
	}

	/**
	 * Pobiera liste komentarzy z serwera
	 */
	public void getCommentsListFromServer() {
		commentsService.getCommentsList(new AsyncCallback<List<CommentsDto>>() {

			public void onFailure(Throwable caught) {
				new MyDialog(messages.error(), caught.getMessage()).show();
			}

			public void onSuccess(List<CommentsDto> result) {
				fillCommentsTable(result);
			}

		});
	}

	/**
	 * Wypelnia tabele z danymi
	 * 
	 * @param commentsDtoList
	 */
	private void fillCommentsTable(List<CommentsDto> commentsDtoList) {
		if (commentsDtoList == null || commentsDtoList.size() == 0) {
			commentsTable.setText(0, 0, messages.noMessageInfo() + " Lista odświeża się automatycznie po: "
					+ (schedulerInterval / 1000) + " sekundach.");
			return;
		}
		int i = 0;
		for (CommentsDto comment : commentsDtoList) {
			commentsTable.setText(i, 0, comment.getUserName());
			commentsTable.setText(i, 1, comment.getUserComments());
			i++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.client.events.RefreshCommentsTableEventHandler#
	 * onRefreshCommentsTable
	 * (com.czapiewski.client.events.RefreshCommentsTableEvent)
	 */
	public void onRefreshCommentsTable(RefreshCommentsTableEvent event) {
		getCommentsListFromServer();
	}
}
