package com.seb44main011.petplaylist.domain.music.service.storageService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seb44main011.petplaylist.domain.music.util.ByteArrayInputStreamUtil;
import com.seb44main011.petplaylist.domain.music.util.MP3DurationCalculator;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_MUSIC_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.BUCKET_IMG_PATH;
import static com.seb44main011.petplaylist.domain.music.constant.StorageConstants.MUSIC_FILE_TYPE;


@Service
public class S3Service extends ByteArrayInputStreamUtil implements StorageService{


    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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


}