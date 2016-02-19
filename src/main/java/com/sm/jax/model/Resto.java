package com.sm.jax.model;

import javax.persistence.*;

/**
 * Simple entity.
 *
 * @author Roberto Cortez
 */
public class Resto {
    private String restoId;

    private String name;

    private String description;
    
    

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Resto person = (Resto) o;

        return restoId.equals(person.restoId);
    }

    @Override
    public int hashCode() {
        return restoId.hashCode();
    }

	public String getRestoId() {
		return restoId;
	}

	public void setRestoId(String restoId) {
		this.restoId = restoId;
	}


}
