package com.czapiewski.common.dto;

import java.io.Serializable;

/**
 * DTO dla komentarzy
 * 
 * @author Marcin
 * 
 */
public class CommentsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5953599995337041894L;

	/**
	 * Nazwa użytkownika
	 */
	private String userName;

	/**
	 * Komentarz użytkownika
	 */
	private String userComments;

	public CommentsDto() {
		super();
	}

	public CommentsDto(String userName, String userComments) {
		super();
		this.userName = userName;
		this.userComments = userComments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserComments() {
		return userComments;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

}
