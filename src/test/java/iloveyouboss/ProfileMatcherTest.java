package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class ProfileMatcherTest {
    private BooleanQuestion question;
    private Criteria criteria;
    private ProfileMatcher matcher;
    private Profile matchingProfile;
    private Profile noMatchingProfile;
    private MatchListener listener;

    @Before
    public void create() {
        question = new BooleanQuestion(1, "");
        criteria = new Criteria();
        criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
        matchingProfile = createMatchingProfile("matching");
        noMatchingProfile = createNoMatchingProfile("noMatching");
    }

    @Before
    public void createMatchListener() {
        listener = mock(MatchListener.class);
    }

    // マッチするとprocessはリスナーに通知する
    @Test
    public void processNotifiesListenerOnMatch() {
        matcher.add(matchingProfile);
        MatchSet set = matchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener).foundMatch(matchingProfile, set);
    }

    // マッチしない場合はrocessはリスナーに通知しない
    @Test
    public void processDoesNotNotifiesListenerNoMatch() {
        matcher.add(matchingProfile);
        MatchSet set = noMatchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener, never()).foundMatch(noMatchingProfile, set);
    }

    @Test
    public void gathersMatchingProfiles() {
        Set<String> processedSets = Collections.synchronizedSet(new HashSet<>());
        BiConsumer<MatchListener, MatchSet> processFunction =
                (listener, set) -> {
                    processedSets.add(set.getProfileId());
                };
        List<MatchSet> matchSets = createMatchSets(100);

        matcher.findMatchingProfiles(criteria, listener, matchSets, processFunction);

        while(!matcher.getExecutor().isTerminated());

        assertThat(processedSets, equalTo(matchSets.stream().map(MatchSet::getProfileId).collect(Collectors.toSet())));
    }

    private List<MatchSet> createMatchSets(int count) {
        List<MatchSet> sets = new ArrayList<>();
        for(int i = 0; i < count; i++ ) {
            sets.add(new MatchSet(String.valueOf(i), null, null));
        }
        return sets;
    }

    private Profile createMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(matchingAnswer());
        return profile;
    }

    private Profile createNoMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(noMatchingAnswer());
        return profile;
    }

    @Before
    public void createMatcher() {
        matcher = new ProfileMatcher();
    }

    @Test
    public void collectMatchSets() {
        matcher.add(matchingProfile);
        matcher.add(noMatchingProfile);

        List<MatchSet> sets = matcher.collectMatchSets(criteria);

        assertThat(sets.stream().map(set -> set.getProfileId()).collect(Collectors.toSet()),
                equalTo(new HashSet<>(Arrays.asList(matchingProfile.getId(), noMatchingProfile.getId()))));
    }

    public Answer matchingAnswer() {
        return new Answer(question, Bool.TRUE);
    }

    public Answer noMatchingAnswer() {
        return new Answer(question, Bool.FALSE);
    }
}