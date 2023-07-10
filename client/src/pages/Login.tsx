import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InputContainer, InBox } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import Share from "../components/commons/Share";
import { EmailRegEx, PasswordRegEx } from "../utils/Check";
import { useForm } from "react-hook-form";
import axios, { AxiosError } from "axios";
import { PostLogin } from "../utils/Url";

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
  } = useForm<FormValues>();

  const onSubmit = async (data: FormValues) => {
    await axios.post(PostLogin, data);
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Modal>
        <Text value="Login" />
        <InputContainer>
          <InBox
            id="email"
            type="text"
            {...register("email", {
              required: true,
              pattern: {
                value: EmailRegEx,
                message: "유효한 이메일 주소를 입력하세요.",
              },
            })}
            placeholder="email"
          />
          {errors.email && <span>{errors.email.message}</span>}
          <InBox
            id="password"
            type="password"
            {...register("password", {
              required: true,
              pattern: {
                value: PasswordRegEx,
                message:
                  "비밀번호는 최소 8자 이상 최대 16자 이하이어야 합니다.",
              },
            })}
            placeholder="password"
          />
          {errors.password && <span>{errors.password.message}</span>}
        </InputContainer>
        <Bluebutton value="Login" />
        <Share />
      </Modal>
    </form>
  );
}

export default Login;
