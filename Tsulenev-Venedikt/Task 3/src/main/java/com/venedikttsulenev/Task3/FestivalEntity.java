package com.venedikttsulenev.Task3;

import javax.persistence.*;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
@Entity
@Table(name = "Festival", schema = "Task2", catalog = "")
public class FestivalEntity {
    private String name;
    private Integer places;

    @Id
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Places")
    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalEntity that = (FestivalEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (places != null ? !places.equals(that.places) : that.places != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (places != null ? places.hashCode() : 0);
        return result;
    }
}
