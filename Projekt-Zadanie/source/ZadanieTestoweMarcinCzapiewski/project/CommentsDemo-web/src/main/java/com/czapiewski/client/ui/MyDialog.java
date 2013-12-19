package com.czapiewski.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyDialog extends DialogBox {

	public MyDialog(String title, String msg) {
		// Set the dialog box's caption.
		setText(title);

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		this.center();

		// Create a table to layout the content
		VerticalPanel dialogContents = new VerticalPanel();
		dialogContents.setSpacing(15);
		this.setWidget(dialogContents);

		// Add some text to the top of the dialog
		HTML details = new HTML(msg);
		dialogContents.add(details);
		dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);

		// DialogBox is a SimplePanel, so you have to set its widget property to
		// whatever you want its contents to be.
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				MyDialog.this.hide();
			}
		});

		dialogContents.add(ok);
		dialogContents.setCellHorizontalAlignment(ok, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
