import { useState } from "react";
import Mypage from "./pages/Mypage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Tags from "./pages/Tags";
import Home from "./pages/Home";
import MainContent from "./components/commons/MainContent";
import SideBar from "./components/SideBar";

function App() {
  return (
    <BrowserRouter>
      <MainContent>
        <Routes>
          <Route path="/mypage" element={<Mypage />} />
          <Route path="/tags" element={<Tags />} />
          <Route path="/" element={<Home />} />
        </Routes>
      </MainContent>
    </BrowserRouter>
  );
}

export default App;
