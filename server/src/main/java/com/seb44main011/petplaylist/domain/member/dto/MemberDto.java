package com.seb44main011.petplaylist.domain.member.dto;

import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MemberDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpPost {
        @Email(message = "이메일 형식이여야 합니다.")
        @NotBlank(message = "공백이 아니여야 합니다.")
        private String email;

        @NotBlank(message = "공백이 아니여야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$",
                 message = "비밀번호는 영문자 1개와 숫자 1개를 포함해야하며, 8 ~ 16자 사이여야 합니다.")
        private String password;

        @NotBlank(message = "공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,7}$",
                 message = "닉네임은 2 ~ 7자 사이여야 합니다.")
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class LogInPost {
        @Email(message = "이메일 형식이여야 합니다.")
        @NotBlank(message = "공백이 아니여야 합니다.")
        private String email;

        @NotBlank(message = "공백이 아니여야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$",
                message = "비밀번호는 영문자 1개와 숫자 1개를 포함해야하며, 8 ~ 16자 사이여야 합니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {
        @NotBlank(message = "공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,7}$",
                message = "닉네임은 2 ~ 7자 사이여야 합니다.")
        private String name;

        private String profileUrl;
    }

    @Getter
    @Builder
    public static class MyPageResponse {
        private String email;
        private String name;
        private String profileUrl;
        private List<PlaylistDto.PublicResponse> musicLists;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Delete {
        @NotBlank(message = "공백이 아니여야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$",
                message = "비밀번호는 영문자 1개와 숫자 1개를 포함해야하며, 8 ~ 16자 사이여야 합니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpResponse {
        private long memberId;
        private String email;
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LogInResponse {
        private long memberId;
        private String email;
        private String role;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PatchResponse {
        private String email;
        private String name;
        private String profileUrl;
    }
}
