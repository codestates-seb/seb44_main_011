package com.seb44main011.petplaylist.domain.comment.stub;


import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.stub.MusicTestData;

public class CommentTestData {
    public static class MockComment {
        private static final String comment = "댓글 작성";

        public static Comment getCommentData() {
            return Comment.builder()
                    .commentId(1L)
                    .comment(comment)
                    .member(MemberTestData.MockMember.getMemberData())
                    .music(MusicTestData.MockMusic.getMusicData())
                    .build();
        }
    }
}
