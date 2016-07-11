package dbEntities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by venedikttsulenev on 11/07/16.
 */
@Entity
@Table(name = "Questions", schema = "Responses", catalog = "")
public class QuestionsEntity {
    private int id;
    private String value;
    private Byte isRequired;
    private Collection<FormsEntity> formsesById;

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
    public Byte getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Byte isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionsEntity that = (QuestionsEntity) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (isRequired != null ? !isRequired.equals(that.isRequired) : that.isRequired != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (isRequired != null ? isRequired.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "questionsByQuestionId")
    public Collection<FormsEntity> getFormsesById() {
        return formsesById;
    }

    public void setFormsesById(Collection<FormsEntity> formsesById) {
        this.formsesById = formsesById;
    }
}
