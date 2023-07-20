import { api } from "../utils/Url";
import { Music } from "../types/Music";
import saveNewToken from "../utils/saveNewToken";

type useLikeDataProps = {
  setIsLikedClick: React.Dispatch<React.SetStateAction<boolean>>;
  handleMusic?: (musicId: number) => void;
  selectedMusic?: Music | null;
};

const useLikeData = ({
  setIsLikedClick,
  handleMusic,
  selectedMusic,
}: useLikeDataProps) => {
  const handleLike = async (musicId: number, liked?: boolean) => {
    const memberId = localStorage.getItem("memberId");

    if (!memberId) {
      alert("로그인이 필요합니다.");
    } else {
      try {
        const response = await api.request({
          method: liked ? "DELETE" : "POST",
          url: `playlist/${memberId}`,
          data: {
            musicId: musicId,
          },
        });
        const accessToken = response.headers["authorization"] || null;
        saveNewToken(accessToken);
        if (response.status === 201 || response.status === 204) {
          setIsLikedClick(true);
          if (selectedMusic && handleMusic) handleMusic(selectedMusic.musicId);
        } else {
          console.error("좋아요 처리에 실패했습니다.");
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  return handleLike;
};

export default useLikeData;
