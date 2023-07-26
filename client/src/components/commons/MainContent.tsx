import { styled } from "styled-components";

const MainContent = styled.div`
  height: 100vh;
  width: 100%;
  margin-left: calc(245px + 5%);
  margin-right: 5%;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  flex-direction: column;
  @media screen and (max-width: 800px) {
    height: 80vh;
    margin: 0 auto;
  }
`;

export default MainContent;
