import React from "react";
import styled from "styled-components";
import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import { ReactComponent as DogLogo } from "../../src/assets/imgs/doglogo.svg";
import { Link } from "react-router-dom";

function SideBar() {
  return (
    <RootWrapper>
      <Nav>
        <InputField type="text" />
        <SearchImg fill="#B4B4B7" />
      </Nav>
      <NavLogo>
        <NaN_0001>
          <DogLogoImg />
        </NaN_0001>
      </NavLogo>
      <NavHome>
        <Link className="home" to="/">
          <HomeImg fill="#84CBFF"/>
          <Home_0001>Home</Home_0001>
        </Link>
      </NavHome>
      <NavMylist>
        <Link className="mylist" to="/mylist">
          <MyList>MyList</MyList>
          <MyListImg fill="#B4B4B7" />
        </Link>
      </NavMylist>
      <NavTags>
        <Link className="tags" to="/tags">
          <Tags>Tags</Tags>
          <TagImg fill="#B4B4B7" />
        </Link>
      </NavTags>
      <ButtonLogout>
        <Rectangle191 />
        <Logout>LOGOUT</Logout>
      </ButtonLogout>
      <NavMypage>
        <Link className="mypage" to="/mypage">
          <MyPage id="mypageText">MyPage</MyPage>
          <MypageImg id="mypageImg" fill="#B4B4B7" />
        </Link>
      </NavMypage>
    </RootWrapper>
  );
}
export default SideBar;

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: relative;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  width: 245px;
  height: 832px;
`;

const Nav = styled.div`
  overflow: hidden;
  background-color: white;
  border-radius: 100px;
  position: absolute;
  left: 28px;
  top: 140px;
  right: 32px;
  bottom: 656px;
`;

const InputField = styled.input`
  border: none;
  position: relative;
  left: 35px;
  top: 7px;
  height: 20px;
  width: 132px;
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
  bottom: 712px;
`;

const NaN_0001 = styled.div`
  position: absolute;
  left: 17px;
  top: 0px;
  right: 17px;
  bottom: 36px;
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
  bottom: 614px;
`;

const HomeImg = styled(HomeIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 64px;
  bottom: 0px;
`;

const Home_0001 = styled.a`
  color: rgb(132, 203, 255);
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
  bottom: 3px;
  text-decoration: none;
`;

const NavMylist = styled.div`
  position: absolute;
  left: 41px;
  top: 280px;
  right: 118px;
  bottom: 530px;
`;

const MyList = styled.a`
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
  bottom: 3px;
  text-decoration: none;
`;

