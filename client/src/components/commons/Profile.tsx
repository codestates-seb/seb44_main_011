import { styled } from "styled-components";
import { FC } from "react";

const ProfileContainer = styled.div<{ size: number; radius: number }>`
  display: flex;
  justify-content: center;
  align-items: center;
  align-self: center;
  margin: 0 4px;
  box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  width: ${({ size }) => size}px;
  height: ${({ size }) => size}px;
  border-radius: ${({ radius }) => radius}px;
  aspect-ratio: 1/1;
`;

const Img = styled.img<{ radius: number }>`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: ${({ radius }) => radius}px;
`;

type ProfileProps = {
  size?: number;
  radius?: number;
  image: string;
  alt?: string;
};

const Profile: FC<ProfileProps> = ({
  size = 34,
  radius = 4,
  image,
  alt = "image",
}) => {
  return (
    <ProfileContainer size={size} radius={radius}>
      <Img src={image} radius={radius} alt={alt} />
    </ProfileContainer>
  );
};

export default Profile;
