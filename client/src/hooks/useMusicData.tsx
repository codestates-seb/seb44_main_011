import { useState, useEffect } from "react";
import axios from "axios";
import { GetPublicMusic, api } from "../utils/Url";
import { Music } from "../types/Music";
import saveNewToken from "../utils/saveNewToken";

type useMusicDataProps = {
  selectedMusic: Music | null;
  handleMusic: (musicId: number) => Promise<void>;
};

const useMusicData = (
  isDogpli?: string,
  isTopChart?: string
): useMusicDataProps => {
  const [selectedMusic, setSelectedMusic] = useState<Music | null>(null);

  const handleMusic = async (musicId: number) => {
    const memberId = localStorage.getItem("memberId");

    try {
      if (!memberId) {
        const response = await axios.get<Music | undefined>(GetPublicMusic, {
          params: {
            music_id: musicId,
          },
        });
        if (response.status === 200) {
          setSelectedMusic(response.data ?? null);
        } else {
          console.error("음악재생에 실패하였습니다.");
        }
      } else {
        const response = await api.get<Music | undefined>(
          `/musics/${memberId}`,
          {
            params: {
              music_id: musicId,
            },
          }
        );

        const accessToken = response.headers["authorization"] || null;
        saveNewToken(accessToken);

        if (response.status === 200) {
          setSelectedMusic(response.data ?? null);
        } else {
          console.error("음악재생에 실패하였습니다.");
        }
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    setSelectedMusic(null);
  }, [isDogpli, isTopChart]);

  return { selectedMusic, handleMusic };
};

export default useMusicData;
