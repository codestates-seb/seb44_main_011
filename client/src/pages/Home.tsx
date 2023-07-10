import { useState, useEffect } from "react";
import { styled } from "styled-components";
import Banner from "../components/commons/Banner";
import CategoryBtns from "../components/commons/CategoryBtns";
import {
  LIST_CATEGORY,
  ANIMAL_CATEGORY,
  TOGGLE_CATEGORY,
} from "../constants/CategoryConstants";
import { MusicList } from "../components/MusicList";
import Pagination from "../components/Pagination";
import { useAllMusicData } from "../hooks/useAllMusicData";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";

const HomeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 3%;
  width: 100%;
  max-width: 1800px;
  min-width: 700px;
`;

const HomsListTitle = styled.div`
  width: 100%;
  display: flex;
  align-items: end;
  justify-content: space-between;
  margin-bottom: 12px;
`;

type MusicListData = {
  data: Music[];
  pageInfo?: PageInfo;
};

const Home = () => {
  const [isDogpli, setIsDogpli] = useState(ANIMAL_CATEGORY[0]?.id);
  const [isTopChart, setIsTopChart] = useState(LIST_CATEGORY[0]?.id);
  const [currentPage, setCurrentPage] = useState(1);

  const [musicList, setMusicList] = useState<MusicListData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  const { data: musicListData, requestPath } = useAllMusicData(
    isDogpli,
    currentPage
  );

  useEffect(() => {
    setMusicList({ data: musicListData });
  }, [musicListData]);

  const handleAnimalButton = (buttonId: string, tags?: string) => {
    setIsDogpli(buttonId);
    setCurrentPage(1);
  };

  const handleListButton = (buttonId: string) => {
    setIsTopChart(buttonId);
  };

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  useEffect(() => {
    useAllMusicData(isDogpli, currentPage);
  }, [isDogpli, currentPage]);

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
      <MusicList musicList={musicList} requestPath={requestPath} />
      <Pagination
        currentPage={currentPage}
        totalPages={musicList.pageInfo?.totalPages || 0}
        onPageChange={handlePageChange}
      />
    </HomeContainer>
  );
};

export default Home;
