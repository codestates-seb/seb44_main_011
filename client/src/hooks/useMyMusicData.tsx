import axios from "axios";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";
import { GetApiPlaylist } from "../utils/Url";

type MusicListData = {
  data: Music[];
  pageInfo?: PageInfo;
};

const useMyMusicData = (
  isLikedClick: boolean,
  currentPage?: number
): MusicListData => {
  const [musicList, setMusicList] = useState<MusicListData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  useEffect(() => {
    const memberId = localStorage.getItem("memberId");
    const accessToken = localStorage.getItem("accessToken");

    const fetchData = async () => {
      try {
        const response = await axios.get(`${GetApiPlaylist}/${memberId}`, {
          params: {
            page: currentPage,
          },
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setMusicList(response.data);
        console.log(response.data);
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
