import { api } from "../utils/Url";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";
import saveNewToken from "../utils/saveNewToken";

type MusicListData = {
  data: Music[];
  pageInfo?: PageInfo;
};

const useMyMusicData = (
  isLikedClick?: boolean,
  currentPage?: number
): MusicListData => {
  const [musicList, setMusicList] = useState<MusicListData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  useEffect(() => {
    const memberId = localStorage.getItem("memberId");

    const fetchData = async () => {
      try {
        const response = await api.get<MusicListData>(`/playlist/${memberId}`, {
          params: {
            page: currentPage ? currentPage : 1,
          },
        });
        setMusicList(
          response.data ?? {
            data: [],
            pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
          }
        );
        const accessToken = response.headers["authorization"] || null;
        saveNewToken(accessToken);
      } catch (error) {
        console.error(error);
        setMusicList({
          data: [],
          pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
        });
      }
    };

    fetchData();
  }, [currentPage, isLikedClick]);

  return musicList;
};

export default useMyMusicData;
