import IntroLogo from "../assets/imgs/IntroLogo.svg";
import Cat from "../assets/imgs/cat.svg";
import Dog from "../assets/imgs/dog.svg";
import styled, { keyframes } from "styled-components";
import Bluebutton from "../components/commons/Bluebutton";
import { Link } from "react-router-dom";
const Container = styled.div`
  width: 100%;
  height: 100vh;
  background: var(--gradation-intro);
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  overflow: hidden;
  a {
    text-decoration: none;
  }
`;
const Logo = styled.img`
  margin-top: 120px;
`;

const SlideUp = keyframes`
  from{
    transform: translateY(100%);
  }
  to{
    transform: translateX(0);
  }
`;
const ImgContainer = styled.div`
  margin-top: 150px;
  & > img {
    margin-top: 10px;
  }

  & > img:nth-child(1) {
    animation: ${SlideUp} 4s ease;
  }

  & > img:nth-child(2) {
    animation: ${SlideUp} 6s ease;
  }
`;
function Intro() {
  return (
    <Container>
      <Logo src={IntroLogo} />
      <Link to="/home">
        <Bluebutton value="시작하기" />
      </Link>
      <ImgContainer>
        <img src={Dog} alt="Dog" />
        <img src={Cat} alt="Cat" />
      </ImgContainer>
    </Container>
  );
}

export default Intro;
