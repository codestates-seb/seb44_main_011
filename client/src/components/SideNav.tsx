import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import DogLogo from "../../src/assets/imgs/doglogo.png";
import { ReactComponent as More } from "../assets/icons/more.svg";
import { styled } from "styled-components";
import { Link } from "react-router-dom";
import { useState } from "react";

interface NavItemProps {
  isActive: boolean;
}

function SideNav() {
  const [currentMenu, setCurrentMenu] = useState<string>("hello");
  const handleClickMenu = (e: any) => {
    setCurrentMenu(e.currentTarget.id);
  };

  return (
    <>
      <RootWrapper>
        <NavWrapper>
          <UlWrapper>
            <Link className="logo" to="/">
              <DogLogoImg src={DogLogo} />
            </Link>
            <LiWrapper>
              <SearchIcon fill="#B4B4B7" />
              <input type="text"></input>
            </LiWrapper>
            <LiWrapper>
              <NavHome
                id="home"
                onClick={handleClickMenu}
                isActive={currentMenu === "home"}
              >
                <Link className="home" to="/">
                  <HomeIcon
                    fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"}
                  />
                  <HomeText isActive={currentMenu === "home"}>
                    {"Home"}
                  </HomeText>
                </Link>
              </NavHome>
            </LiWrapper>
            <LiWrapper>
              <NavTags
                id="tags"
                onClick={handleClickMenu}
                isActive={currentMenu === "tags"}
              >
                <TagsIcon
                  fill={currentMenu === "tags" ? "#84CBFF" : "#B4B4B7"}
                />
                <TagText isActive={currentMenu === "tags"}>{"Tags"}</TagText>
                <ViewMore />
              </NavTags>
            </LiWrapper>
            <LiWrapper>
              <NavMylist
                id="mylist"
                onClick={handleClickMenu}
                isActive={currentMenu === "mylist"}
              >
                <Link className="mylist" to="/mylist">
                  <MylistIcon
                    fill={currentMenu === "mylist" ? "#84CBFF" : "#B4B4B7"}
                  />
                  <MylistText isActive={currentMenu === "mylist"}>
                    {"MyList"}
                  </MylistText>
                </Link>
              </NavMylist>
            </LiWrapper>
            <LiWrapper>
              <NavMyPage
                id="mypage"
                onClick={handleClickMenu}
                isActive={currentMenu === "mypage"}
              >
                <Link className="mypage" to="/mypage">
                  <MypageIcon
                    fill={currentMenu === "mypage" ? "#84CBFF" : "#B4B4B7"}
                  />
                  <MypageText isActive={currentMenu === "mypage"}>
                    {"MyPage"}
                  </MypageText>
                </Link>
              </NavMyPage>
            </LiWrapper>
          </UlWrapper>
        </NavWrapper>
      </RootWrapper>
    </>
  );
}

export default SideNav;

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: sticky;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  width: 245px;
  min-width: 245px;
  max-width: 350px;
  display: flex;
  justify-content: center;
`;
const NavWrapper = styled.nav`
  display: flex;
`;
const UlWrapper = styled.ul`
  list-style: none;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const DogLogoImg = styled.img`
  object-fit: cover;
`;
const LiWrapper = styled.li`
  display: flex;
  justify-content: center;
  align-items: center;
  a {
    text-decoration: none;
  }
`;
const ViewMore = styled(More)`
  width: 30px;
  height: 30px;
  color: #b4b4b7;
`;
const HomeText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;
const TagText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;
const MylistText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;
const MypageText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-align: left;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;

const NavHome = styled.div<NavItemProps>``;
const NavMylist = styled.div<NavItemProps>``;
const NavMyPage = styled.div<NavItemProps>``;
const NavTags = styled.div<NavItemProps>``;
