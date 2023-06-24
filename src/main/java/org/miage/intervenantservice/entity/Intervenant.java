package org.miage.intervenantservice.entity;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Intervenant implements  Persistable<UUID>, Serializable   { 
	
	@Id
	private UUID id;
	private String nom;
	@Transient
	private boolean newOne;
	
	public Intervenant(UUID id, String nom) {
		this.id = id;
		this.nom = nom;
	}	

	public Intervenant() {
	}	

	public UUID getId() {
		return id;
	}	

	public void setId(UUID id) {
		this.id = id;
	}	

	public String getNom() {
		return nom;
	}	

	public void setNom(String nom) {
		this.nom = nom;
	}	

	@Override
	public String toString() {
		return "Intervenant [id=" + id + ", nom=" + nom + "]";
	}			

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)	
			return false;
		if (getClass() != obj.getClass())	
			return false;
		Intervenant other = (Intervenant) obj;	
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))		
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))		
			return false;
		return true;	
	}	

	@Override
	@Transient
	@JsonIgnore
	public boolean isNew() {
		return this.newOne || id == null;
	}

	public Intervenant setAsNew() {
		this.newOne = true;
		return this;
	}

}
