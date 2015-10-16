package iloveyouboss;

import static org.junit.Assert.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * Created by futeshi on 15/10/11.
 */
public class ScoreCollectionTest {

    ScoreCollection collection;
    @Before
    public void setup() {
        collection = new ScoreCollection();
    }

    // 2つの数値の算術平均を返す
    @Test
    public void answerArithmeticMeanOfTwoNumbers() {
        collection.add(() -> 5);
        collection.add(() -> 7);

        int actualResult = collection.arithmeticMean();

        assertThat(actualResult, equalTo(6));
    }

    // nullを追加すると例外が発生する
    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAddingNull() {
        collection.add(null);
    }

    // 何も追加されていない場合は0を返す
    @Test
    public void answersZeroWhenElementsAdded() {
        assertThat(collection.arithmeticMean(), equalTo(0));
    }

    // Integerとしてのオーバーフローに対処する
//    @Test
//    public void dealsWithIntegerOverFlow() {
//        collection.add(() -> Integer.MAX_VALUE);
//        collection.add(() -> 1);
//
//        assertThat(collection.arithmeticMean(), equalTo(1073741824));
//    }

    // Integerとしてのオーバーフローには対処しない
    @Test
    public void doesNotProperlyHandleIntegerOverFlow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        assertTrue(collection.arithmeticMean() < 0);
    }
}