package ru.practice_lab.hibernate.dao;

import javax.persistence.*;

/**
 * Created by a1 on 04.07.16.
 */
@Entity
@Table(name = "Festival", schema = "practice_lab", catalog = "")
public class FestivalEntity {
    private String name;
    private Integer places;
    private CityEntity cityByCity;
    private CountryEntity countryByCountry;

    @Id
    @Column(name = "Name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Places", nullable = true)
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

    @ManyToOne
    @JoinColumn(name = "City", referencedColumnName = "Name")
    public CityEntity getCityByCity() {
        return cityByCity;
    }

    public void setCityByCity(CityEntity cityByCity) {
        this.cityByCity = cityByCity;
    }

    @ManyToOne
    @JoinColumn(name = "Country", referencedColumnName = "Name")
    public CountryEntity getCountryByCountry() {
        return countryByCountry;
    }

    public void setCountryByCountry(CountryEntity countryByCountry) {
        this.countryByCountry = countryByCountry;
    }
}
