import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import DogLogo from "../../src/assets/imgs/doglogo.png";
import CatLogo from "../../src/assets/imgs/catlogo.png";
import { ReactComponent as More } from "../assets/icons/more.svg";
import { keyframes, styled } from "styled-components";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
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
  const [isTagsMenuOpen, setIsTagsMenuOpen] = useState(false); // 드롭다운 메뉴가 열려있는지 여부를 저장하는 상태 변수
  const [currentMenu, setCurrentMenu] = useState<string>("hello");
  const [modalOpen, setModalOpen] = useState(false);
  const [Signmodal, setSignModal] = useState(false);
  //검색기능
  const [searchQuery, setSearchQuery] = useState("");
  // 검색 결과 상태
  const [searchResults, setSearchResults] = useState([]);
  const modalRef = useRef(null);
  const isLogin = localStorage.getItem("memberId");
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isDogpli = useSelector((state: RootState) => state.home.isDogpli);
  // const currentTag = useSelector((state: RootState) => state.tags.currentTag); 클릭한 태그 글자 색 변경시 사용하시면 돼요
  const handleClickMenu = (e: any) => {
    setCurrentMenu(e.currentTarget.id);
  };

  const handleTagsMenuClick = () => {
    setIsTagsMenuOpen((prev) => !prev); // 메뉴 열기/닫기 토글
  };
  // 드롭다운 메뉴를 닫는 함수
  const closeDropdownMenu = () => {
    setIsTagsMenuOpen(false);
  };

  useEffect(() => {
    closeDropdownMenu();
  }, [location.pathname]);

  //로그인 회원가입 모달 부분
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
    window.location.replace("/");
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

  const Navigate = (data: string) => {
    const memberId = localStorage.getItem("memberId");
    if (memberId !== null) navigate(`/${data}`);
    else alert("로그인이 필요한 페이지입니다.");
  };

  // 검색어 입력 이벤트 핸들러
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };

  // 엔터키 입력 시 검색 결과 가져오기
  const handleSearchEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      // 검색 결과를 가져오는 로직을 추가
      // 예를 들어, 서버에 검색어를 보내고 결과를 받아와서 setSearchResults로 상태 업데이트
      // setSearchResults([...results]); // 가져온 검색 결과를 상태에 업데이트
      if (searchQuery.trim() !== "") {
        // Search 페이지로 이동하면서 검색어를 쿼리 파라미터로 전달
        navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
      }
    }
  };

  const handleTagClick = (event: React.MouseEvent<HTMLElement>) => {
    const id = (event.target as HTMLElement).id;
    dispatch(setCurrentTag(id));
  };

  return (
    <>
      <RootWrapper>
        <NavWrapper>
          <UlWrapper>
            <Link className="logo" to="/home">
              <LogoImg
                src={isDogpli === "dog" ? DogLogo : CatLogo}
                alt="Logo"
              />
            </Link>
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
              <LiWrapper>
                <NavHome
                  id="home"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "home"}
                >
                  <HomeLink className="home" to="/home">
                    <HomeImg
                      fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"}
                    />
                    <HomeText isActive={currentMenu === "home"}>
                      {"Home"}
                    </HomeText>
                  </HomeLink>
                </NavHome>
              </LiWrapper>
              <LiWrapper onClick={handleTagsMenuClick}>
                <NavTags
                  id="tags"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "tags"}
                >
                  <TagImg
                    fill={currentMenu === "tags" ? "#84CBFF" : "#B4B4B7"}
                  />
                  <TagText isActive={currentMenu === "tags"}>{"Tags"}</TagText>
                  <ViewMore
                    fill={currentMenu === "tags" ? "#84CBFF" : "#B4B4B7"}
                    rotated={isTagsMenuOpen}
                  />
                </NavTags>
                <DropdownMenu hidden={!isTagsMenuOpen}>
                  <MenuItem id="calm" onClick={handleTagClick}>
                    CALM
                  </MenuItem>
                  <MenuItem id="realxing" onClick={handleTagClick}>
                    RELAXING
                  </MenuItem>
                  <MenuItem id="upbeat" onClick={handleTagClick}>
                    UPBEAT
                  </MenuItem>
                  <MenuItem id="serene" onClick={handleTagClick}>
                    SERENE
                  </MenuItem>
                </DropdownMenu>
              </LiWrapper>
              <LiWrapper>
                <NavMylist
                  id="mylist"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "mylist"}
                >
                  <SideDiv
                    className="mylist"
                    onClick={() => Navigate("mylist")}
                  >
                    <MyListImg
                      fill={currentMenu === "mylist" ? "#84CBFF" : "#B4B4B7"}
                    />
                    <MylistText isActive={currentMenu === "mylist"}>
                      {"MyList"}
                    </MylistText>
                  </SideDiv>
                </NavMylist>
              </LiWrapper>
              <LiWrapper onClick={() => Navigate("mypage")}>
                <NavMyPage
                  id="mypage"
                  onClick={handleClickMenu}
                  isActive={currentMenu === "mypage"}
                >
                  <SideDiv
                    className="mypage"
                    onClick={() => Navigate("mypage")}
                  >
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
          </UlWrapper>
        </NavWrapper>
      </RootWrapper>
      {modalOpen && (
        <ModalBackground ref={modalRef} onClick={modalSideClick}>
          <Login />
        </ModalBackground>
      )}
      {Signmodal && (
        <ModalBackground ref={modalRef} onClick={modalSideClick2}>
          <SignUp />
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
  width: 245px;
  height: 100vh;
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
  justify-content: flex-start;
  align-items: center;
  margin-top: 50px;
`;
const HomeLink = styled(Link)`
  display: flex;
  align-items: center;
`;
const LogoImg = styled.img`
  // width: 90px;
  // height: 90px;
`;
const SearchField = styled.div`
  overflow: hidden;
  background-color: white;
  border-radius: 100px;
  display: flex;
  align-items: center;
  margin-top: 20px;
`;
const InputField = styled.input`
  border: none;
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
  position: absolute;
  margin-left: 10px;
`;
const LiStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;
const LiWrapper = styled.li`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  a {
    text-decoration: none;
  }
  margin-top: 20px;
`;
const TagImg = styled(TagsIcon)`
  margin-right: 10px;
`;
const ViewMore = styled(More)<ViewMoreProps>`
  width: 30px;
  height: 30px;
  transform: ${(props) => (props.rotated ? "rotate(180deg)" : "rotate(0)")};
  transition: transform 0.2s ease;
  margin-left: 20px;
`;
const HomeImg = styled(HomeIcon)`
  margin-right: 10px;
`;
const HomeText = styled.span<NavItemProps>`
  text-overflow: ellipsis;
  font-size: 18px;
  font-family: Quicksand, sans-serif;
  font-weight: 700;
  line-height: 100%;
  text-decoration: none;
  color: ${(props) => (props.isActive ? "#84CBFF" : "#B4B4B7")};
`;
const TagText = styled.span<NavItemProps>`
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
`;

const NavHome = styled.div<NavItemProps>``;
const NavMylist = styled.div<NavItemProps>``;
const NavMyPage = styled.div<NavItemProps>``;
const NavTags = styled.div<NavItemProps>`
  display: flex;
  align-items: center;
`;

const DropdownMenu = styled.div`
  position: absolute;
  top: 23%;
  left: 0;
  width: 100%;
  border-radius: 4px;
  background-color: rgb(240, 243, 243);
  // box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 10;
  display: ${(props) =>
    props.hidden ? "none" : "flex"}; // hidden 속성으로 메뉴 숨기기/보이기
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const MenuItem = styled.div`
  padding: 8px 16px;
  color: var(--black);
  cursor: pointer;

  &:hover {
    background-color: var(--gray-100);
  }
`;

const SideDiv = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;
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
  width: 185px;
  height: 40px;
  display: flex;
  margin-top: 450px;
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
`;
