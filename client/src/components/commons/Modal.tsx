import styled from "styled-components";
import Text from "./H2Text";
import { InBox } from "./Input";
import Bluebutton from "./Bluebutton";
import Share from "./Share";

const ModalContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 550px;
  height: 500px;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 1px 1px 15px rgba(0, 0, 0, 0.2);
  font-family: var(--font-gaegu);
`;

type Props = {
  email: string;
  password: string;
};
function Modal({ email, password }: Props) {
  return (
    <ModalContainer>
      <Text value="Login" />
      <InBox name="email" type="text" value={email} />
      <InBox name="password" type="password" value={password} />
      <Bluebutton value="Login" />
      <Share />
    </ModalContainer>
  );
}

export default Modal;
