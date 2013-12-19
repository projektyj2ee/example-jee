package com.czapiewski.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * workspace
 * /Projekt-Zadanie/ZadanieTestoweMarcinCzapiewski/project/CommentsDemo
 * -web/src/main/resources/com/czapiewski/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

	@DefaultMessage("Send")
	@Key("sendButton")
	String sendButton();
	
	@DefaultMessage("Name")
	@Key("signLabel")
	String signLabel();
	
	@DefaultMessage("Nowy komentarz")
	@Key("commentsTabName1")
	String commentsTabName1();
	
	@DefaultMessage("Komentarze użytkownika")
	@Key("commentsTabName2")
	String commentsTabName2();
	
	@DefaultMessage("Lista ostatnich komentarzy")
	@Key("commentsTabName3")
	String commentsTabName3();
	
	@DefaultMessage("O programie")
	@Key("commentsTabName4")
	String commentsTabName4();
	
	@Key("error")
	String error();
	
	@Key("info")
	String info();
	
	@Key("emptyMessageError")
	String emptyMessageError();
	
	@Key("emptySignError")
	String emptySignError();
	
	@Key("maxSizeMessageError")
	String maxSizeMessageError();
	
	@Key("commentWasSend")
	String commentWasSend();
	
	@Key("noMessageInfo")
	String noMessageInfo();
	
	@Key("searchCommentsButton")
	String searchCommentsButton();

	@Key("userNameLabel")
	String userNameLabel();

	@Key("emptyUserNameError")
	String emptyUserNameError();

	@Key("noSchedulerDefFound")
	String noSchedulerDefFound();
}
