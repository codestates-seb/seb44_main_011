import { styled } from "styled-components";
import { MypageInfo } from "../components/MypageInfo";
import { MusicList } from "../components/MusicList";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import useMyMusicData from "../hooks/useMyMusicData";
import useLikeData from "../hooks/useLikeData";

function MyPage() {
  const [isLikedClick, setIsLikedClick] = useState(false);

  const musicList = useMyMusicData(isLikedClick);

  const handleLike = useLikeData({
    setIsLikedClick,
  });

  useEffect(() => {
    setIsLikedClick(false);
  }, [isLikedClick]);

  return (
    <MyPageContainer>
      <MypageInfo />
      <MyPageTitle>
        <h1>MYLIST</h1>
        <Link to={"/mylist"}>+더보기</Link>
      </MyPageTitle>
      <MusicList
        musicList={musicList.data}
        handleLike={handleLike}
        setIsLikedClick={setIsLikedClick}
      />
    </MyPageContainer>
  );
}

export default MyPage;

const MyPageTitle = styled.div`
  width: 100%;
  display: flex;
  margin-bottom: 12px;
  justify-content: space-between;
  align-items: end;

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

  > a {
    text-decoration: none;
    color: var(--gray-300);
    font-family: var(--font-quicksand);
    font-size: 14px;
    margin-right: 8px;
  }
`;

const MyPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 4vh;
  width: 100%;
  max-width: 1800px;
  /* min-width: 700px; */
  padding: 1px 6px;
`;
