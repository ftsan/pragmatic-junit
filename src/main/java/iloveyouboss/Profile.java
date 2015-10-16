package iloveyouboss;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
    private AnswerCollection answers = new AnswerCollection();
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public MatchSet getMatchSet(Criteria criteria) {
        return new MatchSet(answers, criteria);
    }


    @Override
    public String toString() {
        return name;
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answers.find(pred);
    }
}
