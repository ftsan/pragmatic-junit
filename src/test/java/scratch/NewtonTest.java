package scratch;

import static org.junit.Assert.*;

import org.junit.*;

import static org.hamcrest.number.IsCloseTo.*;

/**
 * Created by futeshi on 15/10/11.
 */
public class NewtonTest {
    static class Newton {
        private static final double TOLERANCE = 1E-16;

        public static double squareRoot(double n) {
            double approx = n;
            while (Math.abs(approx - n / approx) > TOLERANCE * approx) {
                approx = (n / approx + approx) / 2.0;
            }
            return approx;
        }
    }

    @Test
    public void squareRoot() {
        double result = Newton.squareRoot(250.0);
        assertThat(result * result, closeTo(250.0, Newton.TOLERANCE));
    }

}