package com.seb44main011.petplaylist.domain.music.constant;

import org.springframework.beans.factory.annotation.Value;

public final class StorageConstants {
        private StorageConstants(){};
//        @Value("${cloud.aws.s3.dns}")
//        public static String S3_SERVER_DNS;
        public static final String BUCKET_MUSIC_PATH = "music/";
        public static final String BUCKET_IMG_PATH = "img/";
        public static final String MUSIC_FILE_TYPE="mp3";
        public static final String[] IMG_FILE_TYPE={"png","jpg","jpeg"};

}
