import { keyframes, styled } from "styled-components";
import { DeleteUser } from "../utils/Url";
import axios from "axios";
import { useForm } from "react-hook-form";
import { PasswordRegEx, PasswordMin, PasswordMax } from "../utils/Check";
import { ErrorMsg } from "./commons/Input";
import { Form } from "./commons/Form";

type PropsType = {
  setModalOpen: (open: boolean) => void;
};
type FormValues = {
  password: string;
};

function DeleteModal({ setModalOpen }: PropsType) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ mode: "onBlur" });

  const closeModal = () => {
    setModalOpen(false);
  };

  const handleConfirm = async (data: FormValues) => {
    const memberId = localStorage.getItem("memberId");
    const accessToken = localStorage.getItem("accessToken");
    const refresh = localStorage.getItem("refresh");
    console.log(accessToken);
    await axios
      .delete(`${DeleteUser}/${memberId}`, {
        data: data,
        headers: {
          Authorization: accessToken,
          refresh: refresh,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          localStorage.removeItem("memberId");
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refresh");
          alert("정상적으로 탈퇴되었습니다.");
          window.location.replace("/"); // 이동할 경로를 지정하세요
        } else if (response.status === 401) {
          alert("비밀번호를 확인해주세요.");
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <FadeIn>
      <Container>
        <ContentWrapper>
          <Title>회원 탈퇴</Title>
          <Content>본인 인증을 위해 비밀번호를 입력해주세요</Content>
          <Form onSubmit={handleSubmit(handleConfirm)}>
            <InputField
              id="password"
              type="password"
              placeholder="password"
              {...register("password", {
                required: "비밀번호는 필수 입력입니다.",
                minLength: {
                  value: PasswordMin,
                  message: "비밀번호는 최소 8자 이상이어야 합니다.",
                },
                maxLength: {
                  value: PasswordMax,
                  message: "비밀번호는 최대 16자 이하이어야 합니다.",
                },
                pattern: {
                  value: PasswordRegEx,
                  message:
                    "비밀번호는 최소 영문자 1개와 숫자 1개가 포함되어야 합니다.",
                },
              })}
            />
            {errors.password && <ErrorMsg>{errors.password.message}</ErrorMsg>}
            <ButtonWrapper>
              <Cancle onClick={closeModal}>취소</Cancle>
              <Confirm>탈퇴</Confirm>
            </ButtonWrapper>
          </Form>
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
  margin-top: 64px;
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
  margin-bottom: 10px;
  font-size: 24px;
  &::placeholder {
    color: var(--gray-300, #d1d1d1);
    font-family: Gaegu;
    font-style: normal;
    font-weight: 400;
    line-height: 100%; /* 24px */
  }
  &:focus {
    outline: none;
    border-bottom: 1.5px solid #4857fd;
    &::placeholder {
      color: var(--skyblue-100);
    }
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
