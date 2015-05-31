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
@Table(name="User")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {


	private static final long serialVersionUID = -1030932318394851201L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field
	private String user;
	
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
	}
	
	@Override
	public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
	}
}