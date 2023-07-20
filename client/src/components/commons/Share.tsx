/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect } from "react";
import styled from "styled-components";
import Google from "../../assets/imgs/google.svg";
import Kakao from "../../assets/imgs/kakao.svg";
import Naver from "../../assets/imgs/naver.svg";
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

function Share() {
  const GoogleLogin = () => {
    window.location.href =
      "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google";
  };
  const NaverLogin = () => {
    window.location.href =
      "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/naver";
  };
  const KakaoLogin = () => {
    window.location.href =
      "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/kakao";
  };

  return (
    <ShareContainer>
      <img onClick={() => GoogleLogin()} src={Google} alt="Google" />
      <img onClick={() => NaverLogin()} src={Naver} alt="Naver" />
      <img onClick={() => KakaoLogin()} src={Kakao} alt="Kakao" />
    </ShareContainer>
  );
}

export default Share;
