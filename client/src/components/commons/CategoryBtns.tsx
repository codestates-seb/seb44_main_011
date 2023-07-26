import styled, { css } from "styled-components";

const CategoryBtnsContainer = styled.div<{ $gap?: string }>`
  display: flex;
  flex-direction: row;
  ${({ $gap }) => $gap && `gap: ${$gap}px`};
  cursor: pointer;
`;

const CategoryContainer = styled.button<{
  $active?: string;
  color: string;
  fontSize: string;
  fontWeight: string;
}>`
  cursor: pointer;
  border: 0;
  background-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
  font-family: var(--font-quicksand);
  color: ${({ $active, color }) => ($active === "true" ? color : "#fff")};
  font-size: ${({ fontSize }) => fontSize}px;
  font-weight: ${({ fontWeight }) => fontWeight};

  > svg {
    margin-right: 4px;
    fill: ${({ color }) => color};
  }

  ${({ $active }) =>
    $active === "false" &&
    css`
      color: var(--gray-300);
      > svg {
        fill: var(--gray-300);
      }

      :hover {
        color: var(--gray-500);
        > svg {
          fill: var(--gray-500);
        }
      }
    `};
`;

interface CategoryButtonProps {
  $active: string;
  onClick: () => void;
  text: string;
  color: string;
  fontSize: string;
  fontWeight: string;
  icon?: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;
}

const CategoryButton: React.FC<CategoryButtonProps> = ({
  $active,
  onClick,
  text,
  color,
  fontSize,
  fontWeight,
  icon: Icon,
}) => (
  <CategoryContainer
    $active={$active}
    color={color}
    fontSize={fontSize}
    fontWeight={fontWeight}
    onClick={onClick}
  >
    {Icon && <Icon />}
    {text}
  </CategoryContainer>
);

interface CategoryBtnsProps {
  buttonData: {
    id: string;
    text: string;
    color: string;
    fontSize: string;
    fontWeight: string;
    icon?: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;
  }[];
  $activeOption: string;
  onClick: (option: string) => void;
  $gap?: string;
}

const CategoryBtns: React.FC<CategoryBtnsProps> = ({
  buttonData = [],
  $activeOption,
  onClick,
  $gap = "0",
}) => {
  const handleButtonClick = (buttonId: string) => {
    onClick(buttonId);
  };

  return (
    <CategoryBtnsContainer $gap={$gap}>
      {buttonData.map((data) => {
        return (
          <CategoryButton
            key={data.id}
            $active={($activeOption === data.id).toString()}
            onClick={() => handleButtonClick(data.id)}
            text={data.text}
            color={data.color}
            fontSize={data.fontSize}
            fontWeight={data.fontWeight}
            icon={data.icon}
          />
        );
      })}
    </CategoryBtnsContainer>
  );
};

export default CategoryBtns;
