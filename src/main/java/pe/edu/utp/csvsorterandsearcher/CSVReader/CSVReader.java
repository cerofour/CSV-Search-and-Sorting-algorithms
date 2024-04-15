package pe.edu.utp.csvsorterandsearcher.CSVReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;

public class CSVReader {
    FileInputStream file;
    BufferedReader reader;

    boolean use_file_headers;

    CSVReader(String path) {
        try {
            this.file = new FileInputStream(path);
            this.reader = new BufferedReader(reader);
            use_file_headers = false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
