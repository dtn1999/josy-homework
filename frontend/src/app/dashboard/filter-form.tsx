"use client";
import React, { Dispatch, SetStateAction } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { searchNotes, Note } from "@/utils/lib";
import { AlertBanner } from "@/components/alert-banner";

const schema = z.object({
  title: z.string().optional(),
  tag: z.string().optional(),
  from: z
    .string()
    .optional()
    .transform((str) => {
      if (str === "") return null;
      return new Date(str as string);
    }),
  to: z
    .string()
    .optional()
    .transform((str) => {
      if (str === "") return null;
      return new Date(str as string);
    }),
});

export type FilterFormValues = z.infer<typeof schema>;

interface Props {
  onFilter: Dispatch<SetStateAction<Note[]>>;
}

export function FilterForm({ onFilter }: Props) {
  const { register, handleSubmit } = useForm({
    resolver: zodResolver(schema),
    defaultValues: {
      title: "",
      tag: "",
      from: null,
      to: null,
    },
  });

  const [error, setError] = React.useState<string>("");
  const [success, setSuccess] = React.useState<string>("");

  const onSubmit = async (data: FilterFormValues) => {
    const response = await searchNotes(data);
    console.error("Error occurred during search", response);
    if (!response.success) {
      setError(response.message);
      setSuccess("");
      return;
    }
    onFilter(response?.data as Note[]);
  };
  return (
    <form
      onSubmit={handleSubmit((data) => onSubmit(data))}
      className="border py-2 px-1 h-fit bg-sky-900 text-white rounded"
    >
      <AlertBanner error={error} success={success} />
      <h3 className="font-bold">Filter</h3>
      <div className="pt-4 space-y-3">
        <div className="flex flex-col">
          <label htmlFor="search">Title</label>
          <input
            type="text"
            id="title"
            className="px-1 py-2 text-black"
            {...register("title")}
          />
        </div>
        <div className="flex flex-col">
          <label htmlFor="tag">Tag</label>
          <input
            type="text"
            id="tag"
            className="px-1 py-2 text-black"
            {...register("tag")}
          />
        </div>
        <div className="flex flex-col">
          <label htmlFor="from">From</label>
          <input
            type="date"
            id="from"
            className="px-1 py-2 text-black"
            {...register("from")}
          />
        </div>
        <div className="flex flex-col">
          <label htmlFor="to">To</label>
          <input
            type="date"
            id="to"
            className="px-1 py-2 text-black"
            {...register("to")}
          />
        </div>
        <div className="pt-4">
          <button
            className="py-2 px-3 border rounded bg-blue-950 text-center w-full border-blue-950 hover:bg-blue-900 transition-colors duration-300 ease-in-out hover:border-blue-900"
            type="submit"
          >
            Search
          </button>
        </div>
      </div>
    </form>
  );
}
