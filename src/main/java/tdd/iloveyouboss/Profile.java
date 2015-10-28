package tdd.iloveyouboss;

import iloveyouboss.Answer;
import iloveyouboss.Criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by futeshi on 2015/10/16.
 */
public class Profile {
    private Map<String, Answer> answers = new HashMap<>();

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public ProfileMatch match(Criteria criteria) {
        return new ProfileMatch(answers, criteria);
    }
}
