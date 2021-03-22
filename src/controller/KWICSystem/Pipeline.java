package controller.KWICSystem;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Stopwatch;


public class Pipeline implements KWIC{

    public String transform (String inputString) throws IOException {

        //Create a Reader to read data from, and a Writer to send data to
        //Create an outputStream to hold the data
        Reader in = new BufferedReader(new StringReader(inputString));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));

        // Now build up the pipe, starting with the sink, and working backwards,
        // through various filters, until we reach the source.
        Printer output = new Printer(out);
        Filter alphabetizer = new Sorter(output);
        Filter circularShift = new Shifter(alphabetizer);
        Loader input = new Loader(circularShift, in);

        // Start the pipe -- start each of the threads in the pipe running.
//        System.out.println("Starting pipe...");
        input.startPipe();

        // Wait for the pipe to complete
        try {
            input.joinPipe();
        } catch (InterruptedException e) {
            System.out.println("Cannot join pipes");}

        // Dump the data from outputStream to a string
        String outputString = new String(outputStream.toByteArray());
//        System.out.println(outputString);
//        System.out.println("Done.");

        return outputString;
    }

    public String loadTestFile () {
        String fileContent = "";
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            fileContent = new Scanner(new File("test_data/test-data-fu.txt")).useDelimiter("\\Z").next();
        }
        catch (IOException e) {
            System.out.println("ERROR in loadTestFile(): " + e);
        }
        return fileContent;
    }

    public String[] runBenchmark(String inputString) {
        int iterations = 1000;
        String[] results = new String[3];
        String outputString = "";

        // Start timing
        Stopwatch stopwatch = Stopwatch.createStarted();

        /* ... the code being measured starts ... */
        for (int i = 0; i < iterations; i++) {
            try {
                outputString = transform(inputString);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        /* ... the code being measured ends ... */
        stopwatch.stop();

        // get elapsed time, expressed in milliseconds
        long timeElapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        results[0] = outputString;
        results[1] = String.valueOf(iterations);
        results[2] = String.valueOf(timeElapsed);

        return results;
    }
}
