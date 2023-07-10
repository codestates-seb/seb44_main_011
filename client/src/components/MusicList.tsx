import { styled } from "styled-components";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as Disliked } from "../assets/icons/disliked.svg";
import { Music } from "../types/Music";
import Empty from "./Empty";
import axios from "axios";

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
  requestPath: string;
};

export const MusicList: React.FC<MusicListProps> = ({
  musicList,
  requestPath,
}) => {
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
        } else {
          console.error("좋아요 처리에 실패했습니다.");
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <>
      {musicList?.length ? (
        musicList.map((music) => (
          <MusicListContainer key={music.musicId}>
            <img src={music.image_url} alt="listimg" />
            <span>{music.title}</span>
            <Tag>{music.tags}</Tag>
            <span>4:17</span>
            <LikeButton onClick={() => handleLike(music.musicId, music.liked)}>
              {music.liked ? <Liked /> : <Disliked />}
            </LikeButton>
          </MusicListContainer>
        ))
      ) : (
        <Empty message="리스트가 없습니다." />
      )}
    </>
  );
};
