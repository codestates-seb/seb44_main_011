import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import { ReactComponent as DogLogo } from "../../src/assets/imgs/doglogo.svg";
import { Link, NavLink } from "react-router-dom";

function SideBar() {
  const [isTagsMenuOpen, setIsTagsMenuOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    checkLoginStatus();
  }, []);

  function checkLoginStatus() {
    // 로그인 상태 확인 로직
    setIsLoggedIn(true);
  }


  const handleTagsMenuToggle = () => {
    setIsTagsMenuOpen(isTagsMenuOpen);
  };

  return (
    <RootWrapper>
      <NavLogo>
        <NaN_0001>
          <DogLogoImg />
        </NaN_0001>
      </NavLogo>
      <Nav>
        <InputField type="text" />
        <SearchImg fill="#B4B4B7" />
      </Nav>
      <NavHome>
        <Link className="home" to="/">
          <HomeImg fill="#84CBFF" />
          <Home_0001>Home</Home_0001>
        </Link>
      </NavHome>
      <NavMylist>
        <Link className="mylist" to="/mylist">
          <MyList>MyList</MyList>
          <MyListImg fill="#B4B4B7" />
        </Link>
      </NavMylist>
      <NavTags onClick={handleTagsMenuToggle}>
        <Link className="tags" to="/tags">
          <Tags>Tags</Tags>
          <TagImg fill="#B4B4B7" />
        </Link>
        {isTagsMenuOpen && (
          <DropdownMenu>
            <MenuItem>Tag 1</MenuItem>
            <MenuItem>Tag 2</MenuItem>
            <MenuItem>Tag 3</MenuItem>
            <MenuItem>Tag 4</MenuItem>
          </DropdownMenu>
        )}
      </NavTags>
      <NavMypage>
        <Link className="mypage" to="/mypage">
          <MyPage id="mypageText">MyPage</MyPage>
          <MypageImg id="mypageImg" fill="#B4B4B7" />
        </Link>
      </NavMypage>
      <ButtonWrapper>
        {/* {isLoggedIn ? (
          <>
            <Logout>LOGOUT</Logout>
          </>
        ) : (
          <>
            <Login>LOGIN</Login>
            <Signup>SIGNUP</Signup>
          </>
        )} */}
        <Login>LOGIN</Login>
        <Signup>SIGNUP</Signup>
      </ButtonWrapper>
    </RootWrapper>
  );
}
export default SideBar;

const DropdownMenu = styled.div`
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 8px 0;
  min-width: 150px;
  z-index: 1;
`;

const MenuItem = styled.div`
  padding: 8px 16px;
  color: #333333;
  cursor: pointer;
`;

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: sticky;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  width: 245px;
  min-width: 245px;
  max-width: 350px;
  display: flex;
`;
// const RootWrapper = styled.div`
//   background-color: rgb(240, 243, 243);
//   border: solid 1px rgba(255, 255, 255, 0.16);
//   position: sticky;
//   box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
//   width: 16%;
//   top: 0;
//   flex-shrink: 0;
//   min-width: 200px;
//   max-width: 350px;
// `;

const Nav = styled.div`
  overflow: hidden;
  background-color: white;
  border-radius: 100px;
  position: absolute;
  display: flex;
  left: 28px;
  top: 140px;
  right: 32px;
`;

const InputField = styled.input`
  border: none;
  position: relative;
  left: 0px;
  top: 0px;
  height: 30px;
  width: 139px;
  outline: none;
  border-radius: 20px;
  padding-left: 40px;

  &:focus {
    border: 2px solid #84cbff;
  }
`;

const SearchImg = styled(SearchIcon)`
  width: 24px;
  height: 24px;
  object-fit: cover;
  position: absolute;
  left: 10px;
  top: calc((calc((50% + 0px)) - 12px));
`;

const NavLogo = styled.div`
  position: absolute;
  left: 69px;
  top: 50px;
  right: 66px;
`;

const NaN_0001 = styled.div`
  position: absolute;
  left: 17px;
  top: 0px;
  right: 17px;
`;

const DogLogoImg = styled(DogLogo)`
  object-fit: cover;
  position: absolute;
  left: -16px;
  top: -2px;
  right: 7px;
  bottom: -2px;
`;

const NavHome = styled.div`
  position: absolute;
  left: 41px;
  top: 196px;
  right: 118px;
`;

const HomeImg = styled(HomeIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 64px;

`;

const Home_0001 = styled.span`
  color: #84cbff;
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 34px;
  top: 1px;
  right: 0px;

  text-decoration: none;
`;

const NavMylist = styled.div`
  position: absolute;
  left: 41px;
  top: 280px;
  right: 118px;

`;

const MyList = styled.span`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 34px;
  top: 1px;
  right: -4px;
  text-decoration: none;
`;

const MyListImg = styled(MylistIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 64px;

`;

const NavTags = styled.div`
  width: 95px;
  height: 22px;
  overflow: hidden;
  position: absolute;
  left: 41px;
  top: 238px;
`;

const Tags = styled.span`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 34px;
  top: 2px;
  text-decoration: none;
`;

const TagImg = styled(TagsIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 73px;

`;

const ButtonWrapper = styled.div`
  width: 185px;
  height: 40px;
  position: absolute;
  left: calc((calc((50% + 1px)) - 93px));
  top: 743px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Logout = styled.button`
  border: solid 1px rgb(209, 209, 209);
  border-radius: 100px;
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 14px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 24px;
  min-height: 25px;
  position: absolute;
  cursor: pointer;
  width: 185px;
  height: 40px;
`;
const Login = styled.button`
  border: solid 1px #84CBFF;
  border-radius: 100px;
  color: #84CBFF;
  background-color: #fff;
  text-overflow: ellipsis;
  font-size: 14px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 24px;
  min-height: 25px;
  cursor: pointer;
  width: 88px;
  height: 40px;
  margin-right: 10px;
`;
const Signup = styled.button`
  border: solid 1px #84CBFF;
  border-radius: 100px;
  color: #fff;
  background-color: #84CBFF;
  text-overflow: ellipsis;
  font-size: 14px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 24px;
  min-height: 25px;
  cursor: pointer;
  width: 88px;
  height: 40px;
`;

const NavMypage = styled.div`
  width: 106px;
  height: 24px;
  position: absolute;
  left: 41px;
  top: 322px;
`;

const MyPage = styled.span`
  color: #b4b4b7;
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 36px;
  top: 2px;
  right: 0px;

  text-decoration: none;
`;

const MypageImg = styled(MypageIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 84px;

`;
