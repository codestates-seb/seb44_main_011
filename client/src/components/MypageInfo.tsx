import React, { useState } from "react";
import { styled } from "styled-components";
import UserInfo from "../assets/imgs/UserInfo.png";
import { useNavigate } from "react-router-dom";
import DeleteModal from "./DeleteModal";

export function MypageInfo() {
  const navigate = useNavigate();

  const handleBtnEdit = () => {
    navigate("/mypage/edit");
  };

  const [modalOpen, setModalOpen] = useState(false);

  const showModal = () => {
    setModalOpen(true);
  };

  return (
    <Wrapper>
      <UserInfoImg src={UserInfo} />
      <Profile>
        <UserName>남포동불주먹</UserName>
        <UserEmail>firerock@naver.com</UserEmail>
        <ButtonWrapper>
          <ProfileBtn onClick={handleBtnEdit}>프로필수정</ProfileBtn>
          <DeleteBtn onClick={showModal}>회원탈퇴</DeleteBtn>
          {modalOpen && <DeleteModal setModalOpen={setModalOpen} />}
        </ButtonWrapper>
      </Profile>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: relative;
  display: flex;
  margin: 90px 60px;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 40px;
  margin-top: 40px;
`;
const UserInfoImg = styled.img`
  width: 215px;
  height: 215px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
`;

const UserName = styled.span`
  color: black;
  text-overflow: ellipsis;
  font-size: 36px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  margin-bottom: 20px;
`;

const UserEmail = styled.span`
  color: rgb(142, 142, 142);
  text-overflow: ellipsis;
  font-size: 24px;
  font-family: Quicksand, sans-serif;
  font-weight: 500;
  line-height: 120%;
  text-align: left;
  margin-bottom: 20px;
`;

const ButtonWrapper = styled.div`
  display: flex;
`;

const ProfileBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  margin-right: 20px;
  cursor: pointer;
`;
const DeleteBtn = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
`;
