import { styled } from "styled-components";
import { ReactComponent as Dog } from "../../assets/imgs/dog.svg";
import { ReactComponent as Cat } from "../../assets/imgs/cat.svg";
import { ReactComponent as Dogpli } from "../../assets/imgs/dogpli.svg";
import { ReactComponent as Catpli } from "../../assets/imgs/catpli.svg";
import Toggle from "./Toggle";

const DogImg = styled(Dog)`
  align-items: end;
  margin-top: 52px;
`;

const CatImg = styled(Cat)`
  align-items: end;
  margin-top: 52px;
`;

const BannerContainer = styled.div`
  width: 915px;
  height: 300px;
  border-radius: 15px;
  background: var(--gradation-banner);
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  margin-bottom: 30px;
`;

const PliContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 60px;
`;

interface BannerProps {
  buttonData: {
    id: string;
    text: string;
    color: string;
    fontSize: string;
    fontWeight: string;
    icon?: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;
  }[];
  activeOption: string;
  onClick: (buttonId: string) => void;
  gap?: string;
}

const Banner = ({ buttonData, activeOption, gap, onClick }: BannerProps) => {
  const handleToggleClick = (buttonId: string) => {
    onClick(buttonId);
  };

  return (
    <BannerContainer>
      <PliContainer>
        {activeOption === "dog" ? <Dogpli /> : <Catpli />}
        <Toggle
          activeOption={activeOption}
          onClick={handleToggleClick}
          gap={gap}
          buttonData={buttonData}
        />
      </PliContainer>
      {activeOption === "dog" ? <DogImg /> : <CatImg />}
    </BannerContainer>
  );
};

export default Banner;
