import styled from "styled-components";
import Text from "./H2Text";
import Input from "./Input";
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
  setEmail: (value: string) => void;
  setPassword: (value: string) => void;
};
function Modal({ email, password, setEmail, setPassword }: Props) {
  return (
    <ModalContainer>
      <Text value="Login" />
      <Input name="email" type="text" value={email} onChange={setEmail} />
      <Input
        name="password"
        type="password"
        value={password}
        onChange={setPassword}
      />
      <Bluebutton value="Login" />
      <Share />
    </ModalContainer>
  );
}

export default Modal;
