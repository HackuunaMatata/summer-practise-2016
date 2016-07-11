package com.venedikttsulenev.Task3;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
@Entity
@Table(name = "City", schema = "Task2", catalog = "")
public class CityEntity {
    private String name;
    private Integer population;
    private Integer area;
    private String country;
    private CountryEntity countryByCountry;
    private Collection<CountryEntity> countriesByName;
    private Collection<FestivalEntity> festivalsByName;

    @Id
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Population")
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Basic
    @Column(name = "Area")
    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (population != null ? !population.equals(that.population) : that.population != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Country", referencedColumnName = "Name", nullable = false)
    public CountryEntity getCountryByCountry() {
        return countryByCountry;
    }

    public void setCountryByCountry(CountryEntity countryByCountry) {
        this.countryByCountry = countryByCountry;
    }

    @OneToMany(mappedBy = "cityByCapital")
    public Collection<CountryEntity> getCountriesByName() {
        return countriesByName;
    }

    public void setCountriesByName(Collection<CountryEntity> countriesByName) {
        this.countriesByName = countriesByName;
    }

    @OneToMany(mappedBy = "cityByCity")
    public Collection<FestivalEntity> getFestivalsByName() {
        return festivalsByName;
    }

    public void setFestivalsByName(Collection<FestivalEntity> festivalsByName) {
        this.festivalsByName = festivalsByName;
    }
}
