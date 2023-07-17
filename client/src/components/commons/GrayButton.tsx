import styled from "styled-components";

type Props = {
  value: string;
};

const Button = styled.button`
  width: 185px;
  height: 39px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--gray-300);
  border: none;
  border-radius: 100px;
  color: #ffffff;
  font-family: var(--font-gaegu);
  font-size: 20px;
  font-weight: 700;
  margin-top: 30px;
  cursor: pointer;
  &:hover {
    background-color: var(--gray-400);
  }
  &:active {
    background-color: var(--gray-500);
  }
`;
function GrayButton({ value }: Props) {
  return <Button>{value}</Button>;
}

export default GrayButton;
