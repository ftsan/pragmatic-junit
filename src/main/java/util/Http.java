package util;

import java.io.*;

public interface Http {
    String get(String url) throws IOException;
}