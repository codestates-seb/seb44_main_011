import { useState } from "react";
import { styled } from "styled-components";
import Banner from "../components/commons/Banner";
import CategoryBtns from "../components/commons/CategoryBtns";
import {
  LIST_CATEGORY,
  ANIMAL_CATEGORY,
  TOGGLE_CATEGORY,
} from "../constants/CategoryConstants";
import { MusicList } from "../components/MusicList";
import { logginedMusicList } from "../constants/MusicData";

const HomeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 3%;
  width: 100%;
  max-width: 1800px;
  height: 100vh;
  min-width: 700px;
`;
const HomsListTitle = styled.div`
  width: 100%;
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
    <HomeContainer>
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
      <MusicList musicList={logginedMusicList.data.member_playList} />
    </HomeContainer>
  );
};

export default Home;
