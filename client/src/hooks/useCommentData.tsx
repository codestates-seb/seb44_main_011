import { useEffect, useState } from "react";
import axios from "axios";
import { CommentData } from "../types/Comment";
import { GetPublicMusic } from "../utils/Url";

type useCommentDataProps = {
  musicId: number;
  currentPage: number;
  isCommentChanged?: boolean;
};

const useCommentData = ({
  musicId,
  currentPage,
  isCommentChanged,
}: useCommentDataProps) => {
  const [commentData, setCommentData] = useState<CommentData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  useEffect(() => {
    const fetchCommentData = async () => {
      try {
        const response = await axios.get<CommentData>(
          `${GetPublicMusic}/${musicId}/comments`,
          {
            params: {
              page: currentPage,
            },
          }
        );
        setCommentData(response.data || { data: [] });
      } catch (error) {
        console.error("댓글 데이터를 불러오는 중 오류 발생:", error);
      }
    };

    fetchCommentData();
  }, [musicId, currentPage, isCommentChanged]);

  return commentData;
};

export default useCommentData;
