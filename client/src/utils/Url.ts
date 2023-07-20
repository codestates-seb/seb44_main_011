import axios, { AxiosInstance } from "axios";

export const BaseURL =
  "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080";
export const PostLogin =
  "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/public/login";
export const PostSignUp =
  "http://ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com:8080/public/signup";
export const DeleteUser = `${BaseURL}/api/members`;

export const Naver_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${
  import.meta.env.VITE_Naver_Client_Id
}&state=false&redirect_uri=http://localhost:5173`;

export const Naver_Redirect_URL = "http://localhost:5173";

export const GetPublicPlaylist = `${BaseURL}/public/playlist`;

export const GetPublicMusic = `${BaseURL}/public/musics`;

export const api: AxiosInstance = axios.create({
  baseURL: `${BaseURL}/api`,
  timeout: 1000,
  headers: {
    Authorization: localStorage.getItem("accessToken") || "",
    "x-refresh-token": localStorage.getItem("refresh") || "",
    "Content-Type": "application/json",
  },
});

export const Google_URL = `${BaseURL}/login/oauth2/code/google`;

