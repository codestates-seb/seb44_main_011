import { useState, useEffect } from "react";
import { styled } from "styled-components";
import { MusicList } from "../components/MusicList";
import Pagination from "../components/Pagination";
import useMyMusicData from "../hooks/useMyMusicData";
import Player from "../components/Player";
import useMusicData from "../hooks/useMusicData";
import useLikeData from "../hooks/useLikeData";

const MyListContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 4vh;
  width: 100%;
  max-width: 1800px;
  /* min-width: 700px; */
  padding: 1px 6px;
`;

const MyListTitle = styled.div`
  width: 100%;
  display: flex;
  margin-bottom: 12px;

  > h1 {
    font-family: var(--font-quicksand);
    font-size: 24px;
    font-style: normal;
    font-weight: 500;
    line-height: 100%;
    color: var(--black);
    text-align: center;
    padding: 1px 6px;
    margin-top: 30px;
  }
`;

const MyList = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [isLikedClick, setIsLikedClick] = useState(false);
  const [showMusicList, setShowMusicList] = useState(true);

  const musicList = useMyMusicData(isLikedClick, currentPage);

  const { selectedMusic, handleMusic } = useMusicData();

  const handleLike = useLikeData({ setIsLikedClick });

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handleCommentClick = () => {
    setShowMusicList(!showMusicList);
  };

  useEffect(() => {
    setIsLikedClick(false);
  }, [currentPage, isLikedClick]);

  return (
    <MyListContainer>
      <Player
        musicData={selectedMusic}
        handleLike={handleLike}
        handleCommentClick={handleCommentClick}
      />
      {showMusicList && (
        <>
          <MyListTitle>
            <h1>MYLIST</h1>
          </MyListTitle>
          <MusicList
            musicList={musicList.data}
            handleLike={handleLike}
            handleMusic={handleMusic}
          />
          <Pagination
            currentPage={currentPage}
            totalPages={musicList.pageInfo?.totalPages || 0}
            onPageChange={handlePageChange}
          />
        </>
      )}
    </MyListContainer>
  );
};

export default MyList;
