import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import DogLogo from "../../src/assets/imgs/doglogo.svg";
import CatLogo from "../../src/assets/imgs/catlogo.svg";
import { ReactComponent as More } from "../assets/icons/more.svg";
import { keyframes, styled } from "styled-components";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { SetStateAction, useEffect, useRef, useState } from "react";
import Login from "../pages/Login";
import SignUp from "../pages/SignUp";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/RootStore";
import { setCurrentTag } from "../redux/tagSlice";
interface NavItemProps {
  isActive: boolean;
}
interface ViewMoreProps {
  rotated: boolean;
}

function SideNav() {
  const [isTagsMenuOpen, setIsTagsMenuOpen] = useState(false);
  const location = useLocation();
  //path parameter를 통해서 최신 메뉴 확인, /부분은 제거
  const [currentMenu, setCurrentMenu] = useState<string>(
    location.pathname.substring(1)
  );
  const [modalOpen, setModalOpen] = useState(false);
  const [Signmodal, setSignModal] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const modalRef = useRef(null);
  const isLogin = localStorage.getItem("memberId");

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isDogpli = useSelector((state: RootState) => state.home.isDogpli);
  const currentTag = useSelector((state: RootState) => state.tags.currentTag);
  const handleClickMenu = (e: {
    currentTarget: { id: SetStateAction<string> };
  }) => {
    setCurrentMenu(e.currentTarget.id);
  };

  const handleTagsMenuClick = () => {
    setIsTagsMenuOpen((prev) => !prev);
    dispatch(setCurrentTag(""));
  };

  const closeDropdownMenu = () => {
    setIsTagsMenuOpen(false);
  };

  useEffect(() => {
    closeDropdownMenu();
  }, [location.pathname]);

  const showLoginModal = () => {
    setModalOpen(!modalOpen);
  };
  const showSignModal = () => {
    setSignModal(!Signmodal);
  };
  const logout = () => {
    localStorage.removeItem("memberId");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refresh");
    localStorage.removeItem("role");
    window.location.replace("/home");
  };
  const modalSideClick = (e: React.MouseEvent | React.TouchEvent) => {
    if (modalRef.current === e.target) {
      setModalOpen(!modalOpen);
    }
  };
  const modalSideClick2 = (e: React.MouseEvent | React.TouchEvent) => {
    if (modalRef.current === e.target) {
      setSignModal(!Signmodal);
    }
  };

  const Navigate = (data: string, e: React.MouseEvent) => {
    const memberId = localStorage.getItem("memberId");
    if (memberId !== null) {
      navigate(`/${data}`);
      setCurrentMenu(e.currentTarget.id);
    } else alert("로그인이 필요한 페이지입니다.");
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchEnter = async (
    e: React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (e.key === "Enter") {
      e.preventDefault();
      if (searchQuery.trim() !== "") {
        try {
          navigate(`/search?title=${encodeURIComponent(searchQuery)}`, {
            state: { searchQuery },
          });
        } catch (error) {
          console.error("Error while searching:", error);
        }
      }
    }
  };

  const handleTagClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    const id = (event.target as HTMLElement).id;
    dispatch(setCurrentTag(id));
  };

  return (
    <>
      <RootWrapper>
        <NavWrapper>
          <UlWrapper>
            <LogoLink className="logo" to="/home">
              <LogoImg
                src={isDogpli === "dog" ? DogLogo : CatLogo}
                alt="Logo"
              />
            </LogoLink>
            <SearchField>
              <SearchImg fill="#B4B4B7" />
              <InputField
                type="text"
                value={searchQuery}
                onChange={handleInputChange}
                onKeyDown={handleSearchEnter}
              ></InputField>
            </SearchField>
            <LiStyle>
              <HomeWrapper onClick={handleTagsMenuClick}>
                <NavHome
                  id="home"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "/home"}
                >
                  <HomeLink className="home" to="home">
                    <HomeImg
                      fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"}
                    />
                    <HomeText isActive={currentMenu === "home"}>
                      {"Home"}
                    </HomeText>
                    <ViewMore
                      fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"}
                      rotated={isTagsMenuOpen}
                    />
                  </HomeLink>
                </NavHome>
                <DropdownMenu hidden={!isTagsMenuOpen}>
                  <MenuItem
                    id="calm"
                    onClick={handleTagClick}
                    style={{
                      color: currentTag === "calm" ? "var(--black)" : "inherit",
                    }}
                  >
                    CALM
                  </MenuItem>
                  <MenuItem
                    id="relaxing"
                    onClick={handleTagClick}
                    style={{
                      color:
                        currentTag === "relaxing" ? "var(--black)" : "inherit",
                    }}
                  >
                    RELAXING
                  </MenuItem>
                  <MenuItem
                    id="upbeat"
                    onClick={handleTagClick}
                    style={{
                      color:
                        currentTag === "upbeat" ? "var(--black)" : "inherit",
                    }}
                  >
                    UPBEAT
                  </MenuItem>
                  <MenuItem
                    id="serene"
                    onClick={handleTagClick}
                    style={{
                      color:
                        currentTag === "serene" ? "var(--black)" : "inherit",
                    }}
                  >
                    SERENE
                  </MenuItem>
                </DropdownMenu>
              </HomeWrapper>
              <HomeWrapper2>
                <NavHome
                  id="home"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "home"}
                >
                  <HomeLink className="home" to="/home">
                    <HomeImg
                      fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"}
                    />
                  </HomeLink>
                </NavHome>
              </HomeWrapper2>
              <LiWrapper>
                <NavMylist
                  id="mylist"
                  isActive={currentMenu === "mylist"}
                  onClick={(e: React.MouseEvent) => Navigate("mylist", e)}
                >
                  <SideDiv className="mylist">
                    <MyListImg
                      fill={currentMenu === "mylist" ? "#84CBFF" : "#B4B4B7"}
                    />
                    <MylistText isActive={currentMenu === "mylist"}>
                      {"MyList"}
                    </MylistText>
                  </SideDiv>
                </NavMylist>
              </LiWrapper>
              <LiWrapper>
                <NavMyPage
                  id="mypage"
                  isActive={currentMenu === "mypage"}
                  onClick={(e: React.MouseEvent) => Navigate("mypage", e)}
                >
                  <SideDiv className="mypage">
                    <MypageImg
                      fill={currentMenu === "mypage" ? "#84CBFF" : "#B4B4B7"}
                    />
                    <MypageText isActive={currentMenu === "mypage"}>
                      {"MyPage"}
                    </MypageText>
                  </SideDiv>
                </NavMyPage>
              </LiWrapper>
            </LiStyle>
            <SearchField2>
              <SearchImg fill="#B4B4B7" />
              <InputField
                type="text"
                value={searchQuery}
                onChange={handleInputChange}
                onKeyDown={handleSearchEnter}
              ></InputField>
            </SearchField2>
          </UlWrapper>
          <ButtonWrapper>
            {isLogin ? (
              <>
                <Logout onClick={() => logout()}>LOGOUT</Logout>
              </>
            ) : (
              <>
                <Login1 onClick={() => showLoginModal()}>LOGIN</Login1>
                <Signup onClick={() => showSignModal()}>SIGNUP</Signup>
              </>
            )}
          </ButtonWrapper>
        </NavWrapper>
      </RootWrapper>
      {modalOpen && (
        <ModalBackground ref={modalRef} onClick={modalSideClick}>
          <Login />
        </ModalBackground>
      )}
      {Signmodal && (
        <ModalBackground ref={modalRef} onClick={modalSideClick2}>
          <SignUp
            ref={modalRef}
            onClick2={modalSideClick2}
            onClick={modalSideClick}
          />
        </ModalBackground>
      )}
    </>
  );
}

