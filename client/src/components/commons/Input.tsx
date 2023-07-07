import styled from "styled-components";

type Props = {
  value: string | number;
  onChange: (e: any) => void;

  name?: string;
  type?: "text" | "number" | "password";
};

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 5px 0;
`;

const InBox = styled.input`
  border: none;
  border-bottom: 1px solid var(--black);
  width: 400px;
  font-size: 24px;
  font-family: var(--font-gaegu);
  outline: none;
  margin-bottom: 5px;
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
function Input({ type, name, value, onChange }: Props) {
  console.log(value);
  return (
    <InputContainer>
      <InBox
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={name}
      />
    </InputContainer>
  );
}

export default Input;
