import axios from "axios";
import { useState, useEffect } from "react";
import { Music } from "../types/Music";
import { PageInfo } from "../types/PageInfo";

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

  // const memberId = localStorage.getItem("memberId");
  const memberId = 1;

  useEffect(() => {
    const fetchData = async () => {
      let requestPath = `/public/playlist/${isDogpli}s`;
      if (memberId) {
        requestPath = `/api/playlist/${isDogpli}s/id/${memberId}`;
      }

      try {
        const response = await axios.get(
          `http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080${requestPath}`,
          {
            params: {
              page: currentPage,
            },
          }
        );
        console.log(response.data);
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
