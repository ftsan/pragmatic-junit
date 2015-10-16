package scratch;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 15/10/12.
 */
public class BearingTest {
    // 負の値を設定すると例外が発生する
    @Test(expected = BearingOutOfRangeException.class)
    public void throwsOnNagativeNumber() {
        new Bearing(-1);
    }

    // 大きすぎる値を指定すると例外が発生する
    @Test(expected = BearingOutOfRangeException.class)
    public void throwsWhenBearingTooLarge() {
        new Bearing(Bearing.MAX + 1);
    }

    // 正当なBearingを返す
    @Test
    public void answersValidBearing() {
        assertThat(new Bearing(Bearing.MAX).value(), equalTo(Bearing.MAX));
    }

    // 別の方位との間の角度を返す
    @Test
    public void answerAngleBetweenItAndAnotherBearing() {
        assertThat(new Bearing(15).angleBetween(new Bearing(12)), equalTo(3));
    }

    // 自身より大きい値の方位に対しては負の角度を返す
    @Test
    public void angleBetweenIsNegativeWhenThisBearingSmaller() {
        assertThat(new Bearing(12).angleBetween(new Bearing(15)), equalTo(-3));
    }

}