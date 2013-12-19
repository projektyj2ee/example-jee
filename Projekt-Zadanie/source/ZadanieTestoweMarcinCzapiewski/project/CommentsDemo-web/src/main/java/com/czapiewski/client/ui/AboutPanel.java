package com.czapiewski.client.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Kontener dla zakładki 'O programie'
 * 
 * @author Marcin
 * 
 */
public class AboutPanel extends VerticalPanel {

	/**
	 * Konstruktor
	 */
	public AboutPanel() {
		super();

		this.setSize("100%", "100%");
		this.setSpacing(12);

		HTML label = new HTML(
				"<b>Program testowy wykonany na potrzeby rekrutacji</b><br>Autor: Marcin Czapiewski <a href='http://www.marcinczapiewski.pl/'>www.marcinczapiewski.pl</a>");

		SimplePanel imageContainer = new SimplePanel();
		imageContainer.setWidget(new Image(
				"http://marcinczapiewski.pl/images/ae_gwt_java.png"));

		this.add(label);
		this.add(imageContainer);

		// Return the content
		this.ensureDebugId("cwAboutPanel");
	}
}
