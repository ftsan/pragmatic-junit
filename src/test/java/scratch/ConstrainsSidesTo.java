package scratch;

import static org.junit.Assert.*;

import org.junit.*;
import org.hamcrest.*;

/**
 * 実践Junitから写経したもの。
 * カスタムマッチャーの実装。
 * クラス名はマッチャーのメソッド名に合わせるのが一般的。
 */
public class ConstrainsSidesTo extends TypeSafeMatcher<Rectangle> {
    private int length;

    public ConstrainsSidesTo(int length) {
        this.length = length;
    }

    /**
     * アサーションが失敗した場合に表示されるメッセージを記述する。
     *
     * @param description
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("各辺の長さは " + length + "以下でなければなりません");
    }

    /**
     * 適用しようとしている制約をこのメソッドに記述する。
     *
     * @param rectangle
     * @return 制約が満たされた場合にtrue
     */
    @Override
    protected boolean matchesSafely(Rectangle rectangle) {
        return Math.abs(rectangle.origin().x - rectangle.opposite().x) <= length &&
               Math.abs(rectangle.origin().y - rectangle.opposite().y) <= length;
    }

    /**
     * アサーションを呼び出す際に使用されるメソッド。
     *
     * @param length
     * @param <T>
     * @return
     */
    @Factory
    public static <T> Matcher<Rectangle> constrainsSidesTo(int length) {
        return new ConstrainsSidesTo(length);
    }
}