import { FC } from "react";
import { styled } from "styled-components";
import empty from "../assets/imgs/empty.png";

type EmptyProps = {
  message: string;
  size?: number;
  color?: string;
};

const EmptyContainer = styled.div<{ size: number }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  align-self: center;
  justify-content: center;
  font-family: var(--font-quicksand);
  margin: 48px;
  height: ${(props) => props.size}px;
  img {
    margin-bottom: 32px;
  }
`;

const EmptyMessage = styled.h1<{ color: string }>`
  font-family: var(--font-gaegu);
  color: ${(props) => props.color};
  font-size: 24px;
  text-align: center;
`;

const Empty: FC<EmptyProps> = ({
  message,
  size = 182,
  color = "var(--black)",
}) => {
  return (
    <EmptyContainer size={size}>
      <img src={empty} alt="dog and cat" />
      <EmptyMessage color={color}>{message}</EmptyMessage>
    </EmptyContainer>
  );
};

export default Empty;
