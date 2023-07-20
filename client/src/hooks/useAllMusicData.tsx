import axios from "axios";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";
import { GetPublicPlaylist } from "../utils/Url";
import { api } from "../utils/Url";

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

      if (!memberId) {
        try {
          const response = await axios.get<MusicListData>(
            `${GetPublicPlaylist}/${isDogpli}s`,
            {
              params: {
                page: currentPage,
              },
            }
          );
          setMusicList(response.data || { data: [] });
        } catch (error) {
          console.error(error);
        }
      } else {
        try {
          const response = await api.get<MusicListData>(
            `/playlist/${isDogpli}s/id/${memberId}`,
            {
              params: {
                page: currentPage,
              },
            }
          );
          setMusicList(response.data || { data: [] });
        } catch (error) {
          console.error(error);
        }
      }
    };

    fetchData();
  }, [isDogpli, currentPage, isLikedClick]);

  return musicList;
};

export default useAllMusicData;
