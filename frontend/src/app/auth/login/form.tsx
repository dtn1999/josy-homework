"use client";
import React from "react";
import Link from "next/link";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import { login } from "@/utils/lib";
import { useRouter } from "next/navigation";
import { AlertBanner } from "@/components/alert-banner";

const schema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

export type LoginFormValues = z.infer<typeof schema>;

export function LoginForm() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const [error, setError] = React.useState<string>("");
  const [success, setSuccess] = React.useState<string>("");
  const onSubmit = async (data: LoginFormValues) => {
    const response = await login(data.email, data.password);

    if (response.success) {
      setSuccess(response.message);
      setError("");
      setTimeout(() => {
        router.push("/dashboard");
      }, 100);
      return;
    }

    setError(response.message);
    setSuccess("");
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit as SubmitHandler<FieldValues>)}
      className="border h-fit p-5 min-w-[400px] space-y-6"
    >
      <AlertBanner error={error} success={success} />
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
