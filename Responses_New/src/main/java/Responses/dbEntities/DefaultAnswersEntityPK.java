package Responses.dbEntities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class DefaultAnswersEntityPK implements Serializable {
    private int questionId;
    private String value;

    @Column(name = "QuestionID")
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Column(name = "Value")
    @Id
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultAnswersEntityPK that = (DefaultAnswersEntityPK) o;

        if (questionId != that.questionId) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
