import { styled } from "styled-components";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as Disliked } from "../assets/icons/disliked.svg";

const MusicListContainer = styled.div`
  width: 100%;
  height: 5%;
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

const Tag = styled.span`
  color: var(--gray-500);
  font-size: 12px;
`;

type Music = {
  musicId: number;
  title: string;
  music_url: string;
  image_url: string;
  tags: string;
  likes?: boolean;
};

type MusicListProps = {
  musicList: Music[];
};

export const MusicList: React.FC<MusicListProps> = ({ musicList }) => {
  return (
    <>
      {musicList.map((music) => (
        <MusicListContainer key={music.musicId}>
          <img src={music.image_url} alt="listimg" />
          <span>{music.title}</span>
          <Tag>{music.tags}</Tag>
          <span>4:17</span>
          {music.likes ? <Liked /> : <Disliked />}
        </MusicListContainer>
      ))}
    </>
  );
};
