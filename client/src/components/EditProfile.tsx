import { useState } from "react";
import { styled } from "styled-components";
import { useNavigate } from "react-router-dom";
import ImageModal from "./ImageModal";
import { api } from "../utils/Url";

function EditProfile() {
  const movePage = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState<string>("");

  const [nickname, setNickname] = useState("");
  const navigate = useNavigate();

  function goMypage() {
    movePage("/mypage");
  }

  const showModal = () => {
    setModalOpen(true);
  };

  const handleProfileSave = async () => {
    const memberid = localStorage.getItem("memberId");
    console.log("프로필 이미지 저장:", selectedImage);
    console.log("닉네임 저장:", nickname);

    try {
      await api.patch(`/members/my-page/${memberid}`, {
        name: nickname,
        profileUrl: "url-1",
      });
      console.log("성공!");
      setModalOpen(false);
      navigate("/mypage", { state: { selectedImage, nickname } });
    } catch (error) {
      console.error("Error while updating profile:", error);
    }
  };

  return (
    <Wrapper>
      <Title>Edit Profile</Title>
      <Profile>
        <ProfileImage>Profile Image</ProfileImage>
        {modalOpen && (
          <ImageModal
            setModalOpen={setModalOpen}
            onSelectImage={setSelectedImage} // 선택한 이미지를 EditProfile 컴포넌트의 selectedImage로 전달
          />
        )}
        <UserInfoImg src={selectedImage} />
        <ChangeImg onClick={showModal}>프로필 변경</ChangeImg>
        <NickName>Nickname</NickName>
        <NickNameInput
          value={nickname}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setNickname(e.target.value)
          }
        ></NickNameInput>
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
  display: flex;
  flex-direction: column;
  margin: 50px;
  // border: 1px solid black;
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
  width: 100%;
  padding-bottom: 12px;
  border-bottom: solid 1px var(--black);
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
  border-radius: 15px;
  margin-top: 10px;
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
