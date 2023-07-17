import { styled } from "styled-components";
import { MypageInfo } from "../components/MypageInfo";

function Mypage() {
  return (
    <Wrapper>
      <MypageInfo selectedImage={"selectedImage"} />
    </Wrapper>
  );
}

export default Mypage;

const Wrapper = styled.div`
  display: flex;
`;
