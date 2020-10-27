package com.imjonguz.demo.controller;


import com.imjonguz.demo.helper.DownloadHelper;
import com.imjonguz.demo.helper.ExcelEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class ExcelController {

    @Autowired
    ExcelEncryptor encryptor;

    @Autowired
    DownloadHelper downloadHelper;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) throws IOException {
        try {
            if(encryptor.encrypt(file, password)){
                //return new ResponseEntity("Message from server: File encrypted", HttpStatus.OK);
                return downloadHelper.createDownloadContent("Encrypted" + file.getOriginalFilename());
            }

            return new ResponseEntity("Message from server: Could not encrypt file. Support files are: .xlsx. " +
                    "Can not encrypt already encrypted files.", HttpStatus.BAD_REQUEST);
        } finally {
            File f = new File("Encrypted" + file.getOriginalFilename());
            f.delete();
        }

    }

}
