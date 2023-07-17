import { useEffect, useState } from "react";
import axios from "axios";
import { CommentData } from "../types/Comment";

type CommentDataParams = {
  musicId: number;
  currentPage: number;
};

const useCommentData = ({ musicId, currentPage }: CommentDataParams) => {
  const [commentData, setCommentData] = useState<CommentData>({
    data: [],
    pageInfo: { page: 1, size: 6, totalElements: 0, totalPages: 1 },
  });

  useEffect(() => {
    const fetchCommentData = async () => {
      try {
        const response = await axios.get(
          `http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/public/musics/${musicId}/comments`,
          {
            params: {
              page: currentPage,
            },
          }
          // "/data/comment.json"
        );
        setCommentData(response.data);
      } catch (error) {
        console.error("댓글 데이터를 불러오는 중 오류 발생:", error);
      }
    };

    fetchCommentData();
  }, []);

  return commentData;
};

export default useCommentData;
