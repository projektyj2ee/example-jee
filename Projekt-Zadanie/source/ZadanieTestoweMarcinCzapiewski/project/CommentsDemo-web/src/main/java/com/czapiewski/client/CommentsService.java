package com.czapiewski.client;

import java.util.List;

import com.czapiewski.common.dto.CommentsDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("comments")
public interface CommentsService extends RemoteService {	
	/**
	 * Wysyla komentarz użytkownika na serwer
	 * @param commentsDto
	 * @return
	 * @throws IllegalArgumentException
	 */
	String sendCommentToServer(CommentsDto commentsDto) throws IllegalArgumentException;
	
	/**
	 * Pobiera listę komentarzy
	 * @return
	 * @throws IllegalArgumentException
	 */
	List<CommentsDto> getCommentsList() throws IllegalArgumentException;
	
	/**
	 * Pobiera listę komentarzy danego użytkownika
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	List<CommentsDto> getUserCommentsList(String userName) throws IllegalArgumentException;
	
	/**
	 * Pobiera interwal czasowy po jakim odswiezone maja byc tabele z
	 * komentarzami
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	Integer getSchedulerInterval() throws IllegalArgumentException;
	
	/**
	 * Pobiera ile wierszy będzie wyswietlane w tabeli z komentarzami
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	Integer getRowCountShow() throws IllegalArgumentException;
}
