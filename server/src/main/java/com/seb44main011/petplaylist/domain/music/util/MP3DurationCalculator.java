package com.seb44main011.petplaylist.domain.music.util;


import javazoom.jl.decoder.Header;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MP3DurationCalculator {
    public static String getMp3Duration(long fileSize,Header header){
        try {

            //음원의 재생 시간 구하는 방법 -> 파일 바이트 * 8 / bitRate
            long playTime =fileSize*8/header.bitrate();

            int durationInMinutes = (int) (playTime / 60);
            int durationInSecondsRemaining = (int) (playTime % 60);

            log.info("MP3 재생 시간 {}분 {}초", durationInMinutes,durationInSecondsRemaining);
            return durationInSecondsRemaining <10 ?
                    durationInMinutes+":0"+durationInSecondsRemaining : durationInMinutes+":"+durationInSecondsRemaining;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(" ");

        }
    }
}
