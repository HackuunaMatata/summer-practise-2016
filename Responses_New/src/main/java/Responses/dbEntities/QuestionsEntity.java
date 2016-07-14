package Responses.dbEntities;

import javax.persistence.*;

@Entity
@Table(name = "Questions", schema = "Responses", catalog = "")
public class QuestionsEntity {
    private int id;
    private String value;
    private boolean isRequired;
    private boolean isActive;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "isRequired")
    public boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean required) {
        isRequired = required;
    }

    @Basic
    @Column(name = "isActive")
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionsEntity that = (QuestionsEntity) o;

        if (id != that.id) return false;
        if (isRequired != that.isRequired) return false;
        if (isActive != that.isActive) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (isRequired ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
