import Mypage from "./pages/Mypage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
function App() {
  return (

    <BrowserRouter>
      <Routes>
        <Route path="/mypage" element={<Mypage />} />
      </Routes>
    </BrowserRouter>
>>>>>>>>> Temporary merge branch 2
  );
}

export default App;
