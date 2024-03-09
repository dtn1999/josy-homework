"use client";
import React from "react";
import Link from "next/link";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import { ApiResponse, registerUser } from "@/utils/lib";
import { useRouter } from "next/navigation";
import { AlterBanner } from "@/components/alert-banner";

const schema = z
  .object({
    firstname: z.string().min(1, "First name is required"),
    lastname: z.string().min(1, "Last name is required"),
    email: z.string().email("Invalid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string(),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: "Passwords do not match",
        path: ["confirmPassword"],
      });
    }
  });

export type RegistrationFormValues = z.infer<typeof schema>;

export function RegistrationForm() {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const [error, setError] = React.useState<string | null>(null);
  const [success, setSuccess] = React.useState<string | null>(null);

  const onSubmit = async (data: FieldValues) => {
    console.log(data);
    const response = (await registerUser(
      data as RegistrationFormValues
    )) as ApiResponse<undefined>;
    if (response.success === false) {
      setError(response.message);
      setSuccess(null);
      return;
    }
    setSuccess(response.message);
    setError(null);
    setTimeout(() => {
      router.push("/dashboard");
    }, 10);
  };

  return (
    <form
      onSubmit={handleSubmit((data) =>
        onSubmit(data as RegistrationFormValues)
      )}
      className="border h-fit p-5 min-w-[400px] space-y-6"
    >
      <AlterBanner error={error} success={success} />
      <h1 className="text-3xl text-center">Create an account</h1>
      <div className="flex flex-col">
        <label htmlFor="firstname">First Name</label>
        <input
          type="text"
          id="firstname"
          className="border border-black text-black px-0 py-1"
          {...register("firstname")}
        />
        {dirtyFields?.firstname && errors?.firstname && (
          <p className="py-1 text-red-500 font-light">{`${errors.firstname?.message}`}</p>
        )}
      </div>
      <div className="flex flex-col">
        <label htmlFor="lastname">Last Name</label>
        <input
          type="text"
          id="lastname"
          className="border border-black text-black px-0 py-1"
          {...register("lastname")}
        />
        {dirtyFields?.lastname && errors?.lastname && (
          <p className="py-1 text-red-500 font-light">{`${errors.lastname?.message}`}</p>
        )}
      </div>
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
        <label htmlFor="password">Password</label>
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
      <div className="flex flex-col">
        <label htmlFor="password">Confirm Password</label>
        <input
          type="password"
          id="confirmPassword"
          className="border border-black text-black px-0 py-1"
          {...register("confirmPassword")}
        />
        {dirtyFields?.confirmPassword && errors?.confirmPassword && (
          <p className="py-1 text-red-500 font-light">{`${errors.confirmPassword?.message}`}</p>
        )}
      </div>
      <div className="flex justify-between items-center">
        <button
          type="submit"
          className="px-3 py-2 rounded bg-blue-500 text-white"
        >
          Create account
        </button>
        <Link href="/auth/login" className="text-blue-500">
          login
        </Link>
      </div>
    </form>
  );
}
