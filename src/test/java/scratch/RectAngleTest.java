package scratch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.*;

import static scratch.ConstrainsSidesTo.constrainsSidesTo;

/**
 * Created by futeshi on 15/10/12.
 */
public class RectAngleTest {
    private Rectangle rectangle;

    // 制約を満たしている
    @After
    public void ensureInvariant() {
        // 下記のテストは失敗する
//        assertThat(rectangle, constrainsSidesTo(100));
    }

    // 面積を返す
    @Test
    public void answersArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area(), equalTo(50));
    }

    // 動的に座標を変更できる
    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(130, 130));
        assertThat(rectangle.area(), equalTo(15625));
    }

}