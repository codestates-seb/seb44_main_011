import { styled } from "styled-components";
import { useState } from "react";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as Disliked } from "../assets/icons/disliked.svg";
import { Music } from "../types/Music";
import Empty from "./Empty";
import Profile from "./commons/Profile";
import testImg from "../assets/imgs/testimg.jpg";

const MusicListsContainer = styled.div`
  height: 420px;
  width: 100%;
  flex-shrink: 0;
`;
const StyledMusicList = styled.div<{ $active: string }>`
  width: 100%;
  border-radius: 10px;
  border: 1px solid var(--gray-200);
  background: ${(props) =>
    props.$active === "true" ? "var(--gray-300)" : "var(--gray-100)"};
  display: flex;
  align-items: center;
  font-family: var(--font-quicksand);
  justify-content: space-between;
  font-size: 14px;
  color: var(--black);
  margin-bottom: 12px;
  cursor: pointer;

  span {
    min-width: 150px;
    width: 50%;
  }

  div {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 8px;
  }

  svg {
    margin: 0 12px;
  }
`;

const LikeButton = styled.button`
  border: none;
  background: transparent;
  cursor: pointer;
`;

const Title = styled.span`
  text-align: left;
  min-width: 30px;
  margin-left: 16%;
  width: 60% !important;
`;

const Tag = styled.span`
  color: var(--gray-500);
  font-size: 12px;
  text-align: left;
`;

type MusicListProps = {
  musicList: Music[] | [];
  handleLike: (musicId: number, liked?: boolean) => void;
  handleMusic?: (musicId: number) => void;
};

export const MusicList: React.FC<MusicListProps> = ({
  musicList,
  handleLike,
  handleMusic,
}) => {
  const [clickedMusicId, setClickedMusicId] = useState<number | null>(null);

  const handleLikeClick = (musicId: number, liked?: boolean) => {
    handleLike(musicId, liked);
  };

  const handleMusicClick = (musicId: number) => {
    if (handleMusic) {
      handleMusic(musicId);
      setClickedMusicId(musicId);
    }
  };

  return (
    <MusicListsContainer>
      {musicList?.length ? (
        musicList.map((music) => (
          <StyledMusicList
            key={music.musicId}
            onClick={() => handleMusicClick(music.musicId)}
            $active={(clickedMusicId === music.musicId).toString()}
          >
            <Profile image={testImg} size={40} radius={4} />
            <Title>{music.title}</Title>
            <Tag>{music.tags}</Tag>
            <span>{music.playtime}</span>
            <LikeButton
              onClick={(event: React.MouseEvent) => {
                event.stopPropagation();
                handleLikeClick(music.musicId, music.liked);
              }}
            >
              {music.liked ? <Liked /> : <Disliked />}
            </LikeButton>
          </StyledMusicList>
        ))
      ) : (
        <Empty message="안녕하새오 미아내오 리스트를 못찾갯어오" />
      )}
    </MusicListsContainer>
  );
};
