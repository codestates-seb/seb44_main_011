import styled from "styled-components";

const InText = styled.h2`
  font-weight: 400;
  font-size: 48px;
  margin-bottom: 50px;
  color: var(--black);
`;
type Props = {
  value: string;
};
function H2Text({ value }: Props) {
  return <InText>{value}</InText>;
}

export default H2Text;
