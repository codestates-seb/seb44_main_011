import { BrowserRouter, Route, Routes } from "react-router-dom";
import Tags from "./pages/Tags";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import MainContent from "./components/commons/MainContent";
import SideBar from "./components/SideBar";
import MyList from "./pages/MyList";
import { styled } from "styled-components";
import { MypageInfo } from "./components/MypageInfo";
import { useState } from "react";
import MyPage from "./pages/MyPage";
import Oauth from "./pages/Oauth";
import Search from "./pages/Search";


const MainWrapper = styled.main`
  width: 100%;
  height: 100vh;
  display: flex;
`;

function App() {
  return (
    <BrowserRouter>
      <MainWrapper>
        <SideBar />
        <MainContent>
          <Routes>
            <Route path="/mylist" element={<MyList />} />
            <Route path="/mypage" element={<Mypage />} />
            <Route path="/tags" element={<Tags />} />
            <Route path="/" element={<Home />} />
            <Route path="/mypage/edit" element={<Edit />} />
            <Route path="/oauth" element={<Oauth />} />
            <Route path="/search" element={<Search />} />
          </Routes>
        </MainContent>
      </MainWrapper>
    </BrowserRouter>
  );
}

export default App;
