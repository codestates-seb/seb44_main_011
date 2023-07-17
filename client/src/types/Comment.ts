import { PageInfo } from "./PageInfo";

export type Comment = {
  commentId: number;
  musicId: number;
  name: string;
  comment: string;
  createdAt: string;
  modifiedAt: string;
};

export type CommentData = {
  data: Comment[];
  pageInfo: PageInfo;
};
