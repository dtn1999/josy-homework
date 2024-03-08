"use client";
import Image from "next/image";
import React from "react";
import { FaSave } from "react-icons/fa";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
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
  title: z.string().email("title required"),
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

export type LoginFormValues = z.infer<typeof schema>;

export function CreateNoticeForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const [tags, setTags] = React.useState<string[]>([]);
  const [image, setImage] = React.useState<File | null>(null);

  return (
    <form
      onSubmit={handleSubmit((data) => console.log("submitted data", data))}
      className="h-fit min-w-[400px] space-y-6 py-5"
    >
      <h1 className="text-3xl text-left">Create New Notice</h1>
      <div className="flex flex-col">
        <label htmlFor="title">Title</label>
        <input
          type="text"
          id="title"
          className="border border-black text-black px-0 py-1"
          {...register("email")}
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
          <p className="py-1 text-red-500 font-light">{`${errors.content?.message}`}</p>
        )}
        {image && (
          <div className="w-[400px] h-[400px] relative">
            <Image
              src={URL.createObjectURL(image)}
              layout="fill"
              alt="image preview"
              objectFit="cover"
            />
          </div>
        )}
      </div>
      <div className="flex justify-between items-center">
        <button
          type="submit"
          className="px-3 py-2 rounded bg-green-500 text-white flex items-center space-x-1"
        >
          <FaSave />
          <span className="">Create Notice</span>
        </button>
      </div>
    </form>
  );
}
