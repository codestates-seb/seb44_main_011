import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InputContainer, InBox, ErrorMsg } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import Share from "../components/commons/Share";
import {
  NameRegEx,
  EmailRegEx,
  PasswordRegEx,
  PasswordMin,
  PasswordMax,
} from "../utils/Check";
import { useForm } from "react-hook-form";
import { useRef } from "react";
import axios from "axios";
import { PostSignUp } from "../utils/Url";
import { Form } from "../components/commons/Form";

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
    watch,
    formState: { errors },
  } = useForm<FormValues>({ mode: "onBlur" });

  const passwordRef = useRef<string | null>(null);
  passwordRef.current = watch("password");

  const onSubmit = async (data: FormValues) => {
    const dataA = {
      email: data.email,
      password: data.password,
      name: data.name,
    };
    console.log("signup 데이터: ", dataA);
    await axios
      .post(PostSignUp, dataA)
      .then((response) => {
        if (response.status === 201) {
          alert("회원가입이 완료되었습니다.");
          window.location.replace("/");
        } else if (response.status === 409) {
          alert("중복된 이메일입니다. 다시 진행해주세요.");
        } else if (response.status === 500) {
          alert("입력 양식에 맞게 다시 입력해주세요.");
        }
      })
      .catch((error) => console.log(error));
  };
  return (
    <Modal>
      <Text value="SignUp" />
      <Form onSubmit={handleSubmit(onSubmit)}>
        <InputContainer>
          <InBox
            id="name"
            type="text"
            placeholder="name"
            {...register("name", {
              required: "닉네임은 필수 입력입니다.",
              pattern: {
                value: NameRegEx,
                message: "닉네임을 입력하세요.(2~7자)",
              },
            })}
          />
          {errors.name && <ErrorMsg>{errors.name.message}</ErrorMsg>}
          <InBox
            id="email"
            type="text"
            placeholder="email"
            {...register("email", {
              required: "이메일은 필수 입력입니다.",
              pattern: {
                value: EmailRegEx,
                message: "유효한 이메일 주소를 입력하세요.",
              },
            })}
          />
          {errors.email && <ErrorMsg>{errors.email.message}</ErrorMsg>}
          <InBox
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
          <InBox
            id="passwordConfirm"
            type="password"
            placeholder="passwordConfirm"
            {...register("passwordConfirm", {
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
              validate: {
                check: (val) => {
                  if (passwordRef.current !== val) {
                    return "비밀번호가 일치하지 않습니다.";
                  }
                },
              },
            })}
          />
          {errors.passwordConfirm && (
            <ErrorMsg>{errors.passwordConfirm.message}</ErrorMsg>
          )}
        </InputContainer>
        <Bluebutton value="SignUp" />
      </Form>
      <Share />
    </Modal>
  );
}

export default SignUp;
