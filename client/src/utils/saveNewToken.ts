const saveNewToken = (accessToken: string) => {
  if (accessToken) {
    localStorage.removeItem("accessToken");
    localStorage.setItem("accessToken", accessToken);
  }
};

export default saveNewToken;
