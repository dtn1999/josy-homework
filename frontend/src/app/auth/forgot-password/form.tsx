"use client";
import Link from "next/link";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";

const schema = z
  .object({
    email: z.string().email("Invalid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z
      .string()
      .min(6, "Password must be at least 6 characters"),
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

type PasswordForgottenFormValues = z.infer<typeof schema>;

export function PasswordForgottenForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const onSubmit = (data: FieldValues) => console.log(data);

  return (
    <form
      onSubmit={handleSubmit((data) =>
        onSubmit(data as PasswordForgottenFormValues)
      )}
      className="border h-fit p-5 min-w-[400px] space-y-6"
    >
      <h1 className="text-3xl text-center">Forgot password</h1>
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
          Reset password
        </button>
        <Link href="/auth/login" className="text-blue-500">
          login
        </Link>
      </div>
    </form>
  );
}
