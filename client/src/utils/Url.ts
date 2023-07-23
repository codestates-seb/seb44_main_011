import axios, { AxiosInstance } from "axios";

export const BaseURL = "https://api.petpil.site:8080";

export const PostLogin = `${BaseURL}/public/login`;
export const PostSignUp = `${BaseURL}/public/signup`;

export const DeleteUser = `${BaseURL}/api/members`;
export const GoogleLogin = `${BaseURL}/oauth2/authorization/google`;
export const NaverLogin = `${BaseURL}/oauth2/authorization/naver`;
export const KakaoLogin = `${BaseURL}/oauth2/authorization/kakao`;

export const GetPublicPlaylist = `${BaseURL}/public/playlist`;

export const GetPublicMusic = `${BaseURL}/public/musics`;

export const api: AxiosInstance = axios.create({
  baseURL: `${BaseURL}/api`,
  timeout: 1000,
  headers: {
    Authorization: localStorage.getItem("accessToken") || "",
    Refresh: localStorage.getItem("refresh") || "",
    "Content-Type": "application/json",
  },
});
