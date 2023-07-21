import EditProfile from "../components/EditProfile";
import { styled } from "styled-components";

function Edit() {
  return (
    <Wrapper>
      <EditProfile />
    </Wrapper>
  );
}

export default Edit;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 4vh;
  width: 100%;
  max-width: 1800px;
  padding: 1px 6px;
`;
