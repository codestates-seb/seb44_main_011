import React from "react";
import SideBar from "../components/SideBar";
import { styled } from "styled-components";
import { MypageInfo } from "../components/MypageInfo";

function Mypage() {
  return (
    <Wrapper>
      <SideBar />
      <MypageInfo />
    </Wrapper>
  );
}

export default Mypage;

const Wrapper = styled.div`
  display: flex;
`
