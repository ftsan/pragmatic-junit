package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 15/10/12.
 */
public class ProfilePoolTest {
    private ProfilePool pool;
    private Profile langrsoft;
    private Profile smeltInc;
    private BooleanQuestion doTheyReimburseTuition;

    @Before
    public void create() {
        pool = new ProfilePool();
        langrsoft = new Profile("Langrsoft");
        smeltInc = new Profile("Smelt Inc.");
        doTheyReimburseTuition = new BooleanQuestion(1, "Reimburses tuition?");
    }

    // スコアの順に結果を返す
    @Test
    public void answersResultsInScoredOrder() {
        smeltInc.add(new Answer(doTheyReimburseTuition, Bool.FALSE));
        pool.add(smeltInc);
        langrsoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
        pool.add(langrsoft);

        pool.score(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));
        List<Profile> ranked = pool.ranked();

//        assertThat(ranked.toArray(), equalTo(new Profile[] {langrsoft, smeltInc}));
    }

    private Criteria soleNeed(Question question, int value, Weight weight) {
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, value), weight));
        return criteria;
    }

}