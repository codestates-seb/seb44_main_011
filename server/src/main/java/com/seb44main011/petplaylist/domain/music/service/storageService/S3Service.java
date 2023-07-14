package com.seb44main011.petplaylist.domain.music.service.storageService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seb44main011.petplaylist.domain.music.util.ByteArrayInputStreamUtil;
import com.seb44main011.petplaylist.domain.music.util.MP3DurationCalculator;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_MUSIC_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_IMG_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.MUSIC_FILE_TYPE;


@Service
public class S3Service extends ByteArrayInputStreamUtil implements StorageService<List<String>,List<MultipartFile>>{


    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.dns}")
    private String S3_SERVER_DNS;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.disable-path}")
    private String convertPath;

    private final Map<String,String> mapMusicUrl =new HashMap<>();

    public S3Service(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @SneakyThrows
    @Override
    public Map<String,String> saveUploadFile(List<MultipartFile> fileList) {
        for (MultipartFile multipartFile:fileList) {
            ObjectMetadata objectMetadata = getObjectMetadata(multipartFile);
            saveFileToS3(multipartFile, objectMetadata);
        }
        return mapMusicUrl;
    }

    @Override
    public Map<String,String> deactivateFile(List<String> urlList) {
        for (String convertUrl: urlList) {
            String deactivateFile= deactivateS3File(convertUrl);
            setMusicFileUrl(deactivateFile,MUSIC_FILE_TYPE);
        }
        return mapMusicUrl;
    }

    @SneakyThrows
    private String deactivateS3File(String url) {
        String getOldName = getOldFilePath(url);
        String getNewName = setConvertFilePath(getOldName);
        return moveS3(getOldName,getNewName);
    }


    private String setConvertFilePath(String musicUrl)  {
            return convertPath+musicUrl;
    }

    private String getOldFilePath(String musicUrl) {
        if (musicUrl.startsWith(S3_SERVER_DNS+BUCKET_IMG_PATH)){
            return musicUrl.replaceAll(".*/img/", BUCKET_IMG_PATH);
        }
        else if (musicUrl.startsWith(S3_SERVER_DNS+BUCKET_MUSIC_PATH)){
            return musicUrl.replaceAll(".*/music/", BUCKET_MUSIC_PATH);
        }
        throw new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND_INS3);
    }


    private void setMusicFileUrl(String key, String ext) {
        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        if (ext.equals(MUSIC_FILE_TYPE)){
            mapMusicUrl.put("music_url",storeFileUrl);
        }
        else {
            mapMusicUrl.put("image_url", storeFileUrl);
        }
    }

    private void saveFileToS3(MultipartFile multipartFile, ObjectMetadata objectMetadata) throws IOException, BitstreamException {
        String ext = getExt(multipartFile.getOriginalFilename());
        String key = getFileKeyName(ext);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            ByteArrayInputStream byteArrayInputStream = getByteArrayInputStream(inputStream);
            isMp3fileCheck(ext,byteArrayInputStream, multipartFile.getSize());

            amazonS3Client.putObject(new PutObjectRequest(bucket, key, byteArrayInputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            setMusicFileUrl(key,ext);
        }
    }

    private void isMp3fileCheck(String ext,ByteArrayInputStream byteArrayInputStream,long fileSize) throws BitstreamException {
        if (ext.equals(MUSIC_FILE_TYPE)){
            setMusicPlayTime(MP3DurationCalculator.getMp3Duration(fileSize,new Bitstream(byteArrayInputStream).readFrame()));
            byteArrayInputStream.reset();
        }

    }

    private void setMusicPlayTime(String mp3Duration) {
        mapMusicUrl.put("playtime",mp3Duration);
    }

    private String getFileKeyName(String ext) throws FileUploadException {

        String uuid = UUID.randomUUID().toString();
        if (ext.equals("mp3")){
            return BUCKET_MUSIC_PATH+uuid + "." + ext;
        }
        else if (ext.equals("png")||ext.equals("jpg")||ext.equals("jpeg")){
            return BUCKET_IMG_PATH+uuid + "." + ext;
        }
        throw new FileUploadException("올바르지 않은 파일 입니다");
    }

    private static String getExt(String originalFilename) {
        int index = Objects.requireNonNull(originalFilename).lastIndexOf(".");
        return originalFilename.substring(index + 1);
    }

    private static ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
    private String moveS3(String oldSource, String newSource){
        oldSource = URLDecoder.decode(oldSource, StandardCharsets.UTF_8);
        newSource = URLDecoder.decode(newSource, StandardCharsets.UTF_8);
        amazonS3Client.copyObject(bucket, oldSource, bucket, newSource);
        deleteS3(oldSource);
        return newSource;
    }

    private void deleteS3(String source) {

        amazonS3Client.deleteObject(bucket, source);
    }


}