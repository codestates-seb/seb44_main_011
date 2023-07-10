import { useState } from "react";
import Mypage from "./pages/Mypage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Tags from "./pages/Tags";

console.log("ff")

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/tags" element={<Tags />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
