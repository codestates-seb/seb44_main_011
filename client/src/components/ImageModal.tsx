import { keyframes, styled } from "styled-components";
import { api } from "../utils/Url";
import { useEffect, useState } from "react";
import saveNewToken from "../utils/saveNewToken";

type PropsType = {
  setModalOpen: (open: boolean) => void;
  onSelectImage: (selectedImg: string) => void;
};

function ImageModal({ setModalOpen, onSelectImage }: PropsType) {
  const closeModal = () => {
    setModalOpen(false);
  };

  const [imageData, setImageData] = useState([]);

  const getImageData = async () => {
    try {
      const response = await api.get("/members/my-page/profiles");
      const data = response.data;
      const accessToken = response.headers["authorization"] || null;
      saveNewToken(accessToken);
      setImageData(data);
    } catch (error) {
      console.error("이미지 데이터를 가져오는데 에러가 발생했습니다:", error);
    }
  };

  useEffect(() => {
    getImageData();
  }, []);

  const handleImageSelect = (selectedImage: string) => {
    onSelectImage(selectedImage);
    closeModal();
  };

  return (
    <FadeIn>
      <Container>
        <Exit onClick={closeModal}>X</Exit>
        <Title>Choose your Image</Title>
        <ContentWrapper>
          <SampelImg1
            onClick={() => handleImageSelect(imageData[0])}
            src={imageData[0]}
          />
          <SampelImg2
            onClick={() => handleImageSelect(imageData[1])}
            src={imageData[1]}
          />
          <SampelImg5
            onClick={() => handleImageSelect(imageData[2])}
            src={imageData[2]}
          />
          <SampelImg5
            onClick={() => handleImageSelect(imageData[3])}
            src={imageData[3]}
          />
        </ContentWrapper>
        <ContentWrapper>
          <SampelImg3
            onClick={() => handleImageSelect(imageData[4])}
            src={imageData[4]}
          />
          <SampelImg4
            onClick={() => handleImageSelect(imageData[5])}
            src={imageData[5]}
          />
          <SampelImg5
            onClick={() => handleImageSelect(imageData[6])}
            src={imageData[6]}
          />
          <SampelImg5
            onClick={() => handleImageSelect(imageData[7])}
            src={imageData[7]}
          />
        </ContentWrapper>
        <ButtonWrapper>
          <Confirm onClick={closeModal}>확인</Confirm>
        </ButtonWrapper>
      </Container>
    </FadeIn>
  );
}

export default ImageModal;

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const FadeIn = styled.div`
  animation: ${fadeInAnimation} 0.5s ease-in;
  display: flex;
  justify-content: center;
`;

const Container = styled.div`
  position: absolute;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 550px;
  height: 500px;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 1px 1px 15px rgba(0, 0, 0, 0.2);
  font-family: var(--font-gaegu);
  border: 1px solid black;
`;

const Exit = styled.button`
  position: absolute;
  right: 10px;
  top: 10px;
`;
const ContentWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
`;
const Title = styled.p`
  color: var(--black, #212121);
  text-align: center;
  font-family: Gaegu;
  font-size: 30px;
  font-style: normal;
  font-weight: 400;
  line-height: 100%;
  margin-bottom: 12px;
`;
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
`;

const SampelImg1 = styled.img`
  width: 100px;
  height: 100px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
`;
const SampelImg2 = styled.img`
  width: 100px;
  height: 100px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  margin-left: 10px;
`;
const SampelImg3 = styled.img`
  width: 100px;
  height: 100px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
`;
const SampelImg4 = styled.img`
  width: 100px;
  height: 100px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  margin-left: 10px;
`;
const SampelImg5 = styled.img`
  width: 100px;
  height: 100px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  margin-left: 10px;
`;
const Confirm = styled.button`
  margin-left: 10px;
  width: 50px;
  height: 30px;
  border: 1px solid #8e8e8e;
  border-radius: 7px;
  color: #8e8e8e;
  background-color: #fff;
  cursor: pointer;
`;
