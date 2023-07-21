import { styled } from "styled-components";
import { useState } from "react";
import Profile from "../components/commons/Profile";
import CustomAudioPlayer from "../components/CustomAudioPlayer";
import { BaseURL } from "../utils/Url";
import { useNavigate } from "react-router";
import axios, { AxiosError } from "axios";
import saveNewToken from "../utils/saveNewToken";

const MusicImg = styled.input`
  display: flex;
  height: 25px;
`;

const MusicUpload = styled.input`
  display: flex;
  height: 25px;
`;

const MusicName = styled.input`
  display: flex;
  height: 25px;
  width: 100%;
`;

const MusicTag = styled.input`
  display: flex;
`;

const MusicTagsContainer = styled.div`
  display: flex;
  flex-direction: row;
  height: 20px;
  align-items: baseline;

  > label {
    padding-left: 4px;
    margin-right: 12px;
  }
`;

const UploadForm = styled.form`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
  margin: 20px;

  > input {
    padding: 0.5rem;
    border: 1px solid var(--bc-medium);
    border-radius: 0.313rem;
    font-size: 14px;
  }
`;

const ButtonContainer = styled.div`
  row-gap: 8px;
  display: flex;
`;

const CancleBtn = styled.button`
  width: 84px;
  height: 30px;
  border: none;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: var(--gray-100);
  cursor: pointer;
  margin-left: 8px;

  &:hover {
    background-color: var(--gray-300);
  }
`;

const UploadBtn = styled.button`
  width: 84px;
  height: 30px;
  border: none;
  border-radius: 7px;
  color: var(--white);
  background-color: var(--primary);
  cursor: pointer;
  margin-left: 8px;

  &:hover {
    background-color: var(--skyblue-300);
  }
`;

const UploadContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 4vh;
  width: 100%;
  max-width: 1800px;
  /* min-width: 700px; */
  padding: 1px 6px;
`;

const InputTitle = styled.label`
  font-family: var(--font-quicksand);
  font-size: 20px;
  font-style: normal;
  font-weight: 500;
  line-height: 100%;
  color: var(--black);
  text-align: center;
  margin-bottom: 12px;
`;

const InputContainer = styled.div`
  display: flex;
  margin: 12px 0px;
  gap: 8px;
  flex-direction: column;
  width: 100%;
  align-items: start;
  border-radius: 10px;
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

const Upload = () => {
  const [image, setImage] = useState<File | null>(null);
  const [musicFile, setMusicFile] = useState<File | null>(null);
  const [name, setName] = useState("");
  const [tag, setTag] = useState("CALM");
  const [category, setCategory] = useState("DOGS");

  const navigate = useNavigate();

  const handleImgChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      setImage(file);
    }
  };

  const handleMusicChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      setMusicFile(file);
    }
  };

  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  const handleTagChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTag(e.target.value);
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategory(e.target.value);
  };

  const handleCancle = () => {
    navigate("/mypage");
  };

  const handleFormSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!musicFile || !image || !name) {
      alert("업로드 폼을 모두 작성해주세요");
      return;
    }

    const musicInfo = {
      title: name,
      category: category,
      tags: tag,
    };

    const formData = new FormData();
    formData.append("mp3", musicFile);
    formData.append("img", image);
    formData.append(
      "musicInfo",
      new Blob([JSON.stringify(musicInfo)], { type: "application/json" })
    );

    try {
      const response = await axios.post(`${BaseURL}/admin/music`, formData);
      const accessToken = response.headers["authorization"] || null;
      saveNewToken(accessToken);
      console.log("업로드 성공:", response.data);
      navigate("/mypage");
    } catch (error) {
      if (
        (error as AxiosError).response &&
        (error as AxiosError).response?.status === 409
      ) {
        alert("이미 업로드된 음악입니다");
      } else {
        alert("업로드에 실패했습니다");
      }
      console.error("업로드 실패:", error);
    }
  };

  return (
    <UploadContainer>
      <Title>Upload</Title>
      <UploadForm>
        <InputContainer>
          <InputTitle id="musicImg"> Music Image</InputTitle>
          <Profile
            image={image ? URL.createObjectURL(image) : ""}
            size={100}
            radius={8}
          />
          <MusicImg
            type="file"
            id="musicImg"
            accept="image/png, image/jpg, image/jpeg"
            onChange={handleImgChange}
          />
        </InputContainer>
        <InputContainer>
          <InputTitle id="musicFile"> Music File</InputTitle>
          <CustomAudioPlayer
            src={musicFile ? URL.createObjectURL(musicFile) : ""}
          />
          <MusicUpload
            type="file"
            id="musicFile"
            accept="audio/mp3"
            onChange={handleMusicChange}
          />
        </InputContainer>
        <InputContainer>
          <InputTitle id="musicname"> Music Name</InputTitle>
          <MusicName
            id="musicname"
            type="text"
            value={name}
            onChange={handleNameChange}
            placeholder="제목을 입력해주세요"
          />
        </InputContainer>
        <InputContainer>
          <InputTitle id="musictags"> Music Category</InputTitle>
          <MusicTagsContainer>
            <MusicTag
              id="DOGS"
              type="radio"
              name="category"
              value="DOGS"
              checked={category === "DOGS"}
              onChange={handleCategoryChange}
            />
            <label htmlFor="DOG">dog</label>
            <MusicTag
              id="CATS"
              type="radio"
              name="category"
              value="CATS"
              checked={category === "CATS"}
              onChange={handleCategoryChange}
            />
            <label htmlFor="CAT">cat</label>
          </MusicTagsContainer>
        </InputContainer>
        <InputContainer>
          <InputTitle id="musictags"> Music Tag</InputTitle>
          <MusicTagsContainer>
            <MusicTag
              id="CALM"
              type="radio"
              name="tags"
              value="CALM"
              checked={tag === "CALM"}
              onChange={handleTagChange}
            />
            <label htmlFor="CALM">calm</label>
            <MusicTag
              id="RELAXING"
              type="radio"
              name="tags"
              value="RELAXING"
              checked={tag === "RELAXING"}
              onChange={handleTagChange}
            />
            <label htmlFor="RELAXING">relaxing</label>
            <MusicTag
              id="UPBEAT"
              type="radio"
              name="tags"
              value="UPBEAT"
              checked={tag === "UPBEAT"}
              onChange={handleTagChange}
            />
            <label htmlFor="UPBEAT">upbeat</label>
            <MusicTag
              id="SERENE"
              type="radio"
              name="tags"
              value="SERENE"
              checked={tag === "SERENE"}
              onChange={handleTagChange}
            />
            <label htmlFor="SERENE">serene</label>
          </MusicTagsContainer>
        </InputContainer>
        <ButtonContainer>
          <CancleBtn type="button" onClick={handleCancle}>
            취소
          </CancleBtn>
          <UploadBtn type="submit" onClick={handleFormSubmit}>
            업로드
          </UploadBtn>
        </ButtonContainer>
      </UploadForm>
    </UploadContainer>
  );
};

export default Upload;
