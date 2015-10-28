package iloveyouboss.controller;

import static org.junit.Assert.*;

import iloveyouboss.domain.Question;
import org.junit.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 2015/10/20.
 */
public class QuestionControllerTest {
    private QuestionController controller;

    @Before
    public void create() {
        controller = new QuestionController();
        controller.deleteAll();
    }

    @After
    public void cleanUp() {
        controller.deleteAll();
    }

    // IDを使って、永続化された質問を検索する
    @Test
    public void findsPersistedQuestionById() {
        int id = controller.addBooleanQuestion("質問の文字列");

        Question question = controller.find(id);

        assertThat(question.getText(), equalTo("質問の文字列"));
    }

    // Questionは自らが生成された時刻を返す
    @Test
    public void questionAnswersDateAdded() {
        Instant now = new Date().toInstant();
        controller.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
        int id = controller.addBooleanQuestion("文字列");

        Question question = controller.find(id);

        assertThat(question.getCreateTimestamp(), equalTo(now));
    }

    // 複数の永続化された質問を返す
    @Test
    public void answersMultiplePersistedQuestions() {
        controller.addBooleanQuestion("q1");
        controller.addBooleanQuestion("q2");
        controller.addPercentileQuestion("q3", new String[]{"a1", "a2"});

        List<Question> questions = controller.getAll();

        assertThat(questions.stream().map(Question::getText).collect(Collectors.toList()),
                equalTo(Arrays.asList("q1", "q2", "q3")));
    }

    // マッチする質問を検索する
    @Test
    public void findsMatchingEntries() {
        controller.addBooleanQuestion("alpha 1");
        controller.addBooleanQuestion("alpha 2");
        controller.addBooleanQuestion("bata 1");

        List<Question> questions = controller.findWithMatchingText("alpha");

        assertThat(questions.stream().map(Question::getText).collect(Collectors.toList()),
                equalTo(Arrays.asList("alpha 1", "alpha 2")));
    }
}