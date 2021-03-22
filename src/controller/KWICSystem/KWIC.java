package controller.KWICSystem;

import java.io.IOException;

public interface KWIC {
    String transform (String inputString) throws IOException;
    public String loadTestFile();
    public String[] runBenchmark(String inputString);
}
