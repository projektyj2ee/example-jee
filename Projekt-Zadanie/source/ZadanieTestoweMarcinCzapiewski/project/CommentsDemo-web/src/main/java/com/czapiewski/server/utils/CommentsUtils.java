package com.czapiewski.server.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.czapiewski.common.dto.CommentsDto;
import com.czapiewski.entity.Comment;

/**
 * Abstrakcyjna klasa utilisowa uzywana do mapowanie obiektow DTO na encji i
 * odwrotnie Klasa idealnie nadaje się do użycia reflekcji ale nie mam już czasu
 * żeby to zrobić:/
 * 
 * @author Marcin
 * 
 */
public abstract class CommentsUtils {

	/**
	 * Mapuje DTO na encje
	 * 
	 * @param commentsDto
	 * @return
	 */
	public static Comment convertCommentDtoToComment(CommentsDto commentsDto) {
		Comment comment = new Comment();
		comment.setName(commentsDto.getUserName());
		comment.setComment(commentsDto.getUserComments());
		comment.setDate(new Date());
		return comment;
	}

	/**
	 * Mapuje encje na DTO
	 * 
	 * @param comment
	 * @return
	 */
	public static CommentsDto convertCommentToCommentDto(Comment comment) {
		CommentsDto commentsDto = new CommentsDto();
		commentsDto.setUserName(comment.getName());
		commentsDto.setUserComments(comment.getComment());
		return commentsDto;
	}

	/**
	 * Mapuje listę encji na listę obiektów DTO
	 * 
	 * @param commentList
	 * @return
	 */
	public static List<CommentsDto> convertAllCommentsToCommentsDtoList(List<Comment> commentList){
		List<CommentsDto> commentsDtoList = new ArrayList<CommentsDto>();
		for (Comment comment : commentList) {
			CommentsDto commentDto = CommentsUtils.convertCommentToCommentDto(comment);
			commentsDtoList.add(commentDto);
		}
		return commentsDtoList;
	}
}
