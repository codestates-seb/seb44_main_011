import { styled } from "styled-components";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as Disliked } from "../assets/icons/disliked.svg";
import { Music } from "../types/Music";
import Empty from "./Empty";

const MusicListsContainer = styled.div`
  height: 350px;
  width: 100%;
`;
const StyledMusicList = styled.div`
  width: 100%;
  border-radius: 10px;
  border: 1px solid var(--gray-200);
  background: var(--gray-100);
  display: flex;
  align-items: center;
  font-family: var(--font-quicksand);
  justify-content: space-between;
  font-size: 14px;
  color: var(--black);
  margin-bottom: 12px;
  cursor: pointer;

  img {
    display: flex;
    width: 44px;
    height: 44px;
    justify-content: center;
    align-items: center;
    margin: 0 4px;
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

const Tag = styled.span`
  color: var(--gray-500);
  font-size: 12px;
`;

type MusicListProps = {
  musicList: Music[];
  handleLike: (musicId: number, liked?: boolean) => void;
};

export const MusicList: React.FC<MusicListProps> = ({
  musicList,
  handleLike,
}) => {
  const handleLikeButtonClick = (musicId: number, liked?: boolean) => {
    handleLike(musicId, liked);
  };

  return (
    <MusicListsContainer>
      {musicList?.length ? (
        musicList.map((music) => (
          <StyledMusicList key={music.musicId}>
            <img src={music.image_url} alt="listimg" />
            <span>{music.title}</span>
            <Tag>{music.tags}</Tag>
            <span>4:17</span>
            <LikeButton
              onClick={() => handleLikeButtonClick(music.musicId, music.liked)}
            >
              {music.liked ? <Liked /> : <Disliked />}
            </LikeButton>
          </StyledMusicList>
        ))
      ) : (
        <Empty message="리스트가 없습니다." />
      )}
    </MusicListsContainer>
  );
};
