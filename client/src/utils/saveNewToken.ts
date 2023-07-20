const saveNewToken = (accessToken: string) => {
  if (accessToken) {
    localStorage.removeItem("access_token");
    localStorage.setItem("access_token", accessToken);
  }
};

export default saveNewToken;
