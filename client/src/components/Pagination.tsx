import { styled } from "styled-components";
import { PageButtons } from "./commons/PageButtons";

type PaginationProps = {
  currentPage: number;
  totalPages: number;
  onPageChange: (pageNumber: number) => void;
};

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 20px;
  button {
    width: 1.25rem;
    height: 1.25rem;
    margin: 0 4px;
    border: none;
    border-radius: 3px;
    color: var(--black);
    font-family: var(--font-quicksand);
    font-size: 0.75rem;
    font-style: normal;
    font-weight: 400;
    line-height: 100%;
    background-color: var(--gray-100);
    cursor: pointer;

    &:disabled {
      color: var(--gray-300);
      cursor: default;
    }

    &.active {
      background-color: var(--skyblue-100);
    }
  }
`;

const Pagination: React.FC<PaginationProps> = ({
  currentPage,
  totalPages,
  onPageChange,
}) => {
  const handlePageClick = (pageNumber: number) => {
    if (pageNumber >= 1 && pageNumber <= totalPages) {
      onPageChange(pageNumber);
    }
  };

  return (
    <PaginationContainer>
      {PageButtons(currentPage, totalPages, handlePageClick)}
    </PaginationContainer>
  );
};

export default Pagination;
