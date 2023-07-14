import React from "react";
import { keyframes, styled } from "styled-components";
import { useNavigate } from "react-router-dom";

type PropsType = {
  setModalOpen: (open: boolean) => void;
};

function DeleteModal({ setModalOpen }: PropsType) {
  const closeModal = () => {
    setModalOpen(false);
  };

  const navigate = useNavigate();

  const handleConfirm = () => {
    alert("정상적으로 탈퇴되었습니다.");
    navigate("/"); // 이동할 경로를 지정하세요
  };

  return (
    <FadeIn>
      <Container>
        <Exit onClick={closeModal}>X</Exit>
        <ContentWrapper>
          <Content>비밀번호 입력 : </Content>
          <InputField type="password" />
          <Confirm onClick={handleConfirm}>확인</Confirm>
        </ContentWrapper>
      </Container>
    </FadeIn>
  );
}

export default DeleteModal;

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const FadeIn = styled.div`
  animation: ${fadeInAnimation} 0.5s ease-in;
`;

const Container = styled.div`
  width: 500px;
  height: 200px;
  z-index: 999;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #f0f3f3;
  border: 1px solid black;
  border-radius: 8px;
  display: flex;
  justify-content: center;
`;

const Exit = styled.button`
  position: absolute;
  right: 10px;
  top: 10px;
`;
const ContentWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Content = styled.p`
  color: black;
  margin-right: 10px;
`;

const InputField = styled.input`
  border: none;
  position: relative;
  left: 0px;
  top: 0px;
  height: 30px;
  width: 139px;
  outline: none;
  border-radius: 7px;
  &:focus {
    border: 2px solid #84cbff;
  }
`;
const Confirm = styled.button`
  margin-left: 10px;
  width: 50px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
`;