export default SideNav;

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: fixed;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  width: 260px;
  height: -webkit-fill-available;
  display: flex;
  justify-content: center;
  padding: 4vh 0;
  font-family: var(--font-quicksand);
  min-height: fit-content;

  @media screen and (max-width: 800px) {
    width: 100%;
    height: 40px;
    position: relative;
    padding: 2vh 0;
    align-items: center;
  }
`;
const NavWrapper = styled.nav`
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: space-between;
  @media screen and (max-width: 800px) {
    display: flex;
    flex-direction: row;
    width: 100%;
    justify-content: normal;
  }
`;
const UlWrapper = styled.ul`
  list-style: none;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  row-gap: 24px;
  @media screen and (max-width: 800px) {
    display: flex;
    flex-direction: row;
    width: 100%;
  }
`;

const LogoLink = styled(Link)`
  display: flex;
  justify-content: center;
  height: 80px;
  align-items: baseline;
  @media screen and (max-width: 800px) {
    display: none;
  }
`;

const LogoImg = styled.img`
  align-self: center;
  width: 135px;
`;

const SearchField = styled.div`
  background-color: white;
  border-radius: 100px;
  display: flex;
  align-items: center;
  @media screen and (max-width: 800px) {
    display: none;
  }
`;
const SearchField2 = styled.div`
  background-color: white;
  border-radius: 100px;
  display: flex;
  align-items: center;
  width: 100%;
  @media screen and (min-width: 800px) {
    display: none;
  }
