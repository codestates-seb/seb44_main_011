import { useEffect } from "react";
import styled from "styled-components";
import Google from "../../assets/imgs/google.svg";
import Kakao from "../../assets/imgs/kakao.svg";
//import { useGoogleLogin } from "@react-oauth/google";
import { Naver_Redirect_URL } from "../../utils/Url";
const ShareContainer = styled.div`
  width: 200px;
  display: flex;
  justify-content: space-around;
  margin-top: 30px;
  img {
    cursor: pointer;
  }
`;
const NaverDiv = styled.div`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #ffffff;
  overflow: hidden;
`;
declare global {
  interface Window {
    naver: any;
  }
}
function Share() {
  // const login = useGoogleLogin({
  //   onSuccess: (codeResponse) => console.log(codeResponse),
  //   onError: (errorResponse) => {
  //     console.error(errorResponse);
  //   },
  //   flow: "auth-code",
  // });
  const { naver } = window;
  const initializeNaverLogin = () => {
    const naverLogin = new naver.LoginWithNaverId({
      clientId: import.meta.env.VITE_Naver_Client_Id,
      callbackUrl: Naver_Redirect_URL,
      isPopup: false,
      loginButton: { color: "green", type: 1, height: "50" },
    });
    naverLogin.init();
  };
  useEffect(() => {
    initializeNaverLogin();
  }, []);
  return (
    <ShareContainer>
      <img src={Google} alt="Google" />
      <NaverDiv>
        <div id="naverIdLogin" />
      </NaverDiv>
      <img src={Kakao} alt="Kakao" />
    </ShareContainer>
  );
}

export default Share;
