package com.imjonguz.demo.helper;

import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class DownloadHelper {

    final String PATH = "src/main/resources/excel-files/";

    public ResponseEntity<byte[]> createDownloadContent(String filename) throws IOException {
        InputStream is = new FileInputStream(filename);
        try {
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Encrypted_send.xlsx");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            return new ResponseEntity<byte[]>(IOUtils.toByteArray(is), header, HttpStatus.CREATED);


        } catch (FileNotFoundException e){
            System.out.println("File could not be found.");
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            is.close();
        }

        return new ResponseEntity(HttpStatus.CONFLICT);
    }

}
