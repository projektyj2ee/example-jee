package com.czapiewski.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CommentsServiceAsync {

	/**
	 * @see com.czapiewski.client.CommentsService
	 */
	void sendCommentToServer( com.czapiewski.common.dto.CommentsDto commentDto, AsyncCallback<java.lang.String> callback);

	/**
	 * @see com.czapiewski.client.CommentsService
	 */
	void getCommentsList(AsyncCallback<java.util.List<com.czapiewski.common.dto.CommentsDto>> callback);

	/**
	 * @see com.czapiewski.client.CommentsService
	 */
	void getUserCommentsList(java.lang.String userName, AsyncCallback<java.util.List<com.czapiewski.common.dto.CommentsDto>> callback);
	
	/**
	 * @see com.czapiewski.client.CommentsService
	 */
	void getSchedulerInterval(AsyncCallback<java.lang.Integer> callback);

	/**
	 * @see com.czapiewski.client.CommentsService
	 */
	void getRowCountShow(AsyncCallback<java.lang.Integer> callback);
	
	/**
	 * Utility class to get the RPC Async interface from client-side code
	 */
	public static final class Util {
		private static CommentsServiceAsync instance;

		public static final CommentsServiceAsync getInstance() {
			if (instance == null) {
				instance = (CommentsServiceAsync) GWT.create(CommentsService.class);
			}
			return instance;
		}

		private Util() {
			// Utility class should not be instanciated
		}
	}
}
