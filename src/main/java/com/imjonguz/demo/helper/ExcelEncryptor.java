package com.imjonguz.demo.helper;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

@Service
public class ExcelEncryptor {

    final String PATH = "src/main/resources/excel-files/";

    public Boolean encrypt(MultipartFile file, String password){
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
        org.apache.poi.poifs.crypt.Encryptor enc = info.getEncryptor();
        enc.confirmPassword(password);

        try (OPCPackage opc = OPCPackage.open(file.getInputStream());
             OutputStream os = enc.getDataStream(fs)) {
            opc.save(os);
            os.close();

            FileOutputStream fos = new FileOutputStream("Encrypted" + file.getOriginalFilename());
            fs.writeFilesystem(fos);
            fos.close();

            return true;

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyFileException efe){
            efe.printStackTrace();
        } catch (OLE2NotOfficeXmlFileException ele2exc){
            ele2exc.printStackTrace();
            return false;
        } catch (Exception e){
            return false;
        }
        return false;
    }

}
