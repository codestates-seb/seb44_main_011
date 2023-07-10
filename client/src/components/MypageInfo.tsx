import React from "react";
import { styled } from "styled-components";
import UserInfo from "../assets/imgs/UserInfo.png";

export function MypageInfo() {
  return (
    <Wrapper>
      <Img>
        <UserInfoImg src={UserInfo} />
      </Img>
      <UserName>남포동불주먹</UserName>
      <UserEmail>firerock@naver.com</UserEmail>
      <ButtonWrapper>
        <ProfileBtn>프로필수정</ProfileBtn>
        <DeleteBtn>회원탈퇴</DeleteBtn>
      </ButtonWrapper>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: relative;
`;

const Img = styled.div`
  width: 215px;
  height: 215px;
  position: absolute;
  left: 0px;
  top: 0px;
`;
const UserInfoImg = styled.img`
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
`;

const UserName = styled.span`
  color: black;
  text-overflow: ellipsis;
  font-size: 36px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: relative;
  left: 259px;
  top: 36px;
`;

const UserEmail = styled.span`
  color: rgb(142, 142, 142);
  text-overflow: ellipsis;
  font-size: 24px;
  font-family: Quicksand, sans-serif;
  font-weight: 500;
  line-height: 120%;
  text-align: left;
  position: absolute;
  left: 259px;
  top: 96px;
  right: 0px;
  bottom: 90px;
`;

const ButtonWrapper = styled.div`
  display: flex;
`

const ProfileBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8E8E8E;
  border-radius: 10px;
  color: #8E8E8E;
`;
const DeleteBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8E8E8E;
  border-radius: 10px;
  color: #8E8E8E;
`;
