package iloveyouboss;

/**
 * Created by futeshi on 2015/10/14.
 */
public class MatchSet implements Comparable<MatchSet> {
    private AnswerCollection answers;
    private Criteria criteria;
    private int score = Integer.MIN_VALUE;
    private String profileId;

    public MatchSet(String profileId, AnswerCollection answers, Criteria criteria) {
        this.profileId = profileId;
        this.answers = answers;
        this.criteria = criteria;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public boolean matches() {
        if (doesNotMeetAnyMustMatchCriterion(criteria)) {
            return false;
        }
        return anyMatches(criteria);
    }

    private Answer answerMatching(Criterion criterion) {
        return answers.answerMatching(criterion);
    }

    /**
     * 必須条件のチェック
     *
     * @param criteria
     * @return 必須条件にマッチしなければtrue
     */
    private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
        for (Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));

            if (!match && criterion.getWeight() == Weight.MustMatch) {
                return true;
            }
        }
        return false;
    }

    private boolean anyMatches(Criteria criteria) {
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            anyMatches |= criterion.matches(answerMatching(criterion));
        }
        return anyMatches;
    }


    public int getScore() {
        int score = 0;
        for (Criterion criterion : criteria) {
            if (criterion.matches(answerMatching(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
        return score;
    }


    @Override
    public int compareTo(MatchSet that) {
        return new Integer(getScore()).compareTo(new Integer(that.getScore()));
    }
}
