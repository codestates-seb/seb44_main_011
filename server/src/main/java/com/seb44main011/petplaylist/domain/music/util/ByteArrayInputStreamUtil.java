package com.seb44main011.petplaylist.domain.music.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStreamUtil {
    protected static ByteArrayInputStream getByteArrayInputStream(InputStream inputStream) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getBytesFromInputStream(inputStream));
        byteArrayInputStream.mark(0);
        return byteArrayInputStream;
    }
    protected static byte[] getBytesFromInputStream(InputStream inputStream) {
        // InputStream 으로부터 데이터를 읽어서 바이트 배열로 반환하는 로직을 구현
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
