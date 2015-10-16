package util;

import org.junit.*;
import scratch.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SparseArrayTest {
    private SparseArray<Object> array;

    @Before
    public void create() {
        array = new SparseArray<>();
    }

    @Test
    public void handlesInsertionInDescendingOrder() {
        array.put(7, "七");
        array.checkInvariants();
        array.put(6, "六");
        array.checkInvariants();
        assertThat(array.get(6), equalTo("六"));
        assertThat(array.get(7), equalTo("七"));
    }

}