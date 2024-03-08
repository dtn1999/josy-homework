"use client";
import Link from "next/link";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";

const schema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

export type LoginFormValues = z.infer<typeof schema>;

export function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const login = async (data: LoginFormValues) => {
    console.log("login", "email", data.email, "password", data.password);
    const response = await fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    const responseData = await response.json();
    console.log("response", responseData);
  };

  return (
    <form
      onSubmit={handleSubmit(login as SubmitHandler<FieldValues>)}
      className="border h-fit p-5 min-w-[400px] space-y-6"
    >
      <h1 className="text-3xl text-center">Login</h1>
      <div className="flex flex-col">
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          className="border border-black text-black px-0 py-1"
          {...register("email")}
        />
        {dirtyFields?.email && errors?.email && (
          <p className="py-1 text-red-500 font-light">{`${errors.email?.message}`}</p>
        )}
      </div>
      <div className="flex flex-col">
        <div className="flex justify-between">
          <label htmlFor="password">Password</label>
          <Link href="/auth/forgot-password" className="text-blue-500">
            forgot password?
          </Link>
        </div>
        <input
          type="password"
          id="password"
          className="border border-black text-black px-0 py-1"
          {...register("password")}
        />
        {dirtyFields?.password && errors?.password && (
          <p className="py-1 text-red-500 font-light">{`${errors.password?.message}`}</p>
        )}
      </div>
      <div className="flex justify-between items-center">
        <button
          type="submit"
          className="px-3 py-2 rounded bg-blue-500 text-white"
        >
          Login
        </button>
        <Link href="/auth/register" className="text-blue-500">
          sign up
        </Link>
      </div>
    </form>
  );
}
