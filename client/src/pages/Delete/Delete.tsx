import React, { useState } from "react";
import Modal from "../../components/layouts/Modal";
import Text from "../../components/commons/H2Text";
import Input from "../../components/commons/Input";
import Bluebutton from "../../components/commons/Bluebutton";
import GrayButton from "../../components/commons/GrayButton";
import styled from "styled-components";

const ButtonBox = styled.div`
  display: flex;
  justify-content: space-around;
  width: 400px;
`;

function Delete() {
  const [password, setPassword] = useState("");
  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };
  return (
    <Modal>
      <Text value="회원 탈퇴" />
      <Input
        name="password"
        type="password"
        value={password || ""}
        onChange={handlePassword}
      />
      <div>
        <p>본인 인증을 위해 비밀번호를 입력해주세요</p>
      </div>
      <ButtonBox>
        <GrayButton value="취소" />
        <Bluebutton value="탈퇴" />
      </ButtonBox>
    </Modal>
  );
}

export default Delete;
