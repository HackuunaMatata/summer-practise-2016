package Responses.dbEntities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FormsEntityPK implements Serializable {
    private int id;
    private int questionId;

    @Column(name = "ID")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "QuestionID")
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormsEntityPK that = (FormsEntityPK) o;

        if (id != that.id) return false;
        if (questionId != that.questionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + questionId;
        return result;
    }
}
