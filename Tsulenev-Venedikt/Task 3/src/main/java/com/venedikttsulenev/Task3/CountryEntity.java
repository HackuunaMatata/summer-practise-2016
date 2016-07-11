package com.venedikttsulenev.Task3;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
@Entity
@Table(name = "Country", schema = "Task2", catalog = "")
public class CountryEntity {
    private String name;
    private Integer population;
    private Integer area;
    private String capital;
    private Collection<CityEntity> citiesByName;
    private CityEntity cityByCapital;
    private Collection<FestivalOrganizerEntity> festivalOrganizersByName;

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
    @Column(name = "Capital")
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (population != null ? !population.equals(that.population) : that.population != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (capital != null ? !capital.equals(that.capital) : that.capital != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (capital != null ? capital.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "countryByCountry")
    public Collection<CityEntity> getCitiesByName() {
        return citiesByName;
    }

    public void setCitiesByName(Collection<CityEntity> citiesByName) {
        this.citiesByName = citiesByName;
    }

    @ManyToOne
    @JoinColumn(name = "Capital", referencedColumnName = "Name", nullable = false)
    public CityEntity getCityByCapital() {
        return cityByCapital;
    }

    public void setCityByCapital(CityEntity cityByCapital) {
        this.cityByCapital = cityByCapital;
    }

    @OneToMany(mappedBy = "countryByAccreditationCountry")
    public Collection<FestivalOrganizerEntity> getFestivalOrganizersByName() {
        return festivalOrganizersByName;
    }

    public void setFestivalOrganizersByName(Collection<FestivalOrganizerEntity> festivalOrganizersByName) {
        this.festivalOrganizersByName = festivalOrganizersByName;
    }
}
