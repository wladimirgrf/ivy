package br.com.ivy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	private boolean sqlVulnerability;

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
	
	@Override
	public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
	}
	
	@Override
	public boolean equals(Object object) {
        if (!(object instanceof Url)) {
            return false;
        }
        Url other = (Url)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
	}
}
