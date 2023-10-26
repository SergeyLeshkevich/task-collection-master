package by.clevertec;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MainTest {

    String consoleOutput;
    PrintStream originalOut;
    ByteArrayOutputStream outputStream;
    PrintStream capture;

    @BeforeEach
    public void init(){
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream(100);
        capture = new PrintStream(outputStream);
    }

    @Test
    void task20() {
        System.setOut(capture);
        Main.task20();
        capture.flush();
        consoleOutput = outputStream.toString();
        System.setOut(originalOut);
        String print = "Faculty with the highest average score on the first exam:Mathematics";
        assertEquals(print, consoleOutput);
    }

    @AfterEach
    public void close(){
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        capture.close();
    }
}