import styled from "styled-components";

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const InBox = styled.input`
  border: none;
  border-bottom: 1px solid var(--black);
  width: 400px;
  font-size: 24px;
  font-family: var(--font-gaegu);
  outline: none;
  margin-bottom: 10px;
  &::placeholder {
    color: var(--gray-400);
  }
  &:focus {
    outline: none;
    border-bottom: 1.5px solid #4857fd;
    &::placeholder {
      color: var(--skyblue-100);
    }
  }
`;

export const ErrorMsg = styled.span`
  color: rgba(214, 79, 79, 0.87);
  font-family: var(--font-gaegu);
`;
