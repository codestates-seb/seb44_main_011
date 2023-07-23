import { useState, useEffect } from "react";
import { styled } from "styled-components";
import Banner from "../components/commons/Banner";
import Player from "../components/Player";
import CategoryBtns from "../components/commons/CategoryBtns";
import {
  LIST_CATEGORY,
  ANIMAL_CATEGORY,
  TOGGLE_CATEGORY,
} from "../constants/CategoryConstants";
import { MusicList } from "../components/MusicList";
import Pagination from "../components/Pagination";
import useAllMusicData from "../hooks/useAllMusicData";
import useMusicData from "../hooks/useMusicData";
import useLikeData from "../hooks/useLikeData";
import { setIsDogpli } from "../redux/homeSlice";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../redux/RootStore";

const HomeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 4vh;
  width: 100%;
  max-width: 1800px;
`;

const HomsListTitle = styled.div`
  width: 100%;
  display: flex;
  align-items: end;
  justify-content: space-between;
  margin-bottom: 12px;
  margin-top: 30px;
`;

const Home = () => {
  const [isTopChart, setIsTopChart] = useState(LIST_CATEGORY[0]?.id);
  const [currentPage, setCurrentPage] = useState(1);
  const [isLikedClick, setIsLikedClick] = useState(false);
  const [showMusicList, setShowMusicList] = useState(true);

  const isDogpli = useSelector((state: RootState) => state.home.isDogpli);

  const currentTag = useSelector((state: RootState) => state.tags.currentTag);

  const dispatch = useDispatch();

  const musicList = useAllMusicData(
    isDogpli,
    currentPage,
    isTopChart,
    isLikedClick,
    currentTag
  );

  const { selectedMusic, handleMusic } = useMusicData(isDogpli, isTopChart);

  const handleLike = useLikeData({
    setIsLikedClick,
    handleMusic,
    selectedMusic,
  });

  const handleAnimalButton = (buttonId: string) => {
    dispatch(setIsDogpli(buttonId));
    setCurrentPage(1);
  };

  const handleListButton = (buttonId: string) => {
    setIsTopChart(buttonId);
  };

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handleCommentClick = () => {
    setShowMusicList(!showMusicList);
  };

  useEffect(() => {
    setIsLikedClick(false);
  }, [currentPage, isDogpli, isLikedClick]);

  useEffect(() => {
    setCurrentPage(1);
  }, [isTopChart]);

  return (
    <HomeContainer>
      {!selectedMusic ? (
        <Banner
          buttonData={TOGGLE_CATEGORY}
          $activeOption={isDogpli}
          onClick={handleAnimalButton}
          gap="4"
        />
      ) : (
        <Player
          musicData={selectedMusic}
          handleLike={handleLike}
          handleMusic={handleMusic}
          handleCommentClick={handleCommentClick}
          musicList={musicList.data}
        />
      )}

      {showMusicList && (
        <>
          <HomsListTitle>
            <CategoryBtns
              buttonData={LIST_CATEGORY}
              $activeOption={isTopChart}
              onClick={handleListButton}
              $gap="8"
            />
            <CategoryBtns
              buttonData={ANIMAL_CATEGORY}
              $activeOption={isDogpli}
              onClick={handleAnimalButton}
            />
          </HomsListTitle>
          <MusicList
            musicList={musicList.data}
            handleLike={handleLike}
            handleMusic={handleMusic}
            isDogpli={isDogpli}
            isTopChart={isTopChart}
            loading={false}
            setIsLikedClick={setIsLikedClick}
            selectedMusicId={selectedMusic?.musicId}
          />
          <Pagination
            currentPage={currentPage}
            totalPages={musicList.pageInfo?.totalPages || 0}
            onPageChange={handlePageChange}
          />
        </>
      )}
    </HomeContainer>
  );
};

export default Home;
