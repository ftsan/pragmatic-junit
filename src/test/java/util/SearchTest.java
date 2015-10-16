package util;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import static util.ContainsMatches.*;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by futeshi on 2015/10/16.
 */
public class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @Before
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeResources() throws IOException {
        stream.close();
    }

    // コンテンツ中の文字列を検索し、コンテキストを含む結果を返す
    @Test
    public void returnsMatchesshowingContextWhenSearchStringInContent() {
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890"
                + "more rest of text");
        // 検索
        Search search = new Search(stream, "search term", A_TITLE);
        search.setSurroundingCharacterCount(10);

        search.execute();

        assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match(A_TITLE,
                        "search term",
                        "1234567890search term1234567890")}));
    }

    // コンテンツ中に文字列がない場合、空の結果を返す
    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() {
        stream = streamOn("any text");
        Search search = new Search(stream, "text that doesn't match", A_TITLE);

        search.execute();

        assertTrue(search.getMatches().isEmpty());
    }

    // ストリームから読み込めない場合にはerrordをtrueで返す
    @Test
    public void returnsErroredWhenUnableToReadStream() {
        stream = createStreamThrowingErrorWhenRead();
        Search search = new Search(stream, "", "");

        search.execute();

        assertTrue(search.errored());
    }

    // 読み込みに成功するとerroredはfalseを返す
    @Test
    public void erroredReturnsFalseWhenReadSucceeds() {
        stream = streamOn("");
        Search search = new Search(stream, "", "");

        search.execute();

        assertFalse(search.errored());
    }

    private InputStream createStreamThrowingErrorWhenRead() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}