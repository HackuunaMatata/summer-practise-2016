package Responses.dbEntities;

import javax.persistence.*;

/**
 * Created by venedikttsulenev on 14/07/16.
 */
@Entity
@Table(name = "DefaultAnswers", schema = "Responses", catalog = "")
@IdClass(DefaultAnswersEntityPK.class)
public class DefaultAnswersEntity {
    private int questionId;
    private String value;

    @Id
    @Column(name = "QuestionID")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Id
    @Column(name = "Value")
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

        DefaultAnswersEntity that = (DefaultAnswersEntity) o;

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
