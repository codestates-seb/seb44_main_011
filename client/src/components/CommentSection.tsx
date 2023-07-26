import { styled } from "styled-components";
import Profile from "./commons/Profile";
import { calculateTimeAgo } from "../utils/calculateTimeAgo";
import { ReactComponent as EditIcon } from "../assets/icons/editicon.svg";
import { ReactComponent as DeleteIcon } from "../assets/icons/deleteicon.svg";
import useCommentData from "../hooks/useCommentData";
import Pagination from "./Pagination";
import { useState, useEffect, useRef } from "react";
import { CommentData } from "../types/Comment";
import Empty from "./Empty";
import { api } from "../utils/Url";
import saveNewToken from "../utils/saveNewToken";

const CommentListContainer = styled.div`
  border-radius: 15px;
  position: relative;
  display: flex;
  flex-direction: column;
  color: var(--white);
  font-size: 13px;
  font-family: var(--font-quicksand);
  height: 420px;
  flex-shrink: 0;
  background: 10px;
`;
const CommentContainer = styled.div`
  display: flex;
  flex-direction: column;
  background-color: rgba(0, 0, 0, 0.1);
  border-radius: 15px;
  margin: 10px 30px 2%;
`;

const CommentForm = styled.form`
  display: flex;
  flex-direction: row;
  border-bottom: 0.3px solid rgba(255, 255, 255, 0.54);
  padding: 15px 33px;
  align-items: center;
  justify-content: space-between;
`;

const CommentInput = styled.input`
  border: none;
  background-color: rgba(255 255 255 / 8%);
  color: var(--white);
  font-size: 13px;
  font-family: var(--font-quicksand);
  padding-left: 8px;
  border: none;
  height: 30px;
  width: 100%;
  border-radius: 3px;

  &:disabled {
    background-color: rgba(255, 255, 255, 0.2);
  }
  
  &:focus {
    outline: none;
    background-color: rgba(255, 255, 255, 0.2);
  }

  &::placeholder {
    color: rgba(255, 255, 255, 0.5);
  }
`;

const CommentList = styled.form`
  display: flex;
  flex-direction: row;
  border-top: 0.5px solid var(--gray-100);
  border-bottom: 0.5px solid var(--gray-100);
  padding: 8px 30px;
  align-items: center;
  justify-content: space-between;
  min-width: 420px;
`;

const ListContent = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  padding: 0px 8px;
  align-items: center;
  gap: 8px;
`;

const UserName = styled.span`
  font-weight: 700;
  flex-shrink: 0;
`;

const Time = styled.span`
  flex-shrink: 0;
`;

const ButtonContainer = styled.div`
  row-gap: 8px;
  display: flex;
`;

const WriteBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
  margin-left: 8px;
`;

const IconBtn = styled.button`
  height: 30px;
  color: #8e8e8e;
  cursor: pointer;
  margin-right: 10px;
  background-color: transparent;
  border: none;
`;

const EditInput = styled.input`
  border: none;
  background-color: transparent;
  color: var(--white);
  font-size: 13px;
  font-family: var(--font-quicksand);
  border: none;
  height: 30px;
  width: 90%;

  &:focus {
    outline: none;
    background-color: rgba(255, 255, 255, 0.2);
  }
`;

type CommentSectionProps = {
  musicId: number;
};

