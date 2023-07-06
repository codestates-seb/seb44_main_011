import { ReactComponent as Dog } from "./assets/icons/dog.svg";
import Login from "./pages/Login/Login";
import SignUp from "./pages/SignUp/SignUp";
import Delete from "./pages/Delete/Delete";
function App() {
  return (
    <>
      {/* <Login />
      <SignUp />
      <Delete /> */}
      <div>
        <Dog fill="red" />
      </div>
    </>
  );
}

export default App;
