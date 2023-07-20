import { createSlice } from "@reduxjs/toolkit";

const homeSlice = createSlice({
  name: "home",
  initialState: {
    isDogpli: "dog",
  },
  reducers: {
    setIsDogpli: (state, action) => {
      state.isDogpli = action.payload;
    },
  },
});

export const { setIsDogpli } = homeSlice.actions;
export default homeSlice.reducer;
