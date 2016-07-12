package Responses.dbEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin", schema = "database", catalog = "")
public class AdminEntity {
    private String eMail;

    @Id
    @Column(name = "EMail", nullable = false, length = 256)
    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminEntity that = (AdminEntity) o;

        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return eMail != null ? eMail.hashCode() : 0;
    }
}
