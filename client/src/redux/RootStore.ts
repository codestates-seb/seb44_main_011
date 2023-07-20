import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { userSlice } from "./UserInfo";
import homeReducer from "./homeSlice";
import tagReducer from "./tagSlice";

const rootReducer = combineReducers({
  userSlice: userSlice.reducer,
  home: homeReducer,
  tags: tagReducer,
});
export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
