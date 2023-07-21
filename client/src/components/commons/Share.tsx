import styled from "styled-components";
import Google from "../../assets/imgs/google.svg";
import Kakao from "../../assets/imgs/kakao.svg";
import Naver from "../../assets/imgs/naver.svg";
import { GoogleLogin, NaverLogin, KakaoLogin } from "../../utils/Url";

const ShareContainer = styled.div`
  width: 200px;
  display: flex;
  justify-content: space-around;
  margin-top: 30px;
  img {
    cursor: pointer;
  }
`;

function Share() {
  const handleGoogleLogin = () => {
    window.location.href = GoogleLogin;
  };
  const handleNaverLogin = () => {
    window.location.href = NaverLogin;
  };
  const handleKakaoLogin = () => {
    window.location.href = KakaoLogin;
  };

  return (
    <ShareContainer>
      <img onClick={() => handleGoogleLogin()} src={Google} alt="Google" />
      <img onClick={() => handleNaverLogin()} src={Naver} alt="Naver" />
      <img onClick={() => handleKakaoLogin()} src={Kakao} alt="Kakao" />
    </ShareContainer>
  );
}

export default Share;
