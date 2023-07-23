import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { MusicList } from "../components/MusicList";
import Pagination from "../components/Pagination";
import useLikeData from "../hooks/useLikeData";
import { styled } from "styled-components";
import { Music } from "../types/Music";
import { api } from "../utils/Url";
import { current } from "@reduxjs/toolkit";
import { PageInfo } from "../types/PageInfo";

type MusicListData = {
  data: Music[];
  pageInfo?: PageInfo;
};

function Search() {
  const location = useLocation();
  const searchQuery = location.state?.searchQuery;
  const [filteredResults, setFilteredResults] = useState<MusicListData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });
  // const [filteredResults, setFilteredResults] = useState([]); // 필터링된 검색 결과를 저장할 상태 변수를 추가합니다.
  const [currentPage, setCurrentPage] = useState(1);
  const [isLikedClick, setIsLikedClick] = useState(false);
  const handleLike = useLikeData({
    setIsLikedClick,
  });
  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };
  console.log(location.state?.searchQuery);

  useEffect(() => {
    // Fetch search results from the server when the component mounts
    const fetchSearchResults = async () => {
      try {
        const response = await axios.get(
          `https://api.petpil.site:8080/public/playlist/search`,
          {
            params: { title: searchQuery, page: currentPage },
          }
        );
        setFilteredResults(response.data);
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching search results:", error);
      }
    };

    fetchSearchResults();
  }, [searchQuery, currentPage]);

  return (
    <div>
      <SearchTitle>
        <h1>SEARCH RESULT</h1>
      </SearchTitle>
      <MusicList
        musicList={filteredResults.data}
        handleLike={handleLike}
        setIsLikedClick={setIsLikedClick}
      />
      <Pagination
        currentPage={currentPage}
        totalPages={filteredResults.pageInfo?.totalPages || 0}
        onPageChange={handlePageChange}
      />
    </div>
  );
}
export default Search;

const SearchTitle = styled.div`
  width: 100%;
  display: flex;
  margin-bottom: 12px;
  > h1 {
    font-family: var(--font-quicksand);
    font-size: 24px;
    font-style: normal;
    font-weight: 500;
    line-height: 100%;
    color: var(--black);
    text-align: center;
    padding: 1px 6px;
    margin-top: 30px;
  }
`;
