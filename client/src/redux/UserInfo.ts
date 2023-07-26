import { PayloadAction, createSlice } from "@reduxjs/toolkit";

type User = {
  memberId: string;
  email: string;
  role: string;
};
const initialUser: User = {
  memberId: "",
  email: "",
  role: "",
};

const initialState = {
  currentUser: initialUser,
};

export const userSlice = createSlice({
  name: "userInfo",
  initialState,
  reducers: {
    loginUser: (state, action: PayloadAction<User>) => {
      state.currentUser = action.payload;
    },
  },
});

const { reducer, actions } = userSlice;

export const { loginUser } = actions;

export default reducer;
