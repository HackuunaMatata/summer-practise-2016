package com.venedikttsulenev.Task3;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
public class FestivalOrganizerEntityPK implements Serializable {
    private String name;
    private String accreditationCountry;

    @Column(name = "Name")
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "AccreditationCountry")
    @Id
    public String getAccreditationCountry() {
        return accreditationCountry;
    }

    public void setAccreditationCountry(String accreditationCountry) {
        this.accreditationCountry = accreditationCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalOrganizerEntityPK that = (FestivalOrganizerEntityPK) o;

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
