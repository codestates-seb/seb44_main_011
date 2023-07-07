import { styled } from "styled-components";
import { ReactComponent as Liked } from "../assets/icons/liked.svg";
import listImg from "../assets/imgs/listimg.png";

const MusicListContainer = styled.div`
  width: 915px;
  height: 52px;
  border-radius: 10px;
  border: 1px solid var(--gray-200);
  background: var(--gray-100);
  display: flex;
  align-items: center;
  font-family: var(--font-quicksand);
  justify-content: space-between;
  font-size: 14px;

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

const MusicList = () => {
  return (
    <MusicListContainer>
      <img src={listImg} alt="listimg" />
      <span>Let me love you</span>
      <Tag>#발랄한</Tag>
      <span>4:17</span>
      <Liked />
    </MusicListContainer>
  );
};

export default MusicList;