const MyListImg = styled(MylistIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 64px;
  bottom: 0px;
`;

const NavTags = styled.div`
  width: 95px;
  height: 22px;
  overflow: hidden;
  position: absolute;
  left: 41px;
  top: 238px;
`;

const Tags = styled.a`
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
  bottom: 0px;
`;

const ButtonLogout = styled.div`
  width: 185px;
  height: 40px;
  position: absolute;
  left: calc((calc((50% + 1px)) - 93px));
  top: 743px;
`;

const Rectangle191 = styled.div`
  border: solid 1px rgb(209, 209, 209);
  border-radius: 100px;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
`;

const Logout = styled.span`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 14px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 24px;
  text-align: left;
  min-height: 25px;
  position: absolute;
  left: 64px;
  top: 7px;
  right: 63px;
  bottom: 8px;
`;

const NavMypage = styled.div`
  width: 106px;
  height: 24px;
  position: absolute;
  left: 41px;
  top: 322px;
`;

const MyPage = styled.a`
  color: rgb(180, 180, 183);
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
  bottom: 4px;
  text-decoration: none;
`;

const MypageImg = styled(MypageIcon)`
  object-fit: cover;
  position: absolute;
  left: 0px;
  top: 0px;
  right: 84px;
  bottom: 2px;
`;

// const RootWrapper = styled.div`
//   background-color: rgb(240, 243, 243);
//   border: solid 0.0625rem rgba(255, 255, 255, 0.16);
//   position: relative;
//   box-shadow: 0rem 0.25rem 0.3125rem 0.125rem rgba(217, 217, 217, 0.5);
//   width: 15.3125rem;
//   height: 52rem;
// `;

// const Nav = styled.div`
//   overflow: hidden;
//   background-color: white;
//   border-radius: 6.25rem;
//   position: absolute;
//   left: 1.75rem;
//   top: 8.75rem;
//   right: 2rem;
//   bottom: 41rem;
// `;

// const SearchImg = styled(SearchIcon)`
//   width: 1.5rem;
//   height: 1.5rem;
//   object-fit: cover;
//   position: absolute;
//   left: 0.625rem;
//   top: calc((50% + 0rem) - 0.75rem);
// `;

// const NavLogo = styled.div`
//   position: absolute;
//   left: 4.3125rem;
//   top: 3.125rem;
//   right: 4.125rem;
//   bottom: 44.5rem;
// `;

// const NaN_0001 = styled.div`
//   position: absolute;
//   left: 1.0625rem;
//   top: 0rem;
//   right: 1.0625rem;
//   bottom: 2.25rem;
// `;

// const DogLogoImg = styled(DogLogo)`
//   object-fit: cover;
//   position: absolute;
//   left: -1rem;
//   top: -0.125rem;
//   right: 0.4375rem;
//   bottom: -0.125rem;
// `;

// const NavHome = styled.div`
//   position: absolute;
//   left: 2.5625rem;
//   top: 10.375rem;
//   right: 7.375rem;
//   bottom: 38.375rem;
// `;

// const HomeImg = styled(HomeIcon)`
//   object-fit: cover;
//   position: absolute;
//   left: 0rem;
//   top: 0rem;
//   right: 4rem;
//   bottom: 0rem;
// `;

// const Home_0001 = styled.a`
//   color: rgb(132, 203, 255);
//   text-overflow: ellipsis;
//   font-size: 1.125rem;
//   font-family: Quicksand, sans-serif;
//   font-weight: 700;
//   line-height: 100%;
//   text-align: left;
//   position: absolute;
//   left: 2.125rem;
//   top: 0.0625rem;
//   right: 0rem;
//   bottom: 0.1875rem;
//   text-decoration: none;
// `;

// const NavMylist = styled.div`
//   position: absolute;
//   left: 2.5625rem;
//   top: 14rem;
//   right: 7.375rem;
//   bottom: 23.125rem;
// `;

// const MyList = styled.a`
//   color: rgb(180, 180, 183);
//   text-overflow: ellipsis;
//   font-size: 1.125rem;
//   font-family: Quicksand, sans-serif;
//   font-weight: 700;
//   line-height: 100%;
//   text-align: left;
//   position: absolute;
//   left: 2.125rem;
//   top: 0.0625rem;
//   right: -0.25rem;
//   bottom: 0.1875rem;
//   text-decoration: none;
// `;

// const MyListImg = styled(MylistIcon)`
//   object-fit: cover;
//   position: absolute;
//   left: 0rem;
//   top: 0rem;
//   right: 4rem;
//   bottom: 0rem;
// `;

// const NavTags = styled.div`
//   width: 5.9375rem;
//   height: 1.375rem;
//   overflow: hidden;
//   position: absolute;
//   left: 2.5625rem;
//   top: 11.875rem;
// `;

// const Tags = styled.a`
//   color: rgb(180, 180, 183);
//   text-overflow: ellipsis;
//   font-size: 1.125rem;
//   font-family: Quicksand, sans-serif;
//   font-weight: 700;
//   line-height: 100%;
//   text-align: left;
//   position: absolute;
//   left: 2.125rem;
//   top: 0.125rem;
//   text-decoration: none;
// `;

// const TagImg = styled(TagsIcon)`
//   object-fit: cover;
//   position: absolute;
//   left: 0rem;
//   top: 0rem;
//   right: 4.5625rem;
//   bottom: 0rem;
// `;

// const ButtonLogout = styled.div`
//   width: 11.5625rem;
//   height: 2.5rem;
//   position: absolute;
//   left: calc((calc((50% + 0.0625rem)) - 5.8125rem));
//   top: 46.4375rem;
// `;

// const Rectangle191 = styled.div`
//   border: solid 0.0625rem rgb(209, 209, 209);
//   border-radius: 6.25rem;
//   position: absolute;
//   left: 0rem;
//   top: 0rem;
//   right: 0rem;
//   bottom: 0rem;
// `;

// const Logout = styled.span`
//   color: rgb(180, 180, 183);
//   text-overflow: ellipsis;
//   font-size: 0.875rem;
//   font-family: Quicksand, sans-serif;
//   font-weight: 700;
//   line-height: 1.5rem;
//   text-align: left;
//   min-height: 1.5625rem;
//   position: absolute;
//   left: 4rem;
//   top: 0.4375rem;
//   right: 3.9375rem;
//   bottom: 0.5rem;
// `;

// const NavMypage = styled.div`
//   width: 6.625rem;
//   height: 1.5rem;
//   position: absolute;
//   left: 2.5625rem;
//   top: 16.125rem;
// `;

// const MyPage = styled.a`
//   color: rgb(180, 180, 183);
//   text-overflow: ellipsis;
//   font-size: 1.125rem;
//   font-family: Quicksand, sans-serif;
//   font-weight: 700;
//   line-height: 100%;
//   text-align: left;
//   position: absolute;
//   left: 2.25rem;
//   top: 0.125rem;
//   right: 0rem;
//   bottom: 0.25rem;
//   text-decoration: none;
// `;

// const MypageImg = styled(MypageIcon)`
//   object-fit: cover;
//   position: absolute;
//   left: 0rem;
//   top: 0rem;
//   right: 5.25rem;
//   bottom: 0.125rem;
// `;
