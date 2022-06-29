import axios from "axios";
import { saveToken, getToken } from "@/services/jwt.service";
import router from "@/router";

let baseUrl =
  process.env.NODE_ENV === "production"
    ? "/api/"
    : "http://localhost:8098/api/";
const $axios = axios.create({
  baseURL: baseUrl,
  headers: {
    "Access-Control-Allow-Origin": "http://localhost:8080",
    "Content-Type": "application/json;charset=utf-8",
  },
});

$axios.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers["Authorization"] = token;
    }
    return config;
  },
  (error) => {
    Promise.reject(error).then(() => {});
  }
);

$axios.interceptors.response.use(
  (response) => {
    return response;
  },
  function (error) {
    localStorage.removeItem("key");
    router.push("/login");
  }
);

export default $axios;
