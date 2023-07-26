import { PageInfo } from "./PageInfo";

export type Comment = {
  commentId: number;
  musicId: number;
  memberId: number;
  name: string;
  comment: string;
  createdAt: string;
  modifiedAt: string;
  profile: string;
};

export type CommentData = {
  data: Comment[];
  pageInfo: PageInfo;
};
