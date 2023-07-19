import { useSearchParams } from "react-router-dom";
function Oauth() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("access_token"); // test
  const array = token ? token.split("refresh_token=") : [];
  localStorage.setItem("accessToken", array[0] ?? "");
  localStorage.setItem("refresh", array[1] ?? "");
  window.location.replace("/"); //홈화면으로 이동
  return <div></div>;
}

export default Oauth;