`;
const InputField = styled.input`
  border: none;
  height: 30px;
  outline: none;
  border-radius: 20px;
  padding-left: 40px;
  &:focus {
    border: 2px solid #84cbff;
  }
  @media screen and (max-width: 800px) {
    width: 100%;
    height: 40px;
    border-radius: 100px;
  }
`;
const SearchImg = styled(SearchIcon)`
  width: 24px;
  height: 24px;
  position: absolute;
  margin-left: 10px;
`;
const LiStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 12px;
  gap: 24px;
  @media screen and (max-width: 800px) {
    flex-direction: row;
    align-items: center;
    margin-right: 12px;
    gap: 10px;
  }
`;
const LiWrapper = styled.li`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  a {
    text-decoration: none;
  }
`;

const HomeWrapper = styled.li`
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  a {
    text-decoration: none;
  }
  @media screen and (max-width: 800px) {
    display: none;
  }
`;
const HomeWrapper2 = styled.li`
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  a {
    text-decoration: none;
  }
  @media screen and (max-width: 800px) {
    width: 32px;
  }
  @media screen and (min-width: 800px) {
    display: none;
  }
`;

const HomeLink = styled(Link)`
  display: flex;
  align-items: center;
  gap: 8px;

  &:hover {
    > span {
      color: var(--gray-500);
    }
    > svg {
      fill: var(--gray-500);
    }
  }
`;

const ViewMore = styled(More)<ViewMoreProps>`
  height: 18px;
  width: 18px;
  transform: ${(props) => (props.rotated ? "rotate(180deg)" : "rotate(0)")};
  transition: transform 0.2s ease;
`;
const HomeImg = styled(HomeIcon)``;
const HomeText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;

const MyListImg = styled(MylistIcon)`
  margin-right: 10px;
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
  @media screen and (max-width: 800px) {
    display: none;
  }
`;
const MypageImg = styled(MypageIcon)`
  margin-right: 10px;
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
  @media screen and (max-width: 800px) {
    display: none;
  }
`;

const NavHome = styled.div<NavItemProps>``;
const NavMylist = styled.div<NavItemProps>``;
const NavMyPage = styled.div<NavItemProps>``;

const DropdownMenu = styled.div`
  width: fit-content;
  border-radius: 4px;
  // box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  display: ${(props) =>
    props.hidden ? "none" : "flex"}; // hidden 속성으로 메뉴 숨기기/보이기
  flex-direction: column;
  justify-content: start;
  align-items: start;
  row-gap: 16px;
  padding: 16px;
  font-size: 14px;
  font-weight: 700;
  color: #c8c7c7;
`;

const MenuItem = styled.div`
  padding: 0px 16px;
  cursor: pointer;

  &:hover {
    color: var(--gray-400);
  }

  &:active {
    color: var(--black);
  }
`;

const SideDiv = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;

  &:hover {
    > span {
      color: var(--gray-500);
    }
    > svg {
      fill: var(--gray-500);
    }
  }
`;

const ModalBackground = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  z-index: 999;
  position: fixed;
  backdrop-filter: blur(10px);
  animation: ${fadeInAnimation} 0.5s ease-in;
`;
const ButtonWrapper = styled.div`
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  @media screen and (max-width: 800px) {
    width: 120px;
    height: 100%;
    align-items: center;
    justify-content: center;
    margin-left: 15px;
    margin-right: 15px;
  }
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
  @media screen and (max-width: 800px) {
    width: 80px;
  }
`;
const Login1 = styled.button`
  border: solid 1px #84cbff;
  border-radius: 100px;
  color: #84cbff;
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
  @media screen and (max-width: 800px) {
    width: 55px;
    font-size: 13px;
  }
`;
const Signup = styled.button`
  border: solid 1px #84cbff;
  border-radius: 100px;
  color: #fff;
  background-color: #84cbff;
  text-overflow: ellipsis;
  font-size: 14px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 24px;
  min-height: 25px;
  cursor: pointer;
  width: 88px;
  height: 40px;
  @media screen and (max-width: 800px) {
    width: 55px;
    font-size: 13px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;
