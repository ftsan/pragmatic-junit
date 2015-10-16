package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 15/10/11.
 */
public class ProfileTest {
    private Profile profile;

    @Before
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream()
                .mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        profile.add(new Answer(new PercentileQuestion(2, "2", new String[] {}), 0));
        profile.add(new Answer(new PercentileQuestion(3, "3", new String[] {}), 0));

        List<Answer> answers =
                profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);

        assertThat(ids(answers), equalTo(new int[] { 2, 3 }));
    }
    // TODO: missing functionality--what if there is no matching profile answer for a criterion?
}