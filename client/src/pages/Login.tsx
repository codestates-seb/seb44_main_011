import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InputContainer, InBox, ErrorMsg } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import Share from "../components/commons/Share";
import { EmailRegEx, PasswordRegEx } from "../utils/Check";
import { useForm } from "react-hook-form";
import axios, { AxiosError } from "axios";
import { PostLogin } from "../utils/Url";
import { Form } from "../components/commons/Form";

type FormValues = {
  email: string;
  password: string;
};
type Response = {
  member: number;
  Authorization: string;
  Refresh: string;
};

function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ mode: "onBlur" });

  const onSubmit = async (data: FormValues) => {
    console.log("회원 가입 데이터:", data);
    await axios
      .post<Response>(PostLogin, data)
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  };
  return (
    <Modal>
      <Text value="Login" />
      <Form onSubmit={handleSubmit(onSubmit)}>
        <InputContainer>
          <InBox
            id="email"
            type="text"
            {...register("email", {
              required: "이메일은 필수 입력입니다.",
              pattern: {
                value: EmailRegEx,
                message: "유효한 이메일 주소를 입력하세요.",
              },
            })}
            placeholder="email"
          />
          {errors.email && <ErrorMsg>{errors.email.message}</ErrorMsg>}
          <InBox
            id="password"
            type="password"
            {...register("password", {
              required: "비밀번호는 필수 입력입니다.",
              pattern: {
                value: PasswordRegEx,
                message:
                  "비밀번호는 최소 8자 이상 최대 16자 이하이어야 합니다.",
              },
            })}
            placeholder="password"
          />
          {errors.password && <ErrorMsg>{errors.password.message}</ErrorMsg>}
        </InputContainer>
        <Bluebutton value="Login" />
      </Form>
      <Share />
    </Modal>
  );
}

export default Login;
