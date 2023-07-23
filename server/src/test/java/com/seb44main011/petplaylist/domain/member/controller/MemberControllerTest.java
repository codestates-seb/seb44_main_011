package com.seb44main011.petplaylist.domain.member.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.mapper.MemberMapper;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.access.method.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();
    @MockBean
    private  MemberService memberService;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private S3Service s3service;
    private Member testMember;
    private String memberContext;

    @BeforeEach
    public void initTest() {
        this.testMember = MemberTestData.MockMember.getMemberData();
        this.memberContext = gson.toJson(MemberTestData.MockMember.getSignUpPostData());
    }

    @Test
    @DisplayName("회원가입 테스트")
    @WithMockUser
    public void postMemberTest() throws Exception {
        BDDMockito.given(memberService.createMember(Mockito.any(Member.class))).willReturn(testMember);
        BDDMockito.given(memberMapper.memberDtoSignUpPostToMember(Mockito.any(MemberDto.SignUpPost.class))).willReturn(testMember);
        BDDMockito.given(memberMapper.memberToMemberDtoSignUpResponse(Mockito.any(Member.class))).willReturn(MemberTestData.MockMember.getSignUpResponse());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/public/signup")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(memberContext))

                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(
                        MockMvcRestDocumentation.document("회원가입 예제",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("회원 가입")
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                                        PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("닉네임")
                                                )
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                                                        PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("닉네임")
                                                )
                                                .build()
                                )
                        )

                );
    }

    @Test
    @DisplayName("회원정보 수정 테스트")
    @WithMockUser
    public void patchMemberTest() throws Exception {
        String context = gson.toJson(MemberDto.Patch.builder()
                .name("내가진짜홍길동")
                .profileUrl("url-2")
                .build());
        MemberDto.PatchResponse response = MemberDto.PatchResponse.builder()
                .email(MemberTestData.MockMember.getMemberData().getEmail())
                .name("내가진짜홍길동")
                .profileUrl("url-2")
                .build();
        BDDMockito.given(memberService.updateMember(Mockito.anyLong(), Mockito.any(MemberDto.Patch.class))).willReturn(testMember);
        BDDMockito.given(memberMapper.memberToMemberDtoPatchResponse(Mockito.any(Member.class))).willReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/members/my-page/{member-id}", testMember.getMemberId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(context)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("회원정보 수정 테스트",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .description("회원 정보 수정")
                                        .pathParameters(
                                                ResourceDocumentation.parameterWithName("member-id").description("사용자 식별자")
                                        )
                                        .requestFields(
                                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("닉네임"),
                                                PayloadDocumentation.fieldWithPath("profileUrl").type(JsonFieldType.STRING).description("프로필 이미지")
                                        )
                                        .responseFields(
                                                PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("닉네임"),
                                                PayloadDocumentation.fieldWithPath("profileUrl").type(JsonFieldType.STRING).type(JsonFieldType.STRING).description("변경된 프로필 이미지")
                                        )
                                        .build()
                        )
                )
                );
    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    @WithMockUser
    public void deleteMemberTest() throws Exception {
        String password = gson.toJson(MemberDto.Delete.builder()
                .password("a12341234")
                .build());
        BDDMockito.given(memberService.findMember(Mockito.anyLong())).willReturn(testMember);
        Mockito.doNothing().when(memberService).disableMember(Mockito.anyLong(), Mockito.anyString());

        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/members/{member-id}", testMember.getMemberId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(password)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("회원탈퇴 예제",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("회원탈퇴")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("member-id").description("사용자 식별자")
                                                )
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("password").description("비밀번호")
                                                )
                                                .responseFields()
                                                .build()
                                )
                        )
                );
    }

    @Test
    @DisplayName("회원정보 조회 테스트")
    @WithMockUser
    public void getMyPageTest() throws Exception {
        BDDMockito.given(memberService.findMember(Mockito.anyLong())).willReturn(testMember);
        BDDMockito.given(memberMapper.memberToMyPageResponse(testMember)).willReturn(MemberTestData.getMyPageResponse());

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/members/my-page/{member-id}", testMember.getMemberId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("회원조회 예제",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("회원조회")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("member-id").description("사용자 식별자")
                                                )
                                                .requestFields()
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("name").description("닉네임"),
                                                        PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                        PayloadDocumentation.fieldWithPath("profileUrl").description("프로파일 이미지"),
                                                        PayloadDocumentation.fieldWithPath("musicLists").type(JsonFieldType.ARRAY).description("뮤직리스트"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].musicId").description("음악 식별자"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].title").description("곡 이름"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].music_url").description("곡 저장 위치"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].image_url").description("곡 이미지 저장 위치"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].playtime").description("곡 재생 시간"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].category").description("카테고리"),
                                                        PayloadDocumentation.fieldWithPath("musicLists.[].tags").description("태그")
                                                )
                                                .build()
                                )
                        )
                );
    }

    @Test
    @DisplayName("프로필 이미지 리스트 조회")
    @WithMockUser
    public void getProfileImageTest() throws Exception {
        List<String> profileImageList = new ArrayList<>();
        for (Member.Profile profile : Member.Profile.values()) {
            profileImageList.add(profile.getProfileUrl());
        }
        BDDMockito.given(memberService.findProfileImage()).willReturn(profileImageList);

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/members/my-page/profiles")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "프로필 이미지 리스트 조회 예제",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("프로필 이미지")
                                                .requestFields()
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("[]").description("프로필 이미지 리스트")
                                                )
                                                .build()
                                )
                        )
                );
    }
}
