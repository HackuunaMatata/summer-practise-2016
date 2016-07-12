package ru.projects.hibernate.dao;

import javax.persistence.*;

@Entity
@Table(name = "festivalorganizer", schema = "tabletest", catalog = "")
public class FestivalorganizerEntity {
    private String name;
    private Integer accreditationCountry;

    @Id
    @Column(name = "Name", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "AccreditationCountry", nullable = true)
    public Integer getAccreditationCountry() {
        return accreditationCountry;
    }

    public void setAccreditationCountry(Integer accreditationCountry) {
        this.accreditationCountry = accreditationCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalorganizerEntity that = (FestivalorganizerEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (accreditationCountry != null ? !accreditationCountry.equals(that.accreditationCountry) : that.accreditationCountry != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (accreditationCountry != null ? accreditationCountry.hashCode() : 0);
        return result;
    }
}
