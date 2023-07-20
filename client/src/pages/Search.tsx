import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { MusicList } from "../components/MusicList";

function Search() {
  const location = useLocation();
  const searchQuery = new URLSearchParams(location.search).get("q") || "";
  // const musicList = useAllMusicData(isDogpli, currentPage, isLikedClick);
  // 검색 결과 상태
  const [searchResults, setSearchResults] = useState([]);
  const [filteredResults, setFilteredResults] = useState([]); // 필터링된 검색 결과를 저장할 상태 변수를 추가합니다.

  // 검색 결과를 가져오는 함수
  const getSearchResults = async () => {
    try {
      const response = await axios.get(
        "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/public/playlist",
        {
          params: {
            page: 1,
            q: searchQuery,
          },
        }
      );
      const data = response.data;
      console.log(response.data);
      // 서버에서 받아온 검색 결과를 setSearchResults로 상태 업데이트
      setSearchResults(data.results);
    } catch (error) {
      console.error("검색 결과를 가져오는데 에러가 발생했습니다:", error);
    }
  };

  // 컴포넌트가 처음 렌더링 될 때 검색 결과를 가져오도록 useEffect를 사용
  useEffect(() => {
    getSearchResults();
  }, [searchQuery]);

  // useEffect(() => {
  //   const filtered = searchResults.filter(
  //     (music) =>
  //       music.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
  //       music.tags.toLowerCase().includes(searchQuery.toLowerCase())
  //   );
  //   setFilteredResults(filtered);
  // }, [searchResults, searchQuery]);

  return (
    <div>
      {/* <MusicList musicList={filteredResults} /> */}
      hello
    </div>
  );
}

export default Search;
