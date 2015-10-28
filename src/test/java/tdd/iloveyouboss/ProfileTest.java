package tdd.iloveyouboss;

import iloveyouboss.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ProfileTest {
    private Profile profile;
    // 転居時のサポートの有無を尋ねる質問
    private BooleanQuestion questionIsThereRelocation;
    // 転居時のサポートがあるという回答
    private Answer answerThereIsRelocation;

    // 転居時のサポートがないという回答
    private Answer answerThereIsNotRelocation;

    private BooleanQuestion questionReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;
    private Answer answerReimburseTuition;

    private Criteria criteria;

    @Before
    public void createProfile() {
        this.profile = new Profile();
    }
    @Before
    public void createCriteria() {
        this.criteria = new Criteria();
    }

    @Before
    public void createQuestionAndAnswer() {
        questionIsThereRelocation = new BooleanQuestion(1, "転居時のサポートはありますか？");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
        answerReimburseTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
    }

    // 該当するプロフィールに含まれる場合にはマッチングに成功する
    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        profile.add(answerThereIsRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
        criteria.add(criterion);

        assertTrue(profile.match(criteria).matches());
    }

    // 該当する回答がない場合にマッチングは失敗する
    @Test
    public void doesNotMatchWhenNoMatchingAnswer() {
        profile.add(answerThereIsNotRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
        criteria.add(criterion);

        assertFalse(profile.match(criteria).matches());
    }

    // 複数の回答が含まれる場合にマッチングは成功する
    @Test
    public void matchesWhenContainsMultipleAnswers() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
        criteria.add(criterion);

        assertTrue(profile.match(criteria).matches());
    }

    // 該当する条件が１つもない場合にマッチングは失敗する
    @Test
    public void doesNotMatchWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

        assertFalse(profile.match(criteria).matches());
    }

    // 該当する条件がある場合にマッチングが成功する
    @Test
    public void matchesWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerDoesNotReimburseTuition, Weight.Important));

        assertTrue(profile.match(criteria).matches());
    }

    // 必須の条件にすべて該当しない場合にマッチングに失敗する
    @Test
    public void doesNotMatchWhenAnyMustMeetCriteriaNotMet() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimburseTuition, Weight.MustMatch));

        assertFalse(profile.match(criteria).matches());
    }

    // 不問の条件に対してはマッチング成功する
    @Test
    public void matchesWhenCriterionIsDontCare() {
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerReimburseTuition, Weight.DontCare);
        criteria.add(criterion);

        assertTrue(profile.match(criteria).matches());
    }

    // 該当する条件がない場合のスコアは０になる
    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        ProfileMatch match = profile.match(criteria);

        assertThat(match.getScore(), equalTo(0));
    }

    // 該当する条件が存在する場合にスコアの合計が正しい値になる
    @Test
    public void correctScoreTotalThereAreMatches() {
        profile.add(answerReimburseTuition);
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));
        criteria.add(new Criterion(answerThereIsRelocation, Weight.VeryImportant));

        ProfileMatch match = profile.match(criteria);

        assertThat(match.getScore(), equalTo(6000));
    }

}