const CommentSection = ({ musicId }: CommentSectionProps) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [commentInput, setCommentInput] = useState("");
  const [isInputActive, setIsInputActive] = useState(false);
  const [isCommentChanged, setIsCommentChanged] = useState(false);
  const [editCommentId, setEditCommentId] = useState<number | null>(null);
  const [editComment, setEditComment] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);
  const commentData: CommentData = useCommentData({
    musicId,
    currentPage,
    isCommentChanged,
  });

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const memberId = localStorage.getItem("memberId");

  const handleFormClick: () => void = () => {
    if (!memberId) {
      alert("로그인이 필요합니다.");
    } else {
      setIsInputActive(true);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCommentInput(event.target.value);
  };

  const handleEditSubmit = async (
    event: React.FormEvent,
    commentId: number
  ) => {
    event.preventDefault();

    const comment = editComment;

    if (comment.length < 2 || comment.length > 30) {
      alert("댓글은 2글자 이상 30글자 이하여야 합니다.");
      return;
    }

    const requestData = {
      commentId: commentId,
      comment: comment,
    };

    try {
      const response = await api.patch(
        `musics/${musicId}/comments/${commentId}`,
        requestData
      );

      const accessToken = response.headers["authorization"] || null;
      saveNewToken(accessToken);

      if (response.status === 200) {
        setEditCommentId(null);
        setIsCommentChanged(true);
      }
    } catch (error) {
      console.error(error);
      alert("댓글 수정에 실패했습니다.");
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const comment = commentInput;

    if (comment.length < 2 || comment.length > 30) {
      alert("댓글은 2글자 이상 30글자 이하여야 합니다.");
      return;
    }

    const requestData = {
      memberId: memberId,
      musicId: musicId,
      comment: comment,
    };

    try {
      const response = await api.post(
        `musics/${musicId}/comments`,
        requestData
      );

      setCommentInput("");
      setIsInputActive(false);

      const accessToken = response.headers["authorization"] || null;
      saveNewToken(accessToken);

      if (response.status === 201) {
        setIsCommentChanged(true);
      }
    } catch (error) {
      console.error(error);
      setCommentInput("");
      setIsInputActive(false);
      alert("댓글 작성에 실패했습니다.");
    }
  };

  const handleCancel = (event: React.MouseEvent) => {
    event.stopPropagation();
    setCommentInput("");
    setIsInputActive(false);
  };

  const handleEdit = (commentId: number, comment: string) => {
    setEditCommentId(commentId);
    setEditComment(comment);
  };

  const handleEditCancel = () => {
    setEditCommentId(null);
  };

  const handleEditChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEditComment(event.target.value);
  };

  const handleDelete = async (event: React.MouseEvent, commentId: number) => {
    event.stopPropagation();

    const confirmed = window.confirm("댓글을 삭제하시겠습니까?");

    if (confirmed) {
      try {
        const response = await api.delete(
          `musics/${musicId}/comments/${commentId}`
        );

        const accessToken = response.headers["authorization"] || null;
        saveNewToken(accessToken);

        if (response.status === 200) {
          setIsCommentChanged(true);
        }
      } catch (error) {
        console.error(error);
        alert("댓글 삭제에 실패했습니다.");
      }
    }
  };

  useEffect(() => {
    if ((editCommentId || isInputActive) && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isInputActive, editCommentId]);

  useEffect(() => {
    setIsCommentChanged(false);
  }, [isCommentChanged]);

  return (
    <CommentContainer>
      <CommentListContainer>
        <CommentForm onClick={handleFormClick} onSubmit={handleSubmit}>
          <CommentInput
            ref={inputRef}
            id="commentInput"
            type="text"
            placeholder="댓글을 입력하세요. 댓글은 2글자 이상 30글자 이하여야 합니다."
            value={commentInput}
            onChange={handleInputChange}
          />
          {isInputActive && (
            <ButtonContainer>
              <WriteBtn type="submit">작성완료</WriteBtn>
              <WriteBtn type="button" onClick={handleCancel}>
                작성취소
              </WriteBtn>
            </ButtonContainer>
          )}
        </CommentForm>
        {commentData?.data.length > 0 ? (
          commentData?.data.map((comment) => (
            <CommentList
              key={comment.commentId}
              onSubmit={(event: React.FormEvent) =>
                handleEditSubmit(event, comment.commentId)
              }
            >
              <Profile image={comment.profile} size={40} radius={4} />
              <ListContent>
                <UserName>{comment.name}</UserName>
                <span>:</span>
                {editCommentId === comment.commentId ? (
                  <EditInput
                    ref={inputRef}
                    type="text"
                    defaultValue={comment.comment}
                    onChange={handleEditChange}
                  />
                ) : (
                  <span>{comment.comment}</span>
                )}
              </ListContent>
              {editCommentId === comment.commentId ? (
                <ButtonContainer>
                  <WriteBtn type="submit">수정완료</WriteBtn>
                  <WriteBtn type="button" onClick={() => handleEditCancel()}>
                    수정취소
                  </WriteBtn>
                </ButtonContainer>
              ) : (
                  <>
                   { Number(memberId) === comment.memberId && (
                    <ButtonContainer>
                      <IconBtn
                        onClick={() =>
                          handleEdit(comment.commentId, comment.comment)
                        }
                      >
                        <EditIcon type="button" fill="#F5F6F6" />
                      </IconBtn>
                      <IconBtn type="button">
                        <DeleteIcon
                          fill="#F5F6F6"
                          onClick={(event: React.MouseEvent) =>
                            handleDelete(event, comment.commentId)
                          }
                        />
                      </IconBtn>
                    </ButtonContainer>
                   )}
                    <Time>{calculateTimeAgo(comment.createdAt)}</Time>
                  </>
                )
              }
            </CommentList>
          ))
        ) : (
          <Empty
            color="var(--white)"
            message="안녕하새오 미아내오 댓글이 없어오"
          />
        )}
      </CommentListContainer>
      <Pagination
        currentPage={currentPage}
        totalPages={commentData?.pageInfo.totalPages || 0}
        onPageChange={handlePageChange}
      />
    </CommentContainer>
  );
};

export default CommentSection;
