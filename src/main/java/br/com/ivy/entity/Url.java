package br.com.ivy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


@Entity
@Indexed
@Table(name="Url")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Url implements Serializable {

	
	private static final long serialVersionUID = 1628481751610009871L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field
	private String path;
	
	private boolean sqlVulnerability, xssVulnerability;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Target target; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSqlVulnerability() {
		return sqlVulnerability;
	}

	public void setSqlVulnerability(boolean sqlVulnerability) {
		this.sqlVulnerability = sqlVulnerability;
	}

	public boolean isXssVulnerability() {
		return xssVulnerability;
	}

	public void setXssVulnerability(boolean xssVulnerability) {
		this.xssVulnerability = xssVulnerability;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}
