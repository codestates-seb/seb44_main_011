import { styled } from "styled-components";
import Profile from "./commons/Profile";
import testImg from "../assets/imgs/testimg.jpg";
import { calculateTimeAgo } from "../utils/calculateTimeAgo";
import { ReactComponent as EditIcon } from "../assets/icons/editicon.svg";
import { ReactComponent as DeleteIcon } from "../assets/icons/deleteicon.svg";
import useCommentData from "../hooks/useCommentData";
import Pagination from "./Pagination";
import { useState, useEffect, useRef } from "react";
import { CommentData } from "../types/Comment";
import Empty from "./Empty";

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
  margin: 30px;
`;

const CommentForm = styled.form`
  display: flex;
  flex-direction: row;
  border-bottom: 0.3px solid rgba(255, 255, 255, 0.54);
  padding: 15px 30px;
  align-items: center;
  margin: 0px 30px;
`;

const CommentInput = styled.input`
  flex: 1;
  border: none;
  background-color: transparent;
  color: var(--white);
  font-size: 13px;
  font-family: var(--font-quicksand);
  margin-left: 8px;
  border: none;
  height: 30px;
  padding: 0 8px;

  &:focus {
    outline: none;
    background-color: var(--gray-300);
  }

  &::placeholder {
    color: var(--gray-100);
  }
`;

const CommentList = styled.div`
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
  width: 60%;
`;

const UserName = styled.span`
  font-weight: 700;
  margin-right: 8px;
`;

const ButtonContainer = styled.div`
  row-gap: 8px;
`;

const WriteBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
`;

type CommentSectionProps = {
  musicId: number;
};

const CommentSection = ({ musicId }: CommentSectionProps) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [commentInput, setCommentInput] = useState("");
  const [isInputActive, setIsInputActive] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const commentData: CommentData = useCommentData({ musicId, currentPage });

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handleFormClick = () => {
    const memberId = localStorage.getItem("memberId");
    if (!memberId) {
      alert("로그인이 필요합니다.");
    } else {
      setIsInputActive(true);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCommentInput(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    if (commentInput.length < 2 || commentInput.length > 30) {
      alert("댓글은 2글자 이상 30글자 이하여야 합니다.");
      return;
    }

    setCommentInput("");
    setIsInputActive(false);
  };

  const handleCancel = (event: React.MouseEvent) => {
    event.stopPropagation();
    setCommentInput("");
    setIsInputActive(false);
  };

  useEffect(() => {
    if (isInputActive && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isInputActive]);

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
            disabled={!isInputActive}
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
            <CommentList key={comment.commentId}>
              <Profile image={testImg} size={40} radius={4} />
              <ListContent>
                <UserName>{comment.name}</UserName>
                <span>: {comment.comment}</span>
              </ListContent>
              <span>{calculateTimeAgo(comment.createdAt)}</span>
              <ButtonContainer>
                <button>
                  <EditIcon fill="#F5F6F6" />
                </button>
                <button>
                  <DeleteIcon fill="#F5F6F6" />
                </button>
              </ButtonContainer>
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
