package tdd.iloveyouboss;

import static org.junit.Assert.*;

import iloveyouboss.Answer;
import iloveyouboss.Bool;
import iloveyouboss.BooleanQuestion;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 2015/10/16.
 */
public class AnswerTest {
    // 空の回答に対するマッチングはfalseを返す
    @Test
    public void matchAgainstNullAnswerReturnFalse() {
        assertFalse(new Answer(new BooleanQuestion(0, ""), Bool.TRUE).match(null));
    }
}