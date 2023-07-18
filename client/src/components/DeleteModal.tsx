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
        <ContentWrapper>
          <Title>회원 탈퇴</Title>
          <Content>본인 인증을 위해 비밀번호를 입력해주세요</Content>
          <InputField type="password" placeholder="비밀번호 확인" />
          <ButtonWrapper>
            <Cancle onClick={closeModal}>취소</Cancle>
            <Confirm onClick={handleConfirm}>탈퇴</Confirm>
          </ButtonWrapper>
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
  width: 550px;
  height: 500px;
  z-index: 999;
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border: 1px solid black;
  border-radius: 15px;
  display: flex;
  justify-content: center;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Title = styled.p`
  color: var(--black, #212121);
  text-align: center;
  font-family: Gaegu;
  font-size: 48px;
  font-style: normal;
  font-weight: 400;
  line-height: 100%;
  margin-bottom: 12px;
`;
const Content = styled.p`
  color: var(--black, #212121);
  font-family: Gaegu;
  font-size: 20px;
  font-style: normal;
  font-weight: 400;
  line-height: 150%;
  margin-bottom: 60px;
`;

const InputField = styled.input`
  border: none;
  border-bottom: 1px solid #8e8e8e;
  position: relative;
  left: 0px;
  top: 0px;
  height: 30px;
  width: 400px;
  outline: none;
  margin-bottom: 64px;
  &::placeholder {
    color: var(--gray-300, #d1d1d1);
    font-family: Gaegu;
    font-size: 24px;
    font-style: normal;
    font-weight: 400;
    line-height: 100%; /* 24px */
  }
`;
const Confirm = styled.button`
  margin-left: 10px;
  width: 185px;
  height: 40px;
  border: 1px solid #84cbff;
  border-radius: 100px;
  color: var(--white);
  font-family: Gaegu;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: 100%; /* 20px */
  background-color: #84cbff;
  cursor: pointer;
`;
const Cancle = styled.button`
  width: 185px;
  height: 40px;
  border: 1px solid #8e8e8e;
  border-radius: 100px;
  color: var(--white);
  font-family: Gaegu;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: 100%; /* 20px */
  background-color: #8e8e8e;
  cursor: pointer;
`;
