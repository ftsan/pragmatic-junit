package iloveyouboss;

import java.util.*;
import java.util.function.Predicate;

public class Profile {
    private AnswerCollection answers = new AnswerCollection();
    private String id;

    public Profile(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public MatchSet getMatchSet(Criteria criteria) {
        return new MatchSet(id, answers, criteria);
    }


    @Override
    public String toString() {
        return id;
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answers.find(pred);
    }
}
