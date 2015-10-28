package tdd.iloveyouboss;

import iloveyouboss.Answer;
import iloveyouboss.Criteria;
import iloveyouboss.Criterion;
import iloveyouboss.Weight;

import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * Created by futeshi on 2015/10/18.
 */
public class ProfileMatch {
    private Criteria criteria;
    private Map<String, Answer> answers;

    public ProfileMatch(Map<String, Answer> answers, Criteria criteria) {
        this.criteria = criteria;
        this.answers = answers;
    }

    public int getScore() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        criteria.iterator(), Spliterator.ORDERED), false)
                .filter(c -> matches(c))
                .mapToInt(c -> c.getWeight().getValue())
                .sum();
    }

    public boolean matches(Criterion criterion) {
        Answer answer = getMatchingProfileAnswer(criterion);
        return criterion.getAnswer().match(answer) || criterion.getWeight() == Weight.DontCare;
    }

    // 該当する回答をプロフィールから取得する
    private Answer getMatchingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }

    public boolean matches() {
        boolean matches = false;
        for (Criterion criterion : criteria) {
            if (matches(criterion)) {
                matches = true;
            } else if (criterion.getWeight() == Weight.MustMatch) {
                return false;
            }
        }
        return matches;
    }
}
