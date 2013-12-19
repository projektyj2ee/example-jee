package com.czapiewski.server;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.czapiewski.client.CommentsService;
import com.czapiewski.common.dto.CommentsDto;
import com.czapiewski.ejb.intf.ICommentsFacadeRemote;
import com.czapiewski.server.utils.CommentsUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CommentsServiceImpl extends RemoteServiceServlet implements CommentsService {

	private final Logger logger = Logger.getLogger(CommentsServiceImpl.class);

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czapiewski.client.CommentsService#sendCommentToServer(com.czapiewski
	 * .dto.CommentsDto)
	 */
	public String sendCommentToServer(CommentsDto commentDto) throws IllegalArgumentException {
		try {
			ICommentsFacadeRemote commentsFacade;
			InitialContext initContext = new InitialContext();
			commentsFacade = (ICommentsFacadeRemote) initContext.lookup(ICommentsFacadeRemote.JNDI_NAME);
			commentsFacade.save(CommentsUtils.convertCommentDtoToComment(commentDto));
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			return "Wystąpił błąd NamingException";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "Wystąpił błąd podczas zapisu komentarza. Skontaktuj się z administratorem";
		}

		return "Dziękujemy, komentarz został dodany.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.client.CommentsService#getCommentsList()
	 */
	public List<CommentsDto> getCommentsList() throws IllegalArgumentException {
		List<CommentsDto> commentsDtoList = new ArrayList<CommentsDto>();
		try {
			ICommentsFacadeRemote commentsFacade;
			InitialContext initContext = new InitialContext();
			commentsFacade = (ICommentsFacadeRemote) initContext.lookup(ICommentsFacadeRemote.JNDI_NAME);
			commentsDtoList = CommentsUtils.convertAllCommentsToCommentsDtoList(commentsFacade.findAll());
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}

		return commentsDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czapiewski.client.CommentsService#getUserCommentsList(java.lang.String
	 * )
	 */
	public List<CommentsDto> getUserCommentsList(String userName) throws IllegalArgumentException {
		List<CommentsDto> userCommentsDtoList = new ArrayList<CommentsDto>();
		try {
			ICommentsFacadeRemote commentsFacade;
			InitialContext initContext = new InitialContext();
			commentsFacade = (ICommentsFacadeRemote) initContext.lookup(ICommentsFacadeRemote.JNDI_NAME);
			userCommentsDtoList = CommentsUtils.convertAllCommentsToCommentsDtoList(commentsFacade
					.findCommentByName(userName));
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}

		return userCommentsDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.client.CommentsService#getSchedulerInterval()
	 */
	public Integer getSchedulerInterval() throws IllegalArgumentException {
		Integer schedulerInterval = -1;
		try {
			ICommentsFacadeRemote commentsFacade;
			InitialContext initContext = new InitialContext();
			commentsFacade = (ICommentsFacadeRemote) initContext.lookup(ICommentsFacadeRemote.JNDI_NAME);
			schedulerInterval = commentsFacade.getSchedulerInterval();
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		return schedulerInterval;
	}

	@Override
	public Integer getRowCountShow() throws IllegalArgumentException {
		Integer rowCountShow = -1;
		try {
			ICommentsFacadeRemote commentsFacade;
			InitialContext initContext = new InitialContext();
			commentsFacade = (ICommentsFacadeRemote) initContext.lookup(ICommentsFacadeRemote.JNDI_NAME);
			rowCountShow = commentsFacade.getRowCountShow();
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		return rowCountShow;
	}
}
