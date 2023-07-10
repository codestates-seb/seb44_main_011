package com.seb44main011.petplaylist.domain.comment.stub;


import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentTestData {
    public static class MockComment {
        private static final String comment = "댓글 작성";

        public static Comment getCommentData() {
            return Comment.builder()
                    .commentId(1L)
                    .comment(comment)
                    .member(MemberTestData.MockMember.getMemberData())
                    .music(TestData.MockMusic.getMusicData())
                    .build();
        }
    }
}
