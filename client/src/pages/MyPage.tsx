import { styled } from "styled-components";
import { MypageInfo } from "../components/MypageInfo";

function MyPage() {
  return (
    <Wrapper>
      <MypageInfo selectedImage={"selectedImage"} />
    </Wrapper>
  );
}

export default MyPage;

const Wrapper = styled.div`
  display: flex;
`;
