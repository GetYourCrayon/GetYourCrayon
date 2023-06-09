package com.sevenight.coldcrayon.game.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.util.Base64;

@Slf4j
@Service
public class SaveImageServiceImpl {

    @Value("${java.file.homeUrl}")
    String HomeUrl;
 
    public String downloadImage(String imageUrl, String destinationPath, Long idx) throws IOException {
        String prefix = "/getchacrayon/image/history/";
        destinationPath = prefix + destinationPath;

        File file = new File(destinationPath);
        if (!file.exists()) {
            file.mkdirs(); // 디렉토리가 없으면 해당 경로의 모든 디렉토리를 생성합니다.
        }

        URL url = new URL(imageUrl);
        FileOutputStream outputStream = new FileOutputStream(destinationPath + "/" +idx + ".jpg");
        IOUtils.copy(url.openStream(), outputStream);
        outputStream.close();
        return HomeUrl + destinationPath + "/" +idx + ".jpg";
    }
    // destinationPath == roomIdx + gameNumber + roundNumber;
    public void saveCatchMind(String base64Data, String destinationPath, Long idx) throws IOException {

        String prefix = "/getchacrayon/image/history/";
        destinationPath = prefix + destinationPath;
        File file = new File(destinationPath);

        if (!file.exists()) {
            file.mkdirs(); // 디렉토리가 없으면 해당 경로의 모든 디렉토리를 생성합니다.
        }
        System.err.println("여기까진 온다는 건가");

        byte[] decodedBytes = Base64.getDecoder().decode(base64Data.replace("data:image/png;base64,",""));

        FileOutputStream outputStream = new FileOutputStream(destinationPath + "/" +idx + ".png");
        outputStream.write(decodedBytes);

//        byte[] buffer = new byte[4096];
//        int bytesRead;
//        while ((bytesRead = base64Data.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
        outputStream.close();
        outputStream.close();
//        return HomeUrl + destinationPath + "/" +idx + ".png";
    }

}
