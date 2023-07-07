import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InputContainer, InBox } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import Share from "../components/commons/Share";
import { NameRegEx, EmailRegEx, PasswordRegEx } from "../utils/Check";
import { useForm } from "react-hook-form";

type FormValues = {
  name: string;
  email: string;
  password: string;
  passwordConfirm: string;
};
function SignUp() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>();

  const onSubmit = (data: FormValues) => {
    console.log(data);
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Modal>
        <Text value="SignUp" />
        <InputContainer>
          <InBox
            id="name"
            type="text"
            placeholder="name"
            {...register("name", {
              required: true,
              pattern: {
                value: NameRegEx,
                message: "유효한 닉네임을 입력하세요.",
              },
            })}
          />
          {errors.name && <span>{errors.name.message}</span>}
          <InBox
            id="email"
            type="text"
            placeholder="email"
            {...register("email", {
              required: true,
              pattern: {
                value: EmailRegEx,
                message: "유효한 이메일 주소를 입력하세요.",
              },
            })}
          />
          {errors.email && <span>{errors.email.message}</span>}
          <InBox
            id="password"
            type="password"
            placeholder="password"
            {...register("password", {
              required: true,
              pattern: {
                value: PasswordRegEx,
                message:
                  "비밀번호는 최소 8자 이상 최대 16자 이하이어야 합니다.",
              },
            })}
          />
          {errors.password && <span>{errors.password.message}</span>}
          <InBox
            id="passwordConfirm"
            type="password"
            placeholder="passwordConfirm"
            {...register("passwordConfirm", {
              required: true,
              pattern: {
                value: PasswordRegEx,
                message:
                  "비밀번호는 최소 8자 이상 최대 16자 이하이어야 합니다.",
              },
            })}
          />
          {errors.passwordConfirm && (
            <span>{errors.passwordConfirm.message}</span>
          )}
        </InputContainer>
        <Bluebutton value="SignUp" />
        <Share />
      </Modal>
    </form>
  );
}

export default SignUp;
