import { useState } from "react";
import { styled } from "styled-components";
import { Music } from "../types/Music";
import CustomAudioPlayer from "./CustomAudioPlayer";
import Profile from "./commons/Profile";
import Empty from "./Empty";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import { ReactComponent as Disliked } from "../assets/icons/disliked.svg";
import { ReactComponent as CommentIcon } from "../assets/icons/coment.svg";
import { ReactComponent as LineComment } from "../assets/icons/linecomment.svg";
import CommentSection from "./CommentSection";

const StyledPlayer = styled.div`
  flex-direction: row;
  display: flex;
  padding: 30px;
  align-items: center;
`;
const PlayerContainer = styled.div<{ $expanded?: string }>`
  width: 100%;
  height: ${({ $expanded }) =>
    $expanded === "true" ? "fit-content" : "300px"};
  border-radius: 15px;
  position: relative;
  display: flex;
  flex-direction: column;
`;

const BackGroundContainer = styled.div<{ $expanded?: string }>`
  width: 100%;
  height: ${({ $expanded }) =>
    $expanded === "true" ? "fit-content" : "300px"};
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

const ImgContainer = styled.div<{ $image: string; $expanded?: string }>`
  width: 100%;
  height: ${({ $expanded }) =>
    $expanded === "true" ? "fit-content" : "300px"};
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
  font-size: 12px;
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
};

const Player = ({ musicData, handleLike, handleCommentClick }: PlayerProps) => {
  const [expanded, setExpanded] = useState(false);

  const handleLikeClick = (musicId: number, liked?: boolean) => {
    handleLike(musicId, liked);
  };

  const handleCommentBtnClick = () => {
    setExpanded(!expanded);
    handleCommentClick();
  };

  return (
    <>
      {musicData ? (
        <ImgContainer $image={musicData.image_url} $expanded={String(expanded)}>
          <BackGroundContainer $expanded={String(expanded)}>
            <PlayerContainer $expanded={String(expanded)}>
              <StyledPlayer>
                <Profile image={musicData.image_url} size={250} radius={12} />
                <ButtonContainer>
                  <Button
                    onClick={(event: React.MouseEvent) => {
                      event.stopPropagation();
                      handleLikeClick(musicData.musicId, musicData.liked);
                    }}
                  >
                    {musicData.liked ? <Liked /> : <Disliked />}
                  </Button>
                  <Button onClick={handleCommentBtnClick}>
                    {String(expanded) === "false" ? (
                      <LineComment stroke="#212121" />
                    ) : (
                      <CommentIcon fill="#84CBFF" />
                    )}
                  </Button>
                </ButtonContainer>
                <PlayInfo>
                  <MusicTitle>{musicData.title}</MusicTitle>
                  <MusicTag># {musicData.tags}</MusicTag>
                  <CustomAudioPlayer src={musicData.music_url} />
                </PlayInfo>
              </StyledPlayer>
              {String(expanded) === "true" && (
                <CommentSection musicId={musicData.musicId} />
              )}
            </PlayerContainer>
          </BackGroundContainer>
        </ImgContainer>
      ) : (
        <BackGroundContainer>
          <PlayerContainer>
            <Empty message="안녕하새오 미아내오 재생할 음악을 못찾갯어오" />
          </PlayerContainer>
        </BackGroundContainer>
      )}
    </>
  );
};

export default Player;
