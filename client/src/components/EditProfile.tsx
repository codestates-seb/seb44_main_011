import React, { useRef, useState } from "react";
import { styled } from "styled-components";
import UserInfo from "../assets/imgs/UserInfo.png";
import { useNavigate } from "react-router-dom";
import ImageModal from "./ImageModal";

function EditProfile() {
  const movePage = useNavigate();

  function goMypage() {
    movePage("/mypage");
  }

  const [modalOpen, setModalOpen] = useState(false);

  const showModal = () => {
    setModalOpen(true);
  };


  return (
    <Wrapper>
      <Title>Edit Profile</Title>
      <UnderLine />
      <Profile>
        <ProfileImage>Profile Image</ProfileImage>
        <UserInfoImg src={UserInfo} />
        <ChangeImg onClick={showModal}>파일 선택 </ChangeImg>
        {modalOpen && <ImageModal setModalOpen={setModalOpen} />}
        <NickName>Nickname</NickName>
        <NickNameInput></NickNameInput>
      </Profile>
      <ButtonWrapper>
        <Cancle onClick={goMypage}>취소</Cancle>
        <Save>프로필 저장</Save>
      </ButtonWrapper>
    </Wrapper>
  );
}

export default EditProfile;

const Wrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  margin: 50px;
`;

const Profile = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 50px;
`;

const Title = styled.div`
  position: relative;
  display: flex;
  color: black;
  font-weight: 700;
  line-height: 120%;
  text-align: left;
  font-size: 36px;
  font-family: Quicksand, sans-serif;
`;

const UnderLine = styled.div`
  border-top: solid 1px black;
  width: 953px;
`;

const ProfileImage = styled.span`
  text-overflow: ellipsis;
  font-size: 24px;
  font-family: Quicksand, sans-serif;
  font-weight: 500;
  line-height: 100%;
  text-align: left;
  color: black;
  align-items: flex-start;
  display: flex;
  width: 250px;
`;
const UserInfoImg = styled.img`
  width: 270px;
  height: 270px;
`;

const ChangeImg = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
  margin-top: 10px;
`;
const NickName = styled.span`
  text-overflow: ellipsis;
  font-size: 24px;
  font-family: Quicksand, sans-serif;
  font-weight: 500;
  line-height: 100%;
  color: black;
  align-items: flex-start;
  display: flex;
  width: 250px;
  margin-top: 50px;
`;

const NickNameInput = styled.input`
  border: 1px solid black;
  position: relative;
  height: 30px;
  width: 245px;
  outline: none;
  border-radius: 5px;
  margin-top: 10px;
`;

const Cancle = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
  margin-right: 20px;
`;
const Save = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #bbe2ff;
  border-radius: 7px;
  color: #fff;
  background-color: #84cbff;
  cursor: pointer;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
`;
