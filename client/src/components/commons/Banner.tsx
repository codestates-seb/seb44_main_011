import { styled } from "styled-components";
import { ReactComponent as Dog } from "../../assets/imgs/dog.svg";
import { ReactComponent as Cat } from "../../assets/imgs/cat.svg";
import { ReactComponent as Dogpli } from "../../assets/imgs/dogpli.svg";
import { ReactComponent as Catpli } from "../../assets/imgs/catpli.svg";
import Toggle from "./Toggle";

const DogImg = styled(Dog)`
  align-self: flex-end;
  width: 50%;
  height: auto;
`;

const CatImg = styled(Cat)`
  align-self: flex-end;
  width: 50%;
  height: auto;
`;

const BannerDogpliLogo = styled(Dogpli)`
  width: 70%;
  height: 60%;
`;

const BannerCatpliLogo = styled(Catpli)`
  width: 70%;
  height: 60%;
`;

const BannerContainer = styled.div`
  width: 100%;
  height: 300px;
  border-radius: 15px;
  background: var(--gradation-banner);
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
`;

const PliContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
  $activeOption: string;
  onClick: (buttonId: string) => void;
  gap?: string;
}

const Banner = ({ buttonData, $activeOption, gap, onClick }: BannerProps) => {
  const handleToggleClick = (buttonId: string) => {
    onClick(buttonId);
  };

  return (
    <BannerContainer>
      <PliContainer>
        {$activeOption === "dog" ? <BannerDogpliLogo /> : <BannerCatpliLogo />}
        <Toggle
          $activeOption={$activeOption}
          onClick={handleToggleClick}
          gap={gap}
          buttonData={buttonData}
        />
      </PliContainer>
      {$activeOption === "dog" ? <DogImg /> : <CatImg />}
    </BannerContainer>
  );
};

export default Banner;
