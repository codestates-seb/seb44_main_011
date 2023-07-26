import IntroLogo from "../assets/imgs/IntroLogo.svg";
import styled from "styled-components";
import Bluebutton from "../components/commons/Bluebutton";
import { Link } from "react-router-dom";
import IntroImg from "../assets/imgs/intro.gif";
const Container = styled.div`
  width: 100%;
  height: 100vh;
  background: var(--gradation-intro);
  background-image: url(${IntroImg});
  background-size: cover;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  overflow: hidden;
  a {
    text-decoration: none;
  }
`;

const ImgCover = styled.div`
  width: 100%;
  height: 100%;
  background: linear-gradient(
    223deg,
    rgba(170, 221, 255, 0.5) 0%,
    rgba(255, 215, 215, 0.5) 100%
  );
  backdrop-filter: blur(4px);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  a {
    text-decoration: none;
  }
`;
const Logo = styled.img``;

function Intro() {
  return (
    <Container>
      <ImgCover>
        <Logo src={IntroLogo} />
        <Link to="/home">
          <Bluebutton value="시작하기" />
        </Link>
      </ImgCover>
    </Container>
  );
}

export default Intro;
