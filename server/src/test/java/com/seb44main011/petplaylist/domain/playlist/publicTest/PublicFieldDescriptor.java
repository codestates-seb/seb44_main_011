package com.seb44main011.petplaylist.domain.playlist.publicTest;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class PublicFieldDescriptor {
    protected static FieldDescriptor[] getPublicPlayListPage() {
        FieldDescriptor[] playListPage = {fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                fieldWithPath("data.[].musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("음악 타이틀(제목)"),
                fieldWithPath("data.[].music_url").type(JsonFieldType.STRING).description("음악의 URL"),
                fieldWithPath("data.[].image_url").type(JsonFieldType.STRING).description("음악 이미지의 URL"),
                fieldWithPath("data.[].playtime").type(JsonFieldType.STRING).description("재생시간"),
                fieldWithPath("data.[].category").type(JsonFieldType.STRING).description("조회한 카테고리"),
                fieldWithPath("data.[].tags").type(JsonFieldType.STRING).description("조회한 태그"),
                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("한 페이지에 속하는 데이터 개수"),
                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수")};
        return playListPage;
    }
}
