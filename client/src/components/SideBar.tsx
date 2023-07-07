import styled from "styled-components";
import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import { ReactComponent as DogLogo } from "../../src/assets/imgs/doglogo.svg";

function SideBar() {
  return (
    <RootWrapper>
      <NavLogo>
        <DogLogoImg />
      </NavLogo>
      <Nav>
        <SearchImg fill="#B4B4B7" />
      </Nav>
      <NavHome>
        <HomeImg fill="#84CBFF" />
        <Home_0001 href="/">Home</Home_0001>
      </NavHome>
      <NavMylist>
        <MyList href="/mylist">MyList</MyList>
        <MyListImg fill="#B4B4B7" />
      </NavMylist>
      <NavTags>
        <Tags href="/tags">Tags</Tags>
        <TagImg fill="#B4B4B7" />
      </NavTags>
      <ButtonLogout>
        <Rectangle191 />
        <Logout>LOGOUT</Logout>
      </ButtonLogout>
      <NavMypage>
        <MyPage href="/mypage">MyPage</MyPage>
        <MypageImg fill="#B4B4B7" />
      </NavMypage>
    </RootWrapper>
  );
}

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: relative;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  height: 100vh;
  width: 20%;
  max-width: 300px;
  min-width: 200px;
  flex-direction: column;
  align-items: center;
  position: sticky;
`;

const Nav = styled.div`
  overflow: hidden;
  background-color: white;
  border-radius: 6.25rem;
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: calc(20vw * 15.3125 / 20);
  max-width: 250px;
  height: 40px;
`;

const SearchImg = styled(SearchIcon)`
  width: 1.5rem;
  height: 1.5rem;
  object-fit: cover;
  position: absolute;
  left: 0.625rem;
  top: calc(50% - 0.75rem);
  display: flex;
`;

const NavLogo = styled.div`
  position: absolute;
  left: 4.3125rem;
  top: 3.125rem;
  right: 4.125rem;
  bottom: 85vh;
  display: flex;
`;

// const NaN_0001 = styled.div`
//   position: absolute;
//   left: 1.0625rem;
//   top: 0;
//   right: 1.0625rem;
//   bottom: 4.32vh;

//   display: flex;
// `;

const DogLogoImg = styled(DogLogo)`
  object-fit: cover;
  position: absolute;
  left: -1rem;
  top: -0.125rem;
  right: 0.4375rem;
  bottom: -0.125rem;
  display: flex;
`;

const NavHome = styled.div`
  position: absolute;
  left: 2.5625rem;
  top: 12.25rem;
  right: 7.375rem;
  bottom: 26.52vh;
  display: flex;
`;

const HomeImg = styled(HomeIcon)`
  object-fit: cover;
  position: absolute;
  left: 0;
  top: 0;
  right: 4rem;
  bottom: 0;
  display: flex;
`;

const Home_0001 = styled.a`
  color: rgb(132, 203, 255);
  text-overflow: ellipsis;
  font-size: 1.125rem;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 2.125rem;
  top: 0.0625rem;
  right: 0;
  bottom: 0.1875rem;
  text-decoration: none;
  display: flex;
`;

const NavMylist = styled.div`
  position: absolute;
  left: 2.5625rem;
  top: 17.5rem;
  right: 7.375rem;
  bottom: 63.56vh;
  display: flex;
`;

const MyList = styled.a`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 1.125rem;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 2.125rem;
  top: 0.0625rem;
  right: -0.25rem;
  bottom: 0.1875rem;
  text-decoration: none;
  display: flex;
`;

const MyListImg = styled(MylistIcon)`
  object-fit: cover;
  position: absolute;
  left: 0;
  top: 0;
  right: 4rem;
  bottom: 0;
  display: flex;
`;

const NavTags = styled.div`
  width: 5.9375rem;
  height: 1.375rem;
  overflow: hidden;
  position: absolute;
  left: 2.5625rem;
  top: 14.875rem;
  display: flex;
`;

const Tags = styled.a`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 1.125rem;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 2.125rem;
  top: 0.125rem;
  text-decoration: none;
  display: flex;
`;

const TagImg = styled(TagsIcon)`
  object-fit: cover;
  position: absolute;
  left: 0;
  top: 0;
  right: 4.5625rem;
  bottom: 0;
  display: flex;
`;

const ButtonLogout = styled.div`
  width: 11.5625rem;
  height: 2.5rem;
  position: absolute;
  left: calc((50% + 1px) - 5.8125rem);
  top: 86.93vh;
  display: flex;
`;

const Rectangle191 = styled.div`
  border: solid 1px rgb(209, 209, 209);
  border-radius: 6.25rem;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  display: flex;
`;

const Logout = styled.span`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 0.875rem;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 1.5rem;
  text-align: left;
  min-height: 1.5625rem;
  position: absolute;
  left: 4rem;
  top: 0.4375rem;
  right: 3.9375rem;
  bottom: 0.5rem;
  display: flex;
`;

const NavMypage = styled.div`
  width: 6.625rem;
  height: 1.5rem;
  position: absolute;
  left: 2.5625rem;
  top: 20.125rem;
  display: flex;
`;

const MyPage = styled.a`
  color: rgb(180, 180, 183);
  text-overflow: ellipsis;
  font-size: 1.125rem;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  position: absolute;
  left: 2.25rem;
  top: 0.125rem;
  right: 0;
  bottom: 0.25rem;
  text-decoration: none;
  display: flex;
`;

const MypageImg = styled(MypageIcon)`
  object-fit: cover;
  position: absolute;
  left: 0;
  top: 0;
  right: 5.25rem;
  bottom: 0.125rem;
  display: flex;
`;

export default SideBar;
