import { useState, useEffect } from "react";
import { styled } from "styled-components";
import { Music } from "../types/Music";
import CustomAudioPlayer from "./CustomAudioPlayer";
import Profile from "./commons/Profile";
import Empty from "./Empty";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as CommentIcon } from "../assets/icons/coment.svg";
import { ReactComponent as Reapeat } from "../assets/icons/repeat.svg";
import { ReactComponent as ReapeatOff } from "../assets/icons/repeatAllOff.svg";
import CommentSection from "./CommentSection";

const StyledPlayer = styled.div`
  flex-direction: row;
  display: flex;
  padding: 30px;
  align-items: center;
`;
const PlayerContainer = styled.div<{ $expanded?: boolean }>`
  width: 100%;
  height: ${({ $expanded }) => ($expanded ? "fit-content" : "300px")};
  border-radius: 15px;
  position: relative;
  display: flex;
  flex-direction: column;
`;

const BackGroundContainer = styled.div<{ $expanded?: boolean }>`
  width: 100%;
  height: ${({ $expanded }) => ($expanded ? "fit-content" : "300px")};
  border-radius: 15px;
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  background: linear-gradient(
    180deg,
    rgba(180, 180, 183, 0) 0%,
    rgba(33, 33, 33, 0.222) 100%
  );

  backdrop-filter: blur(50px);
  border: 1px solid var(--gray-100);
`;

const ImgContainer = styled.div<{ $image: string; $expanded?: boolean }>`
  width: 100%;
  height: ${({ $expanded }) => ($expanded ? "fit-content" : "300px")};
  border-radius: 15px;
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
  background: url(${(props) => props.$image});
  background-size: cover;
  background-position: center;
`;

const PlayInfo = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-shrink: 1;
  margin-left: 48px;
  gap: 28px;
`;

const ButtonContainer = styled.div`
  z-index: 1;
  align-self: start;
  margin: 30px;
  position: absolute;
  right: 0;
  top: 0;
`;

const Button = styled.button`
  border: none;
  background: transparent;
  cursor: pointer;
  margin-left: 4px;
`;

const MusicTitle = styled.div`
  color: var(--white);
  font-family: var(--font-quicksand);
  font-size: 36px;
  font-weight: 700;
  line-height: 100%;
  cursor: default;
`;

const MusicTag = styled.div`
  color: var(--white);
  background-color: var(--skyblue-200);
  font-family: var(--font-quicksand);
  font-size: 13.5px;
  font-weight: 400;
  line-height: 16px;
  width: fit-content;
  padding: 4px 8px;
  border-radius: 100px;
  text-align: center;
  cursor: default;
`;

type PlayerProps = {
  musicData: Music | null;
  handleLike: (musicId: number, liked?: boolean) => void;
  handleCommentClick: () => void;
  handleMusic: (musicId: number) => void;
  musicList: Music[] | [];
  currentPage: number;
  totalPage: number;
  handlePageChange: (pagenumber: number) => void;
};

const Player = ({
  musicData,
  handleLike,
  handleCommentClick,
  handleMusic,
  musicList,
  currentPage,
  totalPage,
  handlePageChange,
}: PlayerProps) => {
  const [expanded, setExpanded] = useState(false);
  const [isPlayAll, setIsPlayAll] = useState(false);
  const [isPlayedAll, setIsPlayedAll] = useState(true);

  const handlePlayAllClick = () => {
    setIsPlayAll(!isPlayAll);
  };

  const handleLikeClick = (musicId: number, liked?: boolean) => {
    handleLike(musicId, liked);
    handleMusic(musicId);
  };

  const handleCommentBtnClick = () => {
    setExpanded(!expanded);
    handleCommentClick();
  };

  const handleSongEnded = () => {
    if (isPlayAll) {
      const currentMusicId = musicData?.musicId;

      const currentIndex = musicList.findIndex(
        (music) => music.musicId === currentMusicId
      );

      const nextMusic = musicList[currentIndex + 1];

      if (nextMusic) {
        handleMusic(nextMusic.musicId);
      } else {
        if (totalPage >= currentPage + 1) {
          handlePageChange(currentPage + 1);
          setIsPlayedAll(true);
        } else {
          handlePageChange(1);
          setIsPlayedAll(true);
        }
      }
    }
  };

  useEffect(() => {
    if (isPlayedAll && musicList.length > 0) {
      handleMusic(musicList[0].musicId);
      setIsPlayedAll(false);
    }
  }, [musicList]);

  return (
    <>
      {musicData ? (
        <ImgContainer $image={musicData.image_url} $expanded={expanded}>
          <BackGroundContainer $expanded={expanded}>
            <PlayerContainer $expanded={expanded}>
              <StyledPlayer>
                <Profile
                  image={musicData.image_url}
                  size={250}
                  radius={12}
                  alt={"Cover Image"}
                />
                <ButtonContainer>
                  <Button onClick={handlePlayAllClick}>
                    {isPlayAll ? <Reapeat fill="#212121" /> : <ReapeatOff />}
                  </Button>

                  <Button
                    onClick={(event: React.MouseEvent) => {
                      event.stopPropagation();
                      handleLikeClick(musicData.musicId, musicData.liked);
                    }}
                  >
                    <Liked
                      fill={musicData.liked ? "#FF7777" : "none"}
                      stroke={musicData.liked ? "none" : "#212121"}
                    />
                  </Button>
                  <Button onClick={handleCommentBtnClick}>
                    <CommentIcon
                      stroke={expanded ? "none" : "#212121"}
                      fill={expanded ? "#84CBFF" : "none"}
                    />
                  </Button>
                </ButtonContainer>
                <PlayInfo>
                  <MusicTitle>{musicData.title}</MusicTitle>
                  <MusicTag># {musicData.tags}</MusicTag>
                  <CustomAudioPlayer
                    src={musicData.music_url}
                    handleSongEnded={handleSongEnded}
                  />
                </PlayInfo>
              </StyledPlayer>
              {expanded && <CommentSection musicId={musicData.musicId} />}
            </PlayerContainer>
          </BackGroundContainer>
        </ImgContainer>
      ) : (
        <BackGroundContainer>
          <PlayerContainer>
            <Empty message={"안녕하새오 미아내오 재생할 음악을 못찾갯어오"} />
          </PlayerContainer>
        </BackGroundContainer>
      )}
    </>
  );
};

export default Player;
