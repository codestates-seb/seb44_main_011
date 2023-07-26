export const NameRegEx = /^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{2,7}$/;
export const EmailRegEx =
  /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/i;
export const PasswordMin = 8;
export const PasswordMax = 16;
export const PasswordRegEx = /^[A-Za-z0-9]+$/;
//export const PasswordRegEx = /^(?=.*[A-Za-z])(?=.*[0-9]).{8,16}$/;
//export const reg = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
