export const getToken = () => {
  return localStorage.getItem("key");
};

export const saveToken = (token) => {
  localStorage.setItem("key", token);
};

export const destroyToken = () => {
  localStorage.removeItem("key");
};

export default { getToken, saveToken, destroyToken };
