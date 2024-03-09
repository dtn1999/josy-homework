import { RegistrationFormValues } from "@/app/auth/register/form";
import axios, { AxiosError } from "axios";

const backendApi = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export interface Note {
  id: string;
  title: string;
  content: string;
  tags: string[];
  createdAt: string;
  imageUrl?: string;
}

export interface UserDetails {
  email: string;
  firstname: string;
  lastname: string;
}

export interface ApiResponse<T> {
  message: string;
  success: boolean;
  data?: T;
}

export type ProblemDetail = {
  detail: string;
  status: number;
  title: string;
  type: string;
};

export async function registerUser(
  details: RegistrationFormValues
): Promise<ApiResponse<undefined>> {
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

export async function login(
  email: string,
  password: string
): Promise<ApiResponse<undefined>> {
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
    console.error("Error occurred during login", errorResponse);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function logout() {
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

export async function getMyProfile(): Promise<ApiResponse<UserDetails>> {
  const { token } = getAuthenticatedUser();
  try {
    const {
      data: { data, message },
    } = await backendApi.get("/auth/me", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return {
      message,
      success: true,
      data,
    };
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function createNote(data: FormData) {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.post("/notes", data, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      },
    });
    return {
      message: "Note created successfully",
      success: true,
      data: response.data,
    };
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    console.error("Error occurred during note creation", errorResponse);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function getAllNotes(): Promise<ApiResponse<Note[]>> {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.get("/notes", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function getNoteById(id: string) {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.get(`/notes/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function updateNoteById(id: string, data: FormData) {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.put(`/notes/${id}`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function deleteNoteById(id: string) {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.delete(`/notes/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    const errorResponse = mapToProblemDetail(error as AxiosError);
    return {
      message: errorResponse?.detail || "An error occurred",
      success: false,
    };
  }
}

export async function getAllTags() {
  const { token } = getAuthenticatedUser();
  try {
    const response = await backendApi.get("/notes/tags", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
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
