import Modal from "../components/layouts/Modal";
import Text from "../components/commons/H2Text";
import { InputContainer, InBox, ErrorMsg } from "../components/commons/Input";
import Bluebutton from "../components/commons/Bluebutton";
import Share from "../components/commons/Share";
import {
  EmailRegEx,
  PasswordRegEx,
  PasswordMin,
  PasswordMax,
} from "../utils/Check";
import { useForm } from "react-hook-form";
import axios from "axios";
import { PostLogin } from "../utils/Url";
import { Form } from "../components/commons/Form";
import { useDispatch } from "react-redux";
import { loginUser } from "../redux/UserInfo";

type FormValues = {
  email: string;
  password: string;
};
type Response = {
  memberId: string;
  Authorization: string;
  Refresh: string;
  email: string;
  role: string;
};
function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ mode: "onBlur" });

  const dispatch = useDispatch();

  const onSubmit = async (data: FormValues) => {
    await axios
      .post<Response>(PostLogin, data)
      .then((response) => {
        const accessToken = response.headers["authorization"] || null;
        const refresh = response.headers["refresh"] || null;
        const memberId = response.data["memberId"];
        const role = response.data["role"];
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refresh", refresh);
        localStorage.setItem("memberId", memberId);
        localStorage.setItem("role", role);
        dispatch(loginUser(response.data));
        window.location.replace("/home");
      })
      .catch((error) => {
        if (error.response.status === 401) {
          if (
            error.response.data.message === "Invalid credentials : Unauthorized"
          ) {
            alert("잘못된 비밀번호입니다. 다시 입력해주세요.");
          } else if (
            error.response.data.message === "Member not found : Unauthorized"
          ) {
            alert("등록되지 않은 사용자입니다. 이메일을 다시 입력해주세요");
          } else if (
            error.response.data.message ===
            "This email already used in OAuth2 : Unauthorized"
          ) {
            alert(
              "이미 OAuth로 가입된 사용자입니다. OAuth를 이용해 로그인 해주세요."
            );
          } else if (
            error.response.data.message ===
            "Already with drawn Member : Unauthorized"
          ) {
            alert("이미 탈퇴한 사용자입니다. 회원가입을 진행해주세요.");
          }
        } else if (error.response.status === 500) {
          alert("서버 에러가 발생했습니다. 잠시 후 시도해주세요.");
        } else {
          alert("다시 로그인을 진행해주세요.");
        }
      });
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
                  "비밀번호는 최소 영문자 1개와 숫자 1개가 포함되어야 합니다.(특수 문자 제외)",
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
