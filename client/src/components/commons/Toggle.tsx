import { styled } from "styled-components";
import CategoryBtns from "./CategoryBtns";
const ToggleContainer = styled.div`
  position: relative;
  box-sizing: inherit;
`;

const ToggleBase = styled.div`
  display: flex;
  width: 180px;
  height: 40px;
  border-radius: 100px;
  background-color: var(--white);
  border: 2px solid var(--skyblue-100);
  background: var(--white);
  align-items: center;
  justify-content: space-between;
  padding: 0 4px;
`;

const ToggleCircle = styled.div<{ $activeOption: string }>`
  display: flex;
  width: 90px;
  height: 35px;
  border-radius: 6.25rem;
  border: 1px solid var(--primary);
  background: var(--primary);
  position: absolute;
  margin: 0 0.25rem;
  transition: 0.3s;
  left: ${({ $activeOption }) => ($activeOption === "dog" ? "0" : "92px")};
`;

interface ToggleProps {
  onClick: (buttonId: string) => void;
  gap?: string;
  $activeOption: string;
  buttonData: {
    id: string;
    text: string;
    color: string;
    fontSize: string;
    fontWeight: string;
    icon?: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;
  }[];
}

const Toggle = ({ $activeOption, onClick, gap, buttonData }: ToggleProps) => {
  const toggleHandler = (buttonId: string): void => {
    onClick(buttonId);
  };

  return (
    <ToggleContainer>
      <ToggleBase>
        <ToggleCircle $activeOption={$activeOption} />
        <CategoryBtns
          buttonData={buttonData}
          $activeOption={$activeOption}
          onClick={toggleHandler}
          $gap={gap}
        />
      </ToggleBase>
    </ToggleContainer>
  );
};

export default Toggle;
