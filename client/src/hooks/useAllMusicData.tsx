import axios, { AxiosRequestConfig } from "axios";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";
import { GetPublicPlaylist, GetApiPlaylist } from "../utils/Url";

type MusicListData = {
  data: Music[];
  pageInfo?: PageInfo;
};

const useAllMusicData = (
  isDogpli: string,
  currentPage: number,
  isLikedClick: boolean
): MusicListData => {
  const [musicList, setMusicList] = useState<MusicListData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  useEffect(() => {
    const fetchData = async () => {
      const memberId = localStorage.getItem("memberId");
      const accessToken = localStorage.getItem("accessToken");

      const config: AxiosRequestConfig = {
        params: {
          page: currentPage,
        },
      };

      if (memberId && accessToken) {
        config.headers = {
          Authorization: `Bearer ${accessToken}`,
        };
        config.url = `${GetApiPlaylist}/${isDogpli}s/id/${memberId}`;
      } else {
        config.url = `${GetPublicPlaylist}/${isDogpli}s`;
      }

      try {
        const response = await axios.get(config.url, config);
        setMusicList(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, [isDogpli, currentPage, isLikedClick]);

  return musicList;
};

export default useAllMusicData;
