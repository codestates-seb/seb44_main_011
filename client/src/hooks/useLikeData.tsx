import axios from "axios";
import { GetApiPlaylist } from "../utils/Url";

type useLikeDataProps = {
  setIsLikedClick: React.Dispatch<React.SetStateAction<boolean>>;
};

const useLikeData = ({ setIsLikedClick }: useLikeDataProps) => {
  const handleLike = async (musicId: number, liked?: boolean) => {
    const memberId = localStorage.getItem("memberId");
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refresh");

    if (!memberId) {
      alert("로그인이 필요합니다.");
    } else {
      try {
        const response = await axios.request({
          method: liked ? "DELETE" : "POST",
          url: `${GetApiPlaylist}/${memberId}`,
          data: {
            musicId: musicId,
          },
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "x-refresh-token": refreshToken,
          },
        });

        if (response.status === 201 || response.status === 204) {
          setIsLikedClick(true);
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
