package edu.cooper.ee.ece366.LusterCode.store;

import edu.cooper.ee.ece366.LusterCode.model.Answer;
import org.jdbi.v3.core.Jdbi;
import java.util.List;

public class AnswerStoreJdbi implements AnswerStore{

    private final Jdbi jdbi;
    public AnswerStoreJdbi(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void populateDb() {
        jdbi.useHandle(
                handle ->{
                        handle.execute("CREATE TABLE IF NOT EXISTS answers (id bigint auto_increment, username varchar(255), askPostID bigint, answerType varchar(255), content varchar(255), timestamp datetime, primary key(id));");
                });
    }
    @Override
    public Answer addAnswer(final Answer answer) {
        return jdbi.withHandle(
                handle ->
                        handle
                                .createUpdate("INSERT INTO answers (username, askPostID, answerType, content, timestamp) VALUES (:username, :askPostID, :answerType, :content, :timestamp)")
                                .bind("username", answer.getUsername())
                                .bind("askPostID", answer.getAskPostID())
                                .bind("answerType", answer.getAnswerType())
                                .bind("content", answer.getContent())
                                .bind("timestamp", answer.getTimestamp())
                                .executeAndReturnGeneratedKeys("id")
                                .mapToBean(Answer.class)
                                .one());
    }
    @Override
    public Answer getAnswer(final Long answerID) {
        return jdbi.withHandle(
                handler ->
                        handler
                            .createQuery("SELECT id, username, askPostID, answerType, content, timestamp FROM answers WHERE id = :id")
                            .bind("id", answerID)
                            .mapToBean(Answer.class)
                            .one());
    }

    @Override
    public Answer deleteAnswer(final Long answerID) {
        return jdbi.withHandle(
                handler ->
                        handler
                                .createQuery("DELETE FROM answers WHERE id = :id")
                                .bind("id", answerID)
                                .mapToBean(Answer.class)
                                .one());
    }
}