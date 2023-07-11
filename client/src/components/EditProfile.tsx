import React from "react";
import { styled } from "styled-components";
import UserInfo from "../assets/imgs/UserInfo.png";

function EditProfile() {
  return (
    <Wrapper>
      <Title>Edit Profile</Title>
      <UnderLine />
      <Profile>
        <ProfileImage>Profile Image</ProfileImage>
        <UserInfoImg src={UserInfo} />
      </Profile>
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
`;
const UserInfoImg = styled.img`
  width: 270px;
  height: 270px;
  left: 0px;
  top: 36px;ß
`;
