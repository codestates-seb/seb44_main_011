import { useState } from "react";
import { styled } from "styled-components";
import Banner from "../components/commons/Banner";
import CategoryBtns from "../components/commons/CategoryBtns";
import {
  LIST_CATEGORY,
  ANIMAL_CATEGORY,
  TOGGLE_CATEGORY,
} from "../constants/CategoryConstants";
import MusicList from "../components/MusicList";

const HomsListTitle = styled.div`
  width: 915px;
  display: flex;
  align-items: end;
  justify-content: space-between;
  margin-bottom: 12px;
`;
const Home = () => {
  const [isDogpli, setIsDogpli] = useState(ANIMAL_CATEGORY[0]?.id);
  const [isTopChart, setIsTopChart] = useState(LIST_CATEGORY[0]?.id);

  const handleAnimalButton = (buttonId: string) => {
    setIsDogpli(buttonId);
  };

  const handleListButton = (buttonId: string) => {
    setIsTopChart(buttonId);
  };

  return (
    <>
      <Banner
        buttonData={TOGGLE_CATEGORY}
        activeOption={isDogpli}
        onClick={handleAnimalButton}
        gap="4"
      />

      <HomsListTitle>
        <CategoryBtns
          buttonData={LIST_CATEGORY}
          activeOption={isTopChart}
          onClick={handleListButton}
          gap="8"
        />
        <CategoryBtns
          buttonData={ANIMAL_CATEGORY}
          activeOption={isDogpli}
          onClick={handleAnimalButton}
        />
      </HomsListTitle>
      <MusicList></MusicList>
    </>
  );
};

export default Home;
