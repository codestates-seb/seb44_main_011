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
import useAllMusicData from "../hooks/useAllMusicData";
import axios from "axios";

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

const Home = () => {
  const [isDogpli, setIsDogpli] = useState(ANIMAL_CATEGORY[0]?.id);
  const [isTopChart, setIsTopChart] = useState(LIST_CATEGORY[0]?.id);
  const [currentPage, setCurrentPage] = useState(1);
  const [isLikedClick, setIsLikedClick] = useState(false);

  const musicList = useAllMusicData(isDogpli, currentPage, isLikedClick);

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

  const handleLike = async (musicId: number, liked?: boolean) => {
    // const memberId = localStorage.getItem("memberId");
    const memberId = 1;

    if (!memberId) {
      alert("로그인이 필요합니다.");
    } else {
      try {
        const response = await axios.request({
          method: liked ? "DELETE" : "POST",
          url: `http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/api/playlist/${memberId}`,
          data: {
            musicId: musicId,
          },
        });

        if (response.status === 201 || response.status === 204) {
          setIsLikedClick(true);
        } else {
          console.error("좋아요 처리에 실패했습니다.");
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  useEffect(() => {
    setIsLikedClick(false);
  }, [currentPage, isDogpli, isLikedClick]);

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
      <MusicList musicList={musicList.data} handleLike={handleLike} />
      <Pagination
        currentPage={currentPage}
        totalPages={musicList.pageInfo?.totalPages || 0}
        onPageChange={handlePageChange}
      />
    </HomeContainer>
  );
};

export default Home;
