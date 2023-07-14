import SideBar from "../components/SideBar";
import EditProfile from "../components/EditProfile";
import { styled } from "styled-components";

function Edit() {
  return (
    <Wrapper>
      <SideBar />
      <EditProfile />
    </Wrapper>
  );
}

export default Edit;

const Wrapper = styled.div`
  display: flex;
`;
