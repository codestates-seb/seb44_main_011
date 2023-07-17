export function calculateTimeAgo(createdAt: string): string {
  const now = new Date();
  const date = new Date(createdAt);

  const diffMilliseconds = now.getTime() - date.getTime();
  const diffHours = Math.floor(diffMilliseconds / (1000 * 60 * 60));

  if (diffHours < 24) {
    return `${diffHours}시간 전`;
  } else {
    const year = date.getFullYear().toString().slice(-2).padStart(2, "0");
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}.${month}.${day}`;
  }
}
