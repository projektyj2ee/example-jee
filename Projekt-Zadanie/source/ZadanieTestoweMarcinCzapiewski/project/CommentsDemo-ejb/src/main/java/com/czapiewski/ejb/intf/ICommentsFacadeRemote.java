package com.czapiewski.ejb.intf;

import javax.ejb.Remote;

@Remote
public interface ICommentsFacadeRemote extends ICommentsFacadeLocal {
	public static final String JNDI_NAME = "ejb/CMPCommentsFacade";
}
