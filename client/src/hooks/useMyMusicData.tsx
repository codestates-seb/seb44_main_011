import axios from "axios";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";

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

  const memberId = localStorage.getItem("memberId");

  useEffect(() => {
    const fetchData = async () => {
      const requestPath = `/api/playlist/${memberId}`;

      try {
        const response = await axios.get(
          `http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080${requestPath}`,
          {
            params: {
              page: currentPage || 1,
            },
          }
        );
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
