"use client";
import Image from "next/image";
import React from "react";
import { FaSave } from "react-icons/fa";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import { TagInput } from "@/components/tag-input";

const MAX_FILE_SIZE = 1024 * 1024 * 5;

const ACCEPTED_IMAGE_MIME_TYPES = [
  "image/jpeg",
  "image/jpg",
  "image/png",
  "image/webp",
];

const schema = z.object({
  title: z.string().min(5, "title required"),
  content: z.string().min(1, "content required"),
  image: z
    .any()
    .refine((files) => {
      return files?.[0]?.size <= MAX_FILE_SIZE;
    }, `Max image size is 5MB.`)
    .refine(
      (files) => ACCEPTED_IMAGE_MIME_TYPES.includes(files?.[0]?.type),
      "Only .jpg, .jpeg, .png and .webp formats are supported."
    ),
});

export type CreateNoticeFormValues = z.infer<typeof schema>;

export function CreateNoticeForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
    watch,
  } = useForm({
    resolver: zodResolver(schema),
  });

  const [tags, setTags] = React.useState<string[]>([]);
  const [image, setImage] = React.useState<File | null>(null);

  const createNotice = async (data: CreateNoticeFormValues) => {
    console.log(
      "create notice",
      "title",
      data.title,
      "content",
      data.content,
      "tags",
      tags,
      "image",
      image
    );
  };
  watch();
  return (
    <form
      onSubmit={handleSubmit(createNotice as SubmitHandler<FieldValues>)}
      className="h-fit min-w-[400px] space-y-6 py-5"
    >
      <h1 className="text-3xl text-left">Create New Notice</h1>
      <div className="flex flex-col">
        <label htmlFor="title">Title</label>
        <input
          type="text"
          id="title"
          className="border border-black text-black px-0 py-1"
          {...register("title")}
        />
        {dirtyFields?.title && errors?.title && (
          <p className="py-1 text-red-500 font-light">{`${errors.title?.message}`}</p>
        )}
      </div>
      <div className="flex flex-col">
        <label htmlFor="password">Content</label>
        <textarea
          id="content"
          rows={10}
          className="border border-black text-black px-0 py-1"
          {...register("content")}
        />
        {dirtyFields?.content && errors?.content && (
          <p className="py-1 text-red-500 font-light">{`${errors.content?.message}`}</p>
        )}
      </div>
      <TagInput
        tags={tags}
        setTags={setTags}
        placeholder="add tags"
        label="Tags"
      />
      <div className="flex flex-col">
        <label htmlFor="password">Image</label>
        <input
          type="file"
          id="image"
          className="border border-black text-black px-0 py-1"
          {...register("image")}
          onChange={(e) => {
            const file = e.target.files?.[0];
            if (file) {
              setImage(file);
            }
          }}
        />
        {dirtyFields?.image && errors?.image && (
          <p className="py-1 text-red-500 font-light">{`${errors.image?.message}`}</p>
        )}
        {
          <div className="w-[400px] h-[400px] relative">
            <Image
              src="http://localhost:8080/api/notes/files/32b4253d-012e-4a27-b9e7-eb14679e7c55.jpg"
              className="rounded-md absolute inset-0 w-full h-full size-cover"
              alt="image preview"
              width={400}
              height={400}
            />
          </div>
        }
      </div>
      <button
        type="submit"
        className="px-3 py-2 rounded bg-green-500 text-white flex items-center space-x-1"
      >
        <FaSave />
        <span className="">Create Notice</span>
      </button>
    </form>
  );
}
