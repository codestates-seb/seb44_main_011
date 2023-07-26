package com.seb44main011.petplaylist.domain.music.service.storageService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seb44main011.petplaylist.domain.music.entity.Music;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_MUSIC_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_IMG_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.MUSIC_FILE_TYPE;


@Service
@Transactional
public class S3Service extends ByteArrayInputStreamUtil implements StorageService<Music,List<MultipartFile>>{


    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.dns}")
    private  String S3_SERVER_DNS;
    @Value("${cloud.aws.s3.bucket}")
    private  String bucket;
    @Value("${cloud.aws.s3.disable-path}")
    private  String disablePath;
    @Value("${cloud.aws.s3.enable-path}")
    private  String enablePath;

    private String EXT;


    public S3Service(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @SneakyThrows
    @Override
    public void saveUploadFile(List<MultipartFile> fileList,Music music) {
        for (MultipartFile multipartFile:fileList) {
            setExt(multipartFile.getOriginalFilename());
            String key = getFileKeyName(music);
            checkFile(multipartFile,key,music);
            setMusicFileUrl(key,music);
        }

    }

    @Override
    public void convertFileStatus(Music music) {
        List<String> urlList= List.of(music.getMusic_url(),music.getImage_url());
        for (String convertUrl: urlList) {
            setExt(convertUrl);
            String convertFile= convertS3File(convertUrl,music.getStatus());
            setMusicFileUrl(convertFile,music);
        }
    }

    @SneakyThrows
    private String convertS3File(String url,Music.Status status) {
        String getOldName = getOldFilePath(url);
        String getNewName = setConvertFilePath(getOldName,status);
        return moveS3(getOldName,getNewName);
    }


    private String setConvertFilePath(String musicUrl,Music.Status status)  {
        if (status.equals(Music.Status.ACTIVE)) {
            if (musicUrl.startsWith("enable/")) {
                return musicUrl.replaceAll(".*enable/", disablePath);
            }
            return disablePath+musicUrl;
        }
        else {
            if (musicUrl.startsWith("disable/")) {
                return musicUrl.replaceAll(".*disable/", enablePath);
            }
        }
        return enablePath+musicUrl;

    }

    private String getOldFilePath(String musicUrl) {
        String musicFilePath = S3_SERVER_DNS+BUCKET_MUSIC_PATH;
        String imgFilePath = S3_SERVER_DNS+BUCKET_IMG_PATH;
        String enableFilePath = S3_SERVER_DNS+enablePath;
        String disableFilePath = S3_SERVER_DNS+disablePath;
        if(musicUrl.startsWith(enableFilePath)){
            return musicUrl.replaceAll(".*/enable/", enablePath);
        } else if (musicUrl.startsWith(disableFilePath)) {
            return musicUrl.replaceAll(".*/disable/", disablePath);
        } else if (musicUrl.startsWith(imgFilePath)){
            return musicUrl.replaceAll(".*/img/", BUCKET_IMG_PATH);
        }
        else if (musicUrl.startsWith(musicFilePath)){
            return musicUrl.replaceAll(".*/music/", BUCKET_MUSIC_PATH);
        }
        else {
            throw new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND_INS3);
        }
    }


    private void setMusicFileUrl(String key,Music music) {
        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        if (EXT.equals(MUSIC_FILE_TYPE)){
            music.insertMusic_url(storeFileUrl);
        }
        else {
            music.insertImage_url(storeFileUrl);
        }
    }

    private void checkFile(MultipartFile multipartFile, String key,Music music) throws IOException, BitstreamException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            ByteArrayInputStream byteArrayInputStream = getByteArrayInputStream(inputStream);
            if (EXT.equals(MUSIC_FILE_TYPE)){
                long fileSize =multipartFile.getSize();
                setMusicPlayTime(music,MP3DurationCalculator.getMp3Duration(fileSize,new Bitstream(byteArrayInputStream).readFrame()));
                byteArrayInputStream.reset();
            }
            putFileToS3(key, byteArrayInputStream,getObjectMetadata(multipartFile));

        }
    }

    private void putFileToS3(String key, ByteArrayInputStream byteArrayInputStream,ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, key, byteArrayInputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private void setMusicPlayTime(Music music,String mp3Duration) {
        music.insertPlaytime(mp3Duration);
    }

    private String getFileKeyName(Music music) throws FileUploadException {
        String musicIdentifyPath = music.getTitle()+UUID.randomUUID();
        if (EXT.equals("mp3")){
            return BUCKET_MUSIC_PATH+musicIdentifyPath + "." + EXT;
        }
        else if (EXT.equals("png")||EXT.equals("jpg")||EXT.equals("jpeg")){
            return BUCKET_IMG_PATH+musicIdentifyPath + "." + EXT;
        }
        throw new FileUploadException("올바르지 않은 파일 입니다");
    }

    private void setExt(String originalFilename) {
        int index = Objects.requireNonNull(originalFilename).lastIndexOf(".");
        this.EXT = originalFilename.substring(index + 1);
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

    private String uploadImg(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}