import { BrowserRouter, Route, Routes } from "react-router-dom";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import MainContent from "./components/commons/MainContent";
import MyList from "./pages/MyList";
import { styled } from "styled-components";
import Oauth from "./pages/Oauth";
import MyPage from "./pages/MyPage";
import Intro from "./pages/Intro";
import Upload from "./pages/Upload";
import SideNav from "./components/SideNav";
import Search from "./pages/Search";

const MainWrapper = styled.main`
  width: 100%;
  height: 100vh;
  display: flex;
  user-select: none;
  min-width: 500px;
  @media screen and (max-width: 800px) {
    display: flex;
    flex-direction: column;
  }
`;

function App() {
  return (
    <BrowserRouter>
      <MainWrapper>
        <Routes>
          {/* Intro 컴포넌트에서는 SideNav를 렌더링하지 않음 */}
          <Route path="/" element={<Intro />} />
          {/* 다른 페이지들에서는 SideNav를 렌더링 */}
          <Route
            path="/*"
            element={
              <>
                <SideNav />
                <MainContent>
                  <Routes>
                    <Route path="/home" element={<Home />} />
                    <Route path="/mylist" element={<MyList />} />
                    <Route path="/mypage" element={<MyPage />} />
                    <Route path="/mypage/edit" element={<Edit />} />
                    <Route path="/oauth" element={<Oauth />} />
                    <Route path="/mypage/upload" element={<Upload />} />
                    <Route path="/search" element={<Search />} />
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
