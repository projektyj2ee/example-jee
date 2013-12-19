package com.czapiewski.ejb.intf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.czapiewski.entity.Comment;

@Local
public interface ICommentsFacadeLocal extends Serializable {

	/**
	 * Pobiera interwal czasowy po jakim odswiezone maja byc tabele z
	 * komentarzami
	 * 
	 * @return
	 */
	public Integer getSchedulerInterval();

	/**
	 * Pobiera informacje ile wierszy ma byc wyswietlane
	 * @return
	 */
	public Integer getRowCountShow();
	
	/**
	 * Pobiera komentarze danego uzytkownika
	 * 
	 * @param name
	 * @return
	 */
	public List<Comment> findCommentByName(String name);

	/**
	 * Pobiera wszystkie komentarze
	 * 
	 * @return
	 */
	public List<Comment> findAll();

	/**
	 * Utrwala komentarz w bazie
	 * 
	 * @param comment
	 * @throws Exception
	 */
	public void save(Comment comment) throws Exception;
}
