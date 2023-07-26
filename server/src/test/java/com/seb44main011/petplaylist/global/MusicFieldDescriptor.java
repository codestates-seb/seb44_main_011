package com.seb44main011.petplaylist.global;

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class MusicFieldDescriptor {
    protected static FieldDescriptor[] getPublicFieldDescriptor() {
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
    protected static FieldDescriptor[] getApiFieldDescriptor() {
        FieldDescriptor[] musicFieldDescriptor = {
                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("곡 식별 Id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("곡 이름"),
                fieldWithPath("music_url").type(JsonFieldType.STRING).description("곡 url"),
                fieldWithPath("image_url").type(JsonFieldType.STRING).description("이미지 url"),
                fieldWithPath("playtime").type(JsonFieldType.STRING).description("재생 시간"),
                fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
                fieldWithPath("tags").type(JsonFieldType.STRING).description("곡의 태그")
        };
        return musicFieldDescriptor;
    }
    protected static FieldDescriptor[] getAdminDescriptor() {
        FieldDescriptor[] musicFieldDescriptor = {
                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("곡 식별 Id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("곡 이름"),
                fieldWithPath("music_url").type(JsonFieldType.STRING).description("곡 url"),
                fieldWithPath("image_url").type(JsonFieldType.STRING).description("이미지 url"),
                fieldWithPath("playtime").type(JsonFieldType.STRING).description("재생 시간"),
                fieldWithPath("tags").type(JsonFieldType.STRING).description("곡의 태그"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("곡의 활성화 상태"),
        };
        return musicFieldDescriptor;
    }
    protected static FieldDescriptor[] getAdminDescriptorList() {
        FieldDescriptor[] musicFieldDescriptor = {
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 음악 데이터"),
                fieldWithPath("data.[].musicId").type(JsonFieldType.NUMBER).description("곡 식별 Id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("곡 이름"),
                fieldWithPath("data.[].music_url").type(JsonFieldType.STRING).description("곡 url"),
                fieldWithPath("data.[].image_url").type(JsonFieldType.STRING).description("이미지 url"),
                fieldWithPath("data.[].playtime").type(JsonFieldType.STRING).description("재생 시간"),
                fieldWithPath("data.[].tags").type(JsonFieldType.STRING).description("곡의 태그"),
                fieldWithPath("data.[].status").type(JsonFieldType.STRING).description("곡의 활성화 상태"),
                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("응답 페이지 데이터"),
                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 최대 데이터 수"),
                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 응답 데이터 수"),
                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
        };
        return musicFieldDescriptor;
    }
    protected static HeaderDescriptorWithType[] getHeaderDescriptorWithTypes(){
        return new HeaderDescriptorWithType[]{
                headerWithName("Authorization").description("accessToken"),
                headerWithName("Refresh").description("RefreshToken (accessToken 만료시 필수)").optional()
        };
    }

}
