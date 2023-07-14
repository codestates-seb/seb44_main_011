import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { userSlice } from "./UserInfo";

const rootReducer = combineReducers({
  userSlice: userSlice.reducer,
});
export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
