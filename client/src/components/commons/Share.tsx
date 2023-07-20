import styled from "styled-components";
import Google from "../../assets/imgs/google.svg";
import Naver from "../../assets/imgs/naver.svg";
import Kakao from "../../assets/imgs/kakao.svg";
import { useGoogleLogin } from "@react-oauth/google";

const ShareContainer = styled.div`
  width: 200px;
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
  img {
    cursor: pointer;
  }
`;
function Share() {
  const login = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    onError: (errorResponse) => {
      console.error(errorResponse);
    },
    flow: "auth-code",
  });
  return (
    <ShareContainer>
      <img onClick={() => login()} src={Google} alt="Google" />
      <img src={Naver} alt="Naver" />
      <img src={Kakao} alt="Kakao" />
    </ShareContainer>
  );
}

export default Share;
