package com.czapiewski.client.ui;

import java.util.List;

import com.czapiewski.client.CommentsService;
import com.czapiewski.client.CommentsServiceAsync;
import com.czapiewski.client.Messages;
import com.czapiewski.common.dto.CommentsDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Kontener dla zakładki 'Komentarze użytkownika'
 * @author Marcin
 *
 */
public class UserCommentsPanel extends VerticalPanel {
	
	/**
	 * Tworzy zdalny serwis proxy do komunikacji z częścią serwerowa.
	 */
	private final CommentsServiceAsync commentsService = GWT.create(CommentsService.class);

	private final Messages messages = GWT.create(Messages.class);
	
	private final TextBox textBox = new TextBox();
	private FlexTable commentsTable = new FlexTable();
	private final HorizontalPanel hPanel = new HorizontalPanel();
	
	/**
	 * Konstruktor
	 */
	public UserCommentsPanel() {
		super();

		this.setSize("100%", "100%");
		this.setSpacing(5);

		onInitialize();
		
		// Return the content
		this.ensureDebugId("cwUserCommentsPanel");
	}
	
	/**
	 * Inicjalizuje panel zakładki
	 */
	private void onInitialize() {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(10);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		textBox.setMaxLength(10);
		textBox.setWidth("450px");

		final Label userNameLabel = new Label(messages.userNameLabel());
		final Button searchButton = new Button(messages.searchCommentsButton());

		// formularz
		hPanel.add(userNameLabel);
		hPanel.add(textBox);
		hPanel.add(searchButton);

		this.add(hPanel);
		this.add(createCommentsTable());
		
		fillCommentsTable(null);

		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (isValidateForm()) {
					commentsService.getUserCommentsList(textBox.getValue(), new AsyncCallback<List<CommentsDto>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							new MyDialog(messages.error(), caught.getMessage()).show();// SERVER_ERROR).show();
						}

						public void onSuccess(List<CommentsDto> result) {
							textBox.setValue("");
							fillCommentsTable(result);
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
		if (textBox.getValue() == null || "".equals(textBox.getValue())) {
			textBox.setStyleName("gwt-ErrorWidget");
			new MyDialog(messages.error(), messages.emptyUserNameError()).show();
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

		hPanel.setStyleName("gwt-TablePanel");
		hPanel.setWidth("650px");

		commentsTable.setCellPadding(5);
		hPanel.add(commentsTable);
		return hPanel;
	}
	
	/**
	 * Wypelnia tabele z danymi
	 * 
	 * @param commentsDtoList
	 */
	private void fillCommentsTable(List<CommentsDto> commentsDtoList) {
		clearFlexTable();

		if(commentsDtoList == null || commentsDtoList.size() == 0){
			commentsTable.setText(0, 0, messages.noMessageInfo());
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
	 * Czysci tebele z wiadomosciami uzytkownika
	 */
	private void clearFlexTable() {
		hPanel.remove(commentsTable);
		commentsTable = new FlexTable();
		hPanel.add(commentsTable);
	}
}
