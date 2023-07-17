import { keyframes, styled } from "styled-components";
// import UserInfo from "../assets/imgs/UserInfo.png";
import Sample1 from "../assets/imgs/sample1.jpeg";
import Sample2 from "../assets/imgs/sample2.jpeg";
import Sample3 from "../assets/imgs/sample3.jpeg";
import Sample4 from "../assets/imgs/sample4.jpeg";
import Sample5 from "../assets/imgs/sample5.jpeg";

type PropsType = {
  setModalOpen: (open: boolean) => void;
  onSelectImage: (selectedImg: string) => void; // 추가: 선택한 이미지를 전달하기 위한 콜백 함수
};

function ImageModal({ setModalOpen, onSelectImage }: PropsType) {
  const closeModal = () => {
    setModalOpen(false);
  };

  const handleImageSelect = (selectedImage: string) => {
    onSelectImage(selectedImage); // 선택한 이미지를 EditProfile 컴포넌트의 selectedImage로 전달
    closeModal();
  };

  return (
    <FadeIn>
      <Container>
        <Exit onClick={closeModal}>X</Exit>
        <ContentWrapper>
          {/* <DefaultImg>
            onClick={() => handleImageSelect(UserInfo)}
            src={UserInfo}
          </DefaultImg> */}
          <SampelImg1
            onClick={() => handleImageSelect(Sample1)}
            src={Sample1}
          />
          <SampelImg2
            onClick={() => handleImageSelect(Sample2)}
            src={Sample2}
          />
        </ContentWrapper>
        <ContentWrapper>
          <SampelImg3
            onClick={() => handleImageSelect(Sample3)}
            src={Sample3}
          />
          <SampelImg4
            onClick={() => handleImageSelect(Sample4)}
            src={Sample4}
          />
          <SampelImg5
            onClick={() => handleImageSelect(Sample5)}
            src={Sample5}
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
`;

const Container = styled.div`
  width: 700px;
  height: 700px;
  z-index: 999;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #f0f3f3;
  border: 1px solid black;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  animation-name: fadeIn;
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
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
`;
// const DefaultImg = styled.img`
//   width: 200px;
//   height: 200px;
//   box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
//   background-repeat: no-repeat;
//   background-size: cover;
//   background-position: center;
//   border-radius: 15px;
// `;
const SampelImg1 = styled.img`
  width: 200px;
  height: 200px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
`;
const SampelImg2 = styled.img`
  width: 200px;
  height: 200px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  margin-left: 10px;
`;
const SampelImg3 = styled.img`
  width: 200px;
  height: 200px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
`;
const SampelImg4 = styled.img`
  width: 200px;
  height: 200px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  border-radius: 15px;
  margin-left: 10px;
`;
const SampelImg5 = styled.img`
  width: 200px;
  height: 200px;
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
