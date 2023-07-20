import { keyframes, styled } from "styled-components";
import React, { useState, useRef, useEffect } from "react";
import { ReactComponent as HomeIcon } from "../../src/assets/icons/home.svg";
import { ReactComponent as TagsIcon } from "../../src/assets/icons/tags.svg";
import { ReactComponent as MylistIcon } from "../../src/assets/icons/mylist.svg";
import { ReactComponent as MypageIcon } from "../../src/assets/icons/mypage.svg";
import { ReactComponent as SearchIcon } from "../../src/assets/icons/search.svg";
import DogLogo from "../../src/assets/imgs/doglogo.png";
import CatLogo from "../../src/assets/imgs/catlogo.png";
import { ReactComponent as More } from "../assets/icons/more.svg";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Login from "../pages/Login";
import SignUp from "../pages/SignUp";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/RootStore";
import { setCurrentTag } from "../redux/tagSlice";

interface ViewMoreProps {
  rotated: boolean;
}

function SideBar() {
  const [isTagsMenuOpen, setIsTagsMenuOpen] = useState(false); // 드롭다운 메뉴가 열려있는지 여부를 저장하는 상태 변수

  const [modalOpen, setModalOpen] = useState(false);
  const [Signmodal, setSignModal] = useState(false);
  const modalRef = useRef(null);
  const isLogin = localStorage.getItem("memberId");

  const navigate = useNavigate();
  // const [searchQuery, setSearchQuery] = useState("");
  const location = useLocation();

  const [currentMenu, setCurrentMenu] = useState<string>("hello");

  const dispatch = useDispatch();

  const isDogpli = useSelector((state: RootState) => state.home.isDogpli);

  // const currentTag = useSelector((state: RootState) => state.tags.currentTag); 클릭한 태그 글자 색 변경시 사용하시면 돼요

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

  const handleClickMenu = (e: any) => {
    setCurrentMenu(e.target.id);
  };
  const handleTagsMenuClick = () => {
    setIsTagsMenuOpen((prev) => !prev); // 메뉴 열기/닫기 토글
  };
  // 드롭다운 메뉴를 닫는 함수
  const closeDropdownMenu = () => {
    setIsTagsMenuOpen(false);
  };

  const handleTagClick = (event: React.MouseEvent<HTMLElement>) => {
    const id = (event.target as HTMLElement).id;
    dispatch(setCurrentTag(id));
  };

  useEffect(() => {
    closeDropdownMenu();
  }, [location.pathname]);

  // //검색기능
  // const [searchQuery, setSearchQuery] = useState("");

  // // 검색 결과 상태
  // const [searchResults, setSearchResults] = useState([]);

  const Navigate = (data: string) => {
    const memberId = localStorage.getItem("memberId");
    if (memberId !== null) navigate(`/${data}`);
    else alert("로그인이 필요한 페이지입니다.");
  };

  // // 검색어 입력 이벤트 핸들러
  // const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  //   setSearchQuery(e.target.value);
  // };

  // // 엔터키 입력 시 검색 결과 가져오기
  // const handleSearchEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
  //   if (e.key === "Enter") {
  //     // 검색 결과를 가져오는 로직을 추가
  //     // 예를 들어, 서버에 검색어를 보내고 결과를 받아와서 setSearchResults로 상태 업데이트
  //     // setSearchResults([...results]); // 가져온 검색 결과를 상태에 업데이트
  //     if (searchQuery.trim() !== "") {
  //       // Search 페이지로 이동하면서 검색어를 쿼리 파라미터로 전달
  //       navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
  //     }
  //   }
  // };

  return (
    <>
      <RootWrapper>
        <NavLogo>
          <NaN_0001>
            <LogoImg src={isDogpli === "dog" ? DogLogo : CatLogo} alt="Logo" />
          </NaN_0001>
        </NavLogo>
        <Nav>
          <InputField
            type="text"
            // value={searchQuery}
            // onChange={handleInputChange}
            // onKeyDown={handleSearchEnter}
          />
          <SearchImg fill="#B4B4B7" />
        </Nav>
        <Link className="home" to="/">
          <NavHome id="home" onClick={() => handleClickMenu("home")}>
            <HomeImg fill={currentMenu === "home" ? "#84CBFF" : "#B4B4B7"} />
            <Home_0001>Home</Home_0001>
          </NavHome>
        </Link>
        <SideDiv className="mylist" onClick={() => Navigate("mylist")}>
          <NavMylist id="mylist" onClick={() => handleClickMenu("mylist")}>
            <MyList>MyList</MyList>
            <MyListImg
              fill={currentMenu === "mylist" ? "#84BCFF" : "#B4B4B7"}
            />
          </NavMylist>
        </SideDiv>
        <TagContainer className="tags">
          <NavTags id="tags" onClick={handleTagsMenuClick}>
            <Tags>Tags</Tags>
            <TagImg fill={currentMenu === "tags" ? "#84BCFF" : "#B4B4B7"} />
            <ViewMore fill="#B4B4B7" rotated={isTagsMenuOpen} />
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
        </TagContainer>

        <SideDiv className="mypage" onClick={() => Navigate("mypage")}>
          <NavMypage id="mypage" onClick={() => handleClickMenu("mypage")}>
            <MyPage id="mypageText">MyPage</MyPage>
            <MypageImg
              id="mypageImg"
              fill={currentMenu === "mypage" ? "#84BCFF" : "#B4B4B7"}
            />
          </NavMypage>
        </SideDiv>
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
export default SideBar;

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const TagContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: right;
`;

const DropdownMenu = styled.div`
  display: flex;
  width: 100%;
  border-radius: 4px;
  background-color: rgb(240, 243, 243);
  // box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
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
const SideDiv = styled.div`
  cursor: pointer;
`;

const RootWrapper = styled.div`
  background-color: rgb(240, 243, 243);
  border: solid 1px rgba(255, 255, 255, 0.16);
  position: fixed;
  box-shadow: 0px 4px 5px 2px rgba(217, 217, 217, 0.5);
  width: 245px;
  min-width: 245px;
  max-width: 350px;
  display: flex;
  height: 100vh;
`;
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

const LogoImg = styled.img`
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
  width: 170px;
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

const ViewMore = styled(More)<ViewMoreProps>`
  position: absolute;
  left: 80%;
  top: -2px;
  width: 30px;
  height: 30px;
  transform: ${(props) => (props.rotated ? "rotate(180deg)" : "rotate(0)")};
  transition: transform 0.2s ease;
`;
// ViewMore 컴포넌트 스타일 정의
// const ViewMore = styled(More)`
//   position: absolute;
//   left: 80%;
//   top: -2px;
//   width: 30px;
//   height: 30px;
//   transform: ${(props) => (props.$rotated ? "rotate(180deg)" : "rotate(0)")};
//   transition: transform 0.2s ease;
// `;

const ButtonWrapper = styled.div`
  width: 185px;
  height: 40px;
  position: absolute;
  left: calc((calc((50% + 1px)) - 93px));
  top: calc(88vh);
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
