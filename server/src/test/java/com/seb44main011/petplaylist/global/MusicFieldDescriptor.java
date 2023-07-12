package com.seb44main011.petplaylist.global;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class MusicFieldDescriptor {
    protected static FieldDescriptor[] getMusicFieldDescriptor() {
        FieldDescriptor[] musicFieldDescriptor = {
                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("곡 식별 Id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("곡 이름"),
                fieldWithPath("music_url").type(JsonFieldType.STRING).description("곡 url"),
                fieldWithPath("image_url").type(JsonFieldType.STRING).description("이미지 url"),
                fieldWithPath("playtime").type(JsonFieldType.STRING).description("재생 시간"),
                fieldWithPath("tags").type(JsonFieldType.STRING).description("곡의 태그")
        };
        return musicFieldDescriptor;
    }

}
