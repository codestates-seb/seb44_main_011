import styled from "styled-components";
import Google from "../../assets/imgs/google.svg";
import Naver from "../../assets/imgs/naver.svg";
import Kakao from "../../assets/imgs/kakao.svg";

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
  return (
    <ShareContainer>
      <img src={Google} alt="Google" />
      <img src={Naver} alt="Naver" />
      <img src={Kakao} alt="Kakao" />
    </ShareContainer>
  );
}

export default Share;
