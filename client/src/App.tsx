import { useState } from "react";
import Mypage from "./pages/Mypage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Tags from "./pages/Tags";
import Edit from "./pages/Edit";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/tags" element={<Tags />} />
        <Route path="/mypage/edit" element={<Edit />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
