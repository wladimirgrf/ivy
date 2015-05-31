package br.com.ivy.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


@Entity
@Indexed
@Table(name="Target")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Target implements Serializable {

	
	private static final long serialVersionUID = 2963284138834087179L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field
	private String host, owner, person, email, country, changed;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Url> urls;
	
	@Field
	private long lastScan;
	
	@Field
	private boolean security;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
	
	public long getLastScan() {
		return lastScan;
	}

	public void setLastScan(long lastScan) {
		this.lastScan = lastScan;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}
	
	@Override
	public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
	}
	
	@Override
	public boolean equals(Object object) {
        if (!(object instanceof Target)) {
            return false;
        }
        Target other = (Target)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
	}
}
