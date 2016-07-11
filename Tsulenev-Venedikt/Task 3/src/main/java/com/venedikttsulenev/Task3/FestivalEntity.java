package com.venedikttsulenev.Task3;

import javax.persistence.*;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
@Entity
@Table(name = "Festival", schema = "Task2", catalog = "")
public class FestivalEntity {
    private String name;
    private String city;
    private Integer places;
    private String country;
    private String organizer;
    private CityEntity cityByCity;
    private FestivalOrganizerEntity festivalOrganizer;

    @Id
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "Places")
    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "Organizer")
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

    @ManyToOne
    @JoinColumn(name = "City", referencedColumnName = "Name", nullable = false)
    public CityEntity getCityByCity() {
        return cityByCity;
    }

    public void setCityByCity(CityEntity cityByCity) {
        this.cityByCity = cityByCity;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "Organizer", referencedColumnName = "Name", nullable = false), @JoinColumn(name = "Country", referencedColumnName = "AccreditationCountry", nullable = false)})
    public FestivalOrganizerEntity getFestivalOrganizer() {
        return festivalOrganizer;
    }

    public void setFestivalOrganizer(FestivalOrganizerEntity festivalOrganizer) {
        this.festivalOrganizer = festivalOrganizer;
    }
}
