import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InBox, ErrorMsg } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import GrayButton from "../components/commons/GrayButton";
import styled from "styled-components";
import { PasswordRegEx } from "../utils/Check";
import { useForm } from "react-hook-form";

type FormValues = {
  password: string;
};
const ButtonBox = styled.div`
  display: flex;
  justify-content: space-around;
  width: 400px;
`;

function Delete() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ mode: "onBlur" });

  const onSubmit = (data: FormValues) => {
    console.log("회원 삭제 데이터:", data);
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Modal>
        <Text value="회원 탈퇴" />
        <InBox
          id="password"
          type="password"
          placeholder="password"
          {...register("password", {
            required: "비밀번호는 필수 입력입니다.",
            pattern: {
              value: PasswordRegEx,
              message: "비밀번호는 최소 8자 이상 최대 16자 이하이어야 합니다.",
            },
          })}
        />
        {errors.password && <ErrorMsg>{errors.password.message}</ErrorMsg>}
        <div>
          <p>본인 인증을 위해 비밀번호를 입력해주세요</p>
        </div>
        <ButtonBox>
          <GrayButton value="취소" />
          <Bluebutton value="탈퇴" />
        </ButtonBox>
      </Modal>
    </form>
  );
}

export default Delete;
