type HandlePageClick = (pageNumber: number) => void;

export const PageButtons = (
  currentPage: number,
  totalPages: number,
  handlePageClick: HandlePageClick
) => {
  const pageButtons = [];

  pageButtons.push(
    <button
      key="prev"
      onClick={() => handlePageClick(currentPage - 1)}
      disabled={currentPage === 1}
    >
      &lt;
    </button>
  );

  for (let pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
    pageButtons.push(
      <button
        key={pageNumber}
        onClick={() => handlePageClick(pageNumber)}
        className={pageNumber === currentPage ? "active" : ""}
      >
        {pageNumber}
      </button>
    );
  }

  pageButtons.push(
    <button
      key="next"
      onClick={() => handlePageClick(currentPage + 1)}
      disabled={currentPage === totalPages}
    >
      &gt;
    </button>
  );

  return pageButtons;
};
