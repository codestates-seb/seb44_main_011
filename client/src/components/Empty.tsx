import { FC } from "react";
import { styled } from "styled-components";
import empty from "../assets/imgs/empty.svg";
import H2Text from "./commons/H2Text";

type EmptyProps = {
  message: string;
};

const EmptyContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: var(--font-quicksand);
  margin: 32px;
  img {
    margin-bottom: 32px;
  }
`;

const Empty: FC<EmptyProps> = ({ message }) => {
  return (
    <EmptyContainer>
      <img src={empty} alt="dog and cat" />
      <H2Text value={message} />
    </EmptyContainer>
  );
};

export default Empty;
