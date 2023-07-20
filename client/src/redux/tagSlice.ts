import { createSlice } from "@reduxjs/toolkit";

const tagSlice = createSlice({
  name: "tags",
  initialState: {
    currentTag: "",
  },
  reducers: {
    setCurrentTag: (state, action) => {
      state.currentTag = action.payload;
    },
  },
});

export default tagSlice.reducer;
export const { setCurrentTag } = tagSlice.actions;
