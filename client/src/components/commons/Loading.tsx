import styled from "styled-components";
import { ReactComponent as Spinner } from "../../assets/imgs/Spinner.svg";

const LoadingWrapper = styled.div`
  width: 250px;
  height: 250px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  font-family: var(--font-gaegu);
  font-size: 20px;
  margin: 48px auto;
`;
function Loading() {
  return (
    <LoadingWrapper>
      <p>잠시만 기다려주세요.</p>
      <Spinner />
    </LoadingWrapper>
  );
}

export default Loading;
