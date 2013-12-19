package com.czapiewski.ejb;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.czapiewski.ejb.intf.ICommentsFacadeLocal;
import com.czapiewski.ejb.intf.ICommentsFacadeRemote;
import com.czapiewski.entity.Comment;
import com.czapiewski.persistencecontext.intf.INames;
import com.czapiewski.service.CommentsService;

@Stateless(name = "CMPCommentsFacade", mappedName = "ejb/CMPCommentsFacade")
public class CommentsFasade implements ICommentsFacadeLocal,
		ICommentsFacadeRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773642550221672742L;

	private final Logger logger = Logger.getLogger(CommentsFasade.class);

	@Resource
	private EJBContext ctx;

	@PersistenceContext(unitName = INames.PU_NAME)
	private EntityManager em;

	@EJB
	CommentsService service; // serwis uzywany do odczytywanie konfiguracji

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.ejb.intf.ICommentsFacadeLocal#getSchedulerInterval()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer getSchedulerInterval() {
		return service.getSchedulerInterval();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czapiewski.ejb.intf.ICommentsFacadeLocal#findCommentByName(java.lang
	 * .String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Comment> findCommentByName(String name) {
		Query q = em
				.createNamedQuery("Comment.findByAuthorName");
		q.setParameter("name", name);
		return q.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.ejb.intf.ICommentsFacadeLocal#findAll()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Comment> findAll() {
		List<Comment> commentList = new ArrayList<Comment>();
		Query q = em.createNamedQuery("Comment.findAll");
		q.setFirstResult(0);
		q.setMaxResults(service.getMaxCommendsByPage());
		try {
			commentList = (List<Comment>) q.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		return commentList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czapiewski.ejb.intf.ICommentsFacadeLocal#save(com.czapiewski.entity
	 * .Comment)
	 */
	@Override
	public void save(Comment comment) throws Exception {
		try {
			comment.setId((new Date()).getTime());
			em.persist(comment);
			em.flush();
		} catch (Exception ex) {
			ctx.setRollbackOnly();
			logger.error(ex.getMessage(), ex);
			throw new IllegalArgumentException(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czapiewski.ejb.intf.ICommentsFacadeLocal#getRowCountShow()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer getRowCountShow() {
		return service.getMaxCommendsByPage();
	}
}
