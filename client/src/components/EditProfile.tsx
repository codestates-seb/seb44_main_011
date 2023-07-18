import { useState } from "react";
import { styled } from "styled-components";
import UserInfo from "../assets/imgs/UserInfo.png";
import { useNavigate } from "react-router-dom";
import ImageModal from "./ImageModal";

function EditProfile() {
  const movePage = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState(UserInfo);
  const navigate = useNavigate();

  function goMypage() {
    movePage("/mypage");
  }

  const showModal = () => {
    setModalOpen(true);
  };

  const handleProfileSave = () => {
    // 여기에서 선택된 이미지(selectedImage)를 프로필 이미지로 저장하고 서버에 업데이트하는 로직을 추가할 수 있습니다.
    console.log("프로필 이미지 저장:", selectedImage);
    setModalOpen(false); // 저장 후 모달 닫기
    navigate("/mypage", { state: { selectedImage } });
  };

  return (
    <Wrapper>
      <Title>Edit Profile</Title>
      <UnderLine />
      <Profile>
        <ProfileImage>Profile Image</ProfileImage>
        <UserInfoImg src={selectedImage} />
        <ChangeImg onClick={showModal}>프로필 변경</ChangeImg>
        {modalOpen && (
          <ImageModal
            setModalOpen={setModalOpen}
            onSelectImage={setSelectedImage} // 선택한 이미지를 EditProfile 컴포넌트의 selectedImage로 전달
          />
        )}
        <NickName>Nickname</NickName>
        <NickNameInput onClick={handleProfileSave}></NickNameInput>
      </Profile>
      <ButtonWrapper>
        <Cancle onClick={goMypage}>취소</Cancle>
        <Save onClick={handleProfileSave}>프로필 저장</Save>
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
