export type Music = {
  musicId: number;
  title: string;
  music_url: string;
  image_url: string;
  category?: string;
  tags: string;
  liked?: boolean;
  playtime?: string;
};
