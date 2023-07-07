import React, { useState } from "react";
import Modal from "../../components/layouts/Modal";
import Text from "../../components/commons/H2Text";
import Input from "../../components/commons/Input";
import Bluebutton from "../../components/commons/Bluebutton";
import Share from "../../components/commons/Share";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  //const { register, handleSubmit } = useForm();

  const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };
  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };
  return (
    <form>
      <Modal>
        <Text value="Login" />
        <Input
          name="email"
          type="text"
          value={email || ""}
          onChange={handleEmail}
        />
        <Input
          name="password"
          type="password"
          value={password || ""}
          onChange={handlePassword}
        />
        <Bluebutton value="Login" />
        <Share />
      </Modal>
    </form>
  );
}

export default Login;
