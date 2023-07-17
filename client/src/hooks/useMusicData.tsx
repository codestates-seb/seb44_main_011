import { useState, useEffect } from "react";
import axios, { AxiosRequestConfig } from "axios";
import { GetPublicMusic, GetApiMusic } from "../utils/Url";
import { Music } from "../types/Music";

type useMusicDataProps = {
  selectedMusic: Music | null;
  handleMusic: (musicId: number) => Promise<void>;
};

const useMusicData = (isDogpli: string): useMusicDataProps => {
  const [selectedMusic, setSelectedMusic] = useState<Music | null>(null);

  const handleMusic = async (musicId: number) => {
    const memberId = localStorage.getItem("memberId");
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refresh");

    const config: AxiosRequestConfig = {
      params: {
        music_id: musicId,
      },
    };

    if (memberId && accessToken) {
      config.headers = {
        Authorization: `Bearer ${accessToken}`,
        "x-refresh-token": refreshToken,
      };
      config.url = `${GetApiMusic}/${memberId}`;
    } else {
      config.url = `${GetPublicMusic}`;
    }

    try {
      const response = await axios.get(config.url, config);

      if (response.status === 200) {
        setSelectedMusic(response.data);
      } else {
        console.error("음악재생에 실패하였습니다.");
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    setSelectedMusic(null);
  }, [isDogpli]);

  return { selectedMusic, handleMusic };
};

export default useMusicData;
