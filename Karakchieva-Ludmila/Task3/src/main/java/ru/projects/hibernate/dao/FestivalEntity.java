package ru.projects.hibernate.dao;

import javax.persistence.*;

@Entity
@Table(name = "festival", schema = "tabletest", catalog = "")
public class FestivalEntity {
    private String name;
    private String city;
    private Integer places;
    private String country;
    private String organizer;

    @Id
    @Column(name = "Name", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "City", nullable = true, length = 40)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "Places", nullable = true)
    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @Basic
    @Column(name = "Country", nullable = true, length = 40)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "Organizer", nullable = true, length = 40)
    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalEntity that = (FestivalEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (places != null ? !places.equals(that.places) : that.places != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (organizer != null ? !organizer.equals(that.organizer) : that.organizer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (places != null ? places.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (organizer != null ? organizer.hashCode() : 0);
        return result;
    }
}
