import { styled } from "styled-components";
import { useState, useEffect } from "react";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as DeleteIcon } from "../assets/icons/deleteicon.svg";
import { Music } from "../types/Music";
import Empty from "./Empty";
import Profile from "./commons/Profile";
import Loading from "./commons/Loading";
import { BaseURL } from "../utils/Url";
import axios from "axios";

const MusicListsContainer = styled.div`
  height: 420px;
  width: 100%;
  flex-shrink: 0;
`;
const StyledMusicList = styled.div<{ $active: boolean }>`
  width: 100%;
  border-radius: 10px;
  border: 1px solid var(--gray-200);
  background: ${(props) =>
    props.$active ? "var(--gray-300)" : "var(--gray-100)"};
  display: flex;
  align-items: center;
  font-family: var(--font-quicksand);
  justify-content: space-between;
  font-size: 14px;
  color: var(--black);
  margin-bottom: 12px;
  cursor: pointer;

  span {
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

const ButtonContainer = styled.div`
  gap: 16px;
`;

const Button = styled.button`
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
  isDogpli?: string;
  isTopChart?: string;
  loading?: boolean;
  setIsLikedClick: (value: boolean) => void;
  selectedMusicId?: number;
};

export const MusicList: React.FC<MusicListProps> = ({
  musicList,
  handleLike,
  handleMusic,
  isDogpli,
  isTopChart,
  loading,
  setIsLikedClick,
  selectedMusicId,
}) => {
  const [active, setActive] = useState(true);

  const handleLikeClick = (musicId: number, liked?: boolean) => {
    handleLike(musicId, liked);
  };

  const role = localStorage.getItem("role");

  const handleMusicClick = (musicId: number) => {
    if (handleMusic) {
      handleMusic(musicId);
      setActive(true);
    }
  };

  const handleDeleteClick = async (musicId: number) => {
    const confirmed = window.confirm("음악을 삭제하시겠습니까?");

    if (confirmed) {
      try {
        const response = await axios.delete(
          `${BaseURL}/admin/music/id/${musicId}`
        );
        if (response.status === 204) {
          setIsLikedClick(true);
        }
      } catch (error) {
        console.error(error);
        alert("음악 삭제에 실패했습니다.");
      }
    }
  };

  useEffect(() => {
    setActive(false);
  }, [isDogpli, isTopChart]);

  return (
    <MusicListsContainer>
      {loading ? (
        <Loading />
      ) : musicList?.length ? (
        musicList.map((music) => (
          <StyledMusicList
            key={music.musicId}
            onClick={() => handleMusicClick(music.musicId)}
            $active={selectedMusicId === music.musicId && active}
          >
            <Profile
              image={music.image_url}
              size={40}
              radius={4}
              alt={"Cover Image"}
            />
            <Title>{music.title}</Title>
            <Tag>{music.tags}</Tag>
            <span>{music.playtime}</span>
            <ButtonContainer>
              <Button
                onClick={(event: React.MouseEvent) => {
                  event.stopPropagation();
                  handleLikeClick(music.musicId, music.liked);
                }}
              >
                <Liked
                  fill={music.liked ? "#FF7777" : "none"}
                  stroke={music.liked ? "none" : "#212121"}
                />
              </Button>
              {role === "ADMIN" && (
                <Button
                  onClick={(event: React.MouseEvent) => {
                    event.stopPropagation();
                    handleDeleteClick(music.musicId);
                  }}
                >
                  <DeleteIcon stroke="#212121" />
                </Button>
              )}
            </ButtonContainer>
          </StyledMusicList>
        ))
      ) : (
        <Empty message="안녕하새오 미아내오 리스트를 못찾갯어오" />
      )}
    </MusicListsContainer>
  );
};
