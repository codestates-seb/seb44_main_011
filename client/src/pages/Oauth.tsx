import { useSearchParams } from "react-router-dom";
function Oauth() {
  const [searchParams] = useSearchParams();
  const access_token = searchParams.get("access_token") || null;
  const refresh_token = searchParams.get("refresh_token") || null;
  const memberId = searchParams.get("memberId") || null;
  const errormessage = searchParams.get("error_message") || null;
  if (errormessage === "[authorization_request_not_found]%20") {
    window.location.replace("/home");
  }
  if (access_token !== null) {
    localStorage.setItem("accessToken", access_token);
  }

  if (refresh_token !== null) {
    localStorage.setItem("refresh", refresh_token);
  }

  if (memberId !== null) {
    localStorage.setItem("memberId", memberId);
  }
  window.location.replace("/home"); //홈화면으로 이동
  return <div></div>;
}

export default Oauth;
