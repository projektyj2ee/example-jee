package com.czapiewski.client.ui;

import java.util.List;

import com.czapiewski.client.CommentsService;
import com.czapiewski.client.CommentsServiceAsync;
import com.czapiewski.client.Messages;
import com.czapiewski.client.events.RefreshCommentsTableEvent;
import com.czapiewski.client.events.RefreshCommentsTableEventHandler;
import com.czapiewski.common.dto.CommentsDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Kontener dla zakładki 'Nowy komentarz'
 * 
 * @author Marcin
 * 
 */
public class NewCommentsPanel extends VerticalPanel implements
		RefreshCommentsTableEventHandler {

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Tworzy zdalny serwis proxy do komunikacji z częścią serwerowa.
	 */
	private final CommentsServiceAsync commentsService = GWT
			.create(CommentsService.class);

	private final Messages messages = GWT.create(Messages.class);
	private final TextArea textArea = new TextArea();
	private final TextBox textBox = new TextBox();
	private final FlexTable commentsTable = new FlexTable();
	VerticalPanel propertiesPanel = new VerticalPanel();
	
	private Integer schedulerInterval = new Integer(0);
	private Integer countRowShow = new Integer(0);

	/**
	 * Konstruktor
	 */
	public NewCommentsPanel() {
		super();

		NewCommentsPanel.EVENT_BUS.addHandler(
				RefreshCommentsTableEvent.getType(), this);

		this.setSize("100%", "100%");
		this.setSpacing(5);

		onInitialize();
		
		getPropertiesFromServer();

		// Return the content
		this.ensureDebugId("cwNewCommentsPanel");
	}

	/**
	 * Inicjalizuje panel zakładki
	 */
	private void onInitialize() {
		// textArea.setCharacterWidth(80);
		textArea.setVisibleLines(15);
		textArea.setWidth("645px");
		textArea.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (textArea.getValue().length() > 1000) {
					textArea.setStyleName("gwt-ErrorWidget");
					new MyDialog(messages.error(), messages
							.maxSizeMessageError()).show();
				}
			}
		});

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(10);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		textBox.setMaxLength(10);
		textBox.setWidth("450px");

		final Label signLabel = new Label(messages.signLabel());
		final Button sendButton = new Button(messages.sendButton());

		// formularz
		hPanel.add(signLabel);
		hPanel.add(textBox);
		hPanel.add(sendButton);

		VerticalPanel spacePanel = new VerticalPanel();
		spacePanel.setHeight("40px");
		spacePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		spacePanel.add(new Label("Lista komentarzy:"));

		this.add(textArea);
		this.add(hPanel);
		this.add(spacePanel);
		this.add(createCommentsTable());
		this.add(propertiesPanel);

		getCommentsListFromServer();

		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (isValidateForm()) {
					CommentsDto commentDto = new CommentsDto();
					commentDto.setUserName(textBox.getValue());
					commentDto.setUserComments(textArea.getValue());
					commentsService.sendCommentToServer(commentDto,
							new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									new MyDialog(messages.error(), caught
											.getMessage()).show();// SERVER_ERROR).show();
								}

								public void onSuccess(String result) {
									getCommentsListFromServer();
									textBox.setValue("");
									textArea.setValue("");
									new MyDialog(messages.info(), result);
								}
							});
				}// end if
			}
		});
	}

	/**
	 * Waliduje dane formularza
	 * 
	 * @return
	 */
	private boolean isValidateForm() {
		if (textArea.getValue() == null || "".equals(textArea.getValue())) {
			textArea.setStyleName("gwt-ErrorWidget");
			new MyDialog(messages.error(), messages.emptyMessageError()).show();
			return false;
		} else if (textArea.getValue().length() > 1000) {
			textArea.setStyleName("gwt-ErrorWidget");
			new MyDialog(messages.error(), messages.maxSizeMessageError())
					.show();
			return false;
		} else {
			textArea.setStyleName("gwt-NormalWidget");
		}

		if (textBox.getValue() == null || "".equals(textBox.getValue())) {
			textBox.setStyleName("gwt-ErrorWidget");
			new MyDialog(messages.error(), messages.emptySignError()).show();
			return false;
		} else {
			textBox.setStyleName("gwt-NormalWidget");
		}

		return true;
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
	 * Tworzy Panel informacyjny
	 * 
	 * @return
	 */
	private VerticalPanel createInfoPanel() {
		this.remove(propertiesPanel);
		propertiesPanel = new VerticalPanel();
		propertiesPanel.setHeight("40px");
		propertiesPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		propertiesPanel.add(new Label("Czas automatycznego odświeżenia: "
				+ (schedulerInterval / 1000) + " sekund"));
		propertiesPanel.add(new Label("Ilość wyświetlanych wierszy: " + countRowShow));
		
		this.add(propertiesPanel);
		return propertiesPanel;
	}

	/**
	 * Wypelnia tabele z danymi
	 * 
	 * @param commentsDtoList
	 */
	private void fillCommentsTable(List<CommentsDto> commentsDtoList) {
		if (commentsDtoList == null || commentsDtoList.size() == 0) {
			if (schedulerInterval == 0) {
				commentsTable.setText(0, 0, messages.noMessageInfo());
			} else {
				commentsTable.setText(0, 0, messages.noMessageInfo()
						+ " Lista odświeża się automatycznie po: "
						+ (schedulerInterval / 1000) + " sekundach.");
			}
			return;
		}
		int i = 0;
		for (CommentsDto comment : commentsDtoList) {
			commentsTable.setText(i, 0, comment.getUserName());
			commentsTable.setText(i, 1, comment.getUserComments());
			i++;
		}
	}

	/**
	 * Pobiera liste komentarzy z serwera
	 */
	private void getCommentsListFromServer() {
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
	 * Pobiera propertiesy z serwera
	 */
	private void getPropertiesFromServer() {
		commentsService.getSchedulerInterval(new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				new MyDialog(messages.error(), caught.getMessage()).show();
			}

			public void onSuccess(Integer result) {
				schedulerInterval = result;
				
				commentsService.getRowCountShow(new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						new MyDialog(messages.error(), caught.getMessage())
								.show();
					}

					public void onSuccess(Integer result) {
						countRowShow = result;
						createInfoPanel();
					}
				});
			}
		});
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
