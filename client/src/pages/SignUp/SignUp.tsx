import React, { useState } from "react";
import Modal from "../../components/layouts/Modal";
import Text from "../../components/commons/H2Text";
import Input from "../../components/commons/Input";
import Bluebutton from "../../components/commons/Bluebutton";
import Share from "../../components/commons/Share";
function SignUp() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setConfirm] = useState("");

  const handleName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };
  const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };
  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };
  const handlePasswordConfirm = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setConfirm(event.target.value);
  };
  return (
    <Modal>
      <Text value="SignUp" />
      <Input name="name" type="text" value={name || ""} onChange={handleName} />
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
      <Input
        name="passwordConfirm"
        type="password"
        value={passwordConfirm || ""}
        onChange={handlePasswordConfirm}
      />
      <Bluebutton value="SignUp" />
      <Share />
    </Modal>
  );
}

export default SignUp;
