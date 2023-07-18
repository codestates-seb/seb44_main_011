import { useState, useEffect } from "react";
import { styled } from "styled-components";
import { MusicList } from "../components/MusicList";
import Pagination from "../components/Pagination";
import useMyMusicData from "../hooks/useMyMusicData";
import axios from "axios";
import Player from "../components/Player";
import { Music } from "../types/Music";

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
  const [selectedMusic, setSelectedMusic] = useState<Music | null>(null);
  const [showMusicList, setShowMusicList] = useState(true);

  const musicList = useMyMusicData(isLikedClick, currentPage);

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handleCommentClick = () => {
    setShowMusicList(!showMusicList);
  };

  const handleLike = async (musicId: number, liked?: boolean) => {
    const memberId = localStorage.getItem("memberId");
    const accessToken = localStorage.getItem("accessToken");

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
          headers: {
            Authorization: `Bearer ${accessToken}`,
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

  const handleMusic = async (musicId: number) => {
    try {
      const response = await axios.get(
        "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/public/musics",
        {
          params: {
            music_id: musicId,
          },
        }
      );

      if (response.status === 200) {
        setSelectedMusic(response.data);
        console.log(response.data);
      } else {
        console.error("음악재생에 실패하였습니다.");
      }
    } catch (error) {
      console.error(error);
    }
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
