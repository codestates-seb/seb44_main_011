import { styled } from "styled-components";
import { MypageInfo } from "../components/MypageInfo";
import { useLocation } from "react-router-dom";

function MyPage() {
  const loc = useLocation();
  return (
    <Wrapper>
      <MypageInfo selectedImage={loc.state.selectedImage} />
    </Wrapper>
  );
}

export default MyPage;

const Wrapper = styled.div`
  display: flex;
`;
