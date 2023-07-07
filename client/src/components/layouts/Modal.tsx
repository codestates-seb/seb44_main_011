import styled from "styled-components";

type Props = {
  children: React.ReactNode;
};

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
function Modal({ children }: Props) {
  return <ModalContainer>{children}</ModalContainer>;
}

export default Modal;
