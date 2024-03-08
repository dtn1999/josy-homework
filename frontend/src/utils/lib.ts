import { RegistrationFormValues } from "@/app/auth/register/form";
import axios, { AxiosError } from "axios";

const backendApi = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export type ApiResponse = {
  message: string;
  success: boolean;
};

export type ProblemDetail = {
  detail: string;
  status: number;
  title: string;
  type: string;
};

type BackendResponse = {
  data: unknown;
  message: string;
  success: boolean;
};

export async function registerUser(details: RegistrationFormValues) {
  try {
    const {
      data: { data, message },
    } = await backendApi.post("/auth/register", {
      email: details.email,
      password: details.password,
      firstName: details.firstname,
      lastName: details.lastname,
    });
    storeToken(data.accessToken, data.username);
    return {
      message,
      success: true,
    };
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function login(email: string, password: string) {
  try {
    const {
      data: { data, message },
    } = await backendApi.post("/auth/login", {
      email,
      password,
    });
    storeToken(data.accessToken, data.username);
    return {
      message,
      success: true,
    };
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export function logout() {
  const { token } = getAuthenticatedUser();
  try {
    backendApi.post("/auth/logout", null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    removeToken();
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

function storeToken(token: string, username: string) {
  localStorage.setItem("token", token);
  localStorage.setItem("username", username);
}

function removeToken() {
  localStorage.removeItem("token");
  localStorage.removeItem("username");
}

export function getAuthenticatedUser() {
  return {
    token: localStorage.getItem("token"),
    username: localStorage.getItem("username"),
  };
}

function mapToProblemDetail(error: AxiosError) {
  return error.response?.data as ProblemDetail;
}