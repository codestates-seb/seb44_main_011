import { BrowserRouter, Route, Routes } from "react-router-dom";
import Tags from "./pages/Tags";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import MainContent from "./components/commons/MainContent";
import SideBar from "./components/SideBar";
import MyList from "./pages/MyList";
import { styled } from "styled-components";
import Oauth from "./pages/Oauth";
import MyPage from "./pages/MyPage";
import Intro from "./pages/Intro";
import { useState, useEffect } from "react";

const MainWrapper = styled.main`
  width: 100%;
  height: 100vh;
  display: flex;
`;

function App() {
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    const memberId = localStorage.getItem("memberId");
    if (memberId) {
      setIsLogin(true);
    } else setIsLogin(false);
  }, []);

  return (
    <BrowserRouter>
      <MainWrapper>
        <Routes>
          {/* Intro 컴포넌트에서는 SideBar를 렌더링하지 않음 */}
          <Route path="/" element={<Intro />} />
          {/* 다른 페이지들에서는 SideBar를 렌더링 */}
          <Route
            path="/*"
            element={
              <>
                <SideBar />
                <MainContent>
                  <Routes>
                    <Route path="/home" element={<Home />} />
                    <Route path="/mylist" element={<MyList />} />
                    <Route path="/mypage" element={<MyPage />} />
                    <Route path="/tags" element={<Tags />} />
                    <Route path="/mypage/edit" element={<Edit />} />
                    <Route path="/oauth" element={<Oauth />} />
                  </Routes>
                </MainContent>
              </>
            }
          />
        </Routes>
      </MainWrapper>
    </BrowserRouter>
  );
}

export default App;
