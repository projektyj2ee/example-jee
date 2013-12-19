package com.czapiewski.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TableGenerator;

/**
 * Encja dla tebeli COMMENT
 * 
 * @author Marcin
 * 
 */
@Entity
@Table(name = "COMMENT")
@NamedQueries({
		@NamedQuery(name = "Comment.findAll", query = "SELECT a FROM Comment a ORDER BY a.date DESC"),
		@NamedQuery(name = "Comment.findByAuthorName", query = "SELECT a FROM Comment a WHERE a.name = :name") })
public class Comment implements Serializable {

	private static final long serialVersionUID = -7457768439131454898L;

	/**
	 * Z racji tego za mam problemy automatyczna generacja wartosci dla PK w eclipselinku i bazie derby 
	 * a nie mam czasu na analizowanie tego problemu to narazie rezygnuje z obu metod
	 * 
	 */
	// eclipseline cos sobie nie radzi z sekwencjami na derby:/
	// @Id
	// @GeneratedValue(generator = "CommentsSequenceGenerator")
	// @SequenceGenerator(name = "CommentsSequenceGenerator", sequenceName =
	// "commentssequence", allocationSize = 500)
	// initialValue=1 (default) but 'START WITH'=500
	
	@Id
	@Column(name = "ID_COMMENT", nullable = false, precision = 10)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "commentIdGenerator")
//	@TableGenerator(name = "commentIdGenerator", table = "is_generators", pkColumnName = "name", pkColumnValue = "idComment", valueColumnName = "\"value\"", initialValue = 1, allocationSize = 1)
	@Basic(optional = false)
	private Long id;

	@Basic(optional = false)
	@Column(name = "AUTHOR_NAME")
	private String name; 

	@Basic(optional = false)
	@Column(name = "USER_COMMENT")
	private String comment;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
