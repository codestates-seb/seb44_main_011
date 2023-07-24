import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { useNavigate } from "react-router-dom";
import ImageModal from "./ImageModal";
import { api } from "../utils/Url";
import Default from "../assets/imgs/Default.jpg";
import { ErrorMsg } from "./commons/Input";
import saveNewToken from "../utils/saveNewToken";

const defaultImage = { Default };

function EditProfile() {
  const movePage = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState<string>("");
  const [errorMessage, setMessage] = useState<string>("");
  const [nickname, setNickname] = useState("");
  const navigate = useNavigate();
  const memberid = localStorage.getItem("memberId");

  function goMypage() {
    movePage("/mypage");
  }

  const showModal = () => {
    setModalOpen(true);
  };
  const handleProfileSave = async () => {
    if (nickname.length < 2 || nickname.length > 7) {
      alert("2글자 이상 7글자 이하로 입력해주세요.");
      return;
    }
    try {
      const response = await api.patch(`/members/my-page/${memberid}`, {
        name: nickname,
        profileUrl: selectedImage,
      });
      const accessToken = response.headers["authorization"] || null;
      saveNewToken(accessToken);
      setModalOpen(false);
      navigate("/mypage", { state: { selectedImage, nickname } });
    } catch (error) {
      console.error("Error while updating profile:", error);
    }
  };
  useEffect(() => {
    api
      .get(`/members/my-page/${memberid}`)
      .then((response) => {
        const data = response.data;
        const accessToken = response.headers["authorization"] || null;
        saveNewToken(accessToken);
        setSelectedImage(data.profileUrl || defaultImage);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
  const onChangeNickName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    setNickname(inputValue.replace(/\s/g, ""));
    if (nickname.length < 2 || nickname.length > 7) {
      setMessage("2글자 이상 7글자 이하로 입력해주세요.");
    } else {
      setMessage("");
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
        <NickNameInput value={nickname} onChange={onChangeNickName} />
        {errorMessage && <ErrorMsg>{errorMessage}</ErrorMsg>}
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
  font-size: 12px;
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
  margin-bottom: 10px;
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
  font-size: 12px;
`;
const Save = styled.button`
  width: 84px;
  height: 30px;
  border: 1px solid #bbe2ff;
  border-radius: 7px;
  color: #fff;
  background-color: #84cbff;
  cursor: pointer;
  font-size: 12px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
`;
