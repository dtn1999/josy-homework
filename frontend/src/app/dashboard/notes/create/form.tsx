"use client";
import Image from "next/image";
import React from "react";
import { FaSave } from "react-icons/fa";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import { TagInput } from "@/components/tag-input";
import { createNote } from "@/utils/lib";
import { useRouter } from "next/navigation";
import { AlertBanner } from "@/components/alert-banner";

const MAX_FILE_SIZE = 1024 * 1024 * 5;

const ACCEPTED_IMAGE_MIME_TYPES = [
  "image/jpeg",
  "image/jpg",
  "image/png",
  "image/webp",
];

const schema = z.object({
  title: z.string().min(5, "title required"),
  content: z.string(),
  image: z
    .any()
    .optional()
    .refine((files) => {
      if (!files || files.length == 0) return true;
      return files?.[0]?.size <= MAX_FILE_SIZE;
    }, `Max image size is 5MB.`)
    .refine((files) => {
      if (!files || files.length == 0) return true;
      return;
      ACCEPTED_IMAGE_MIME_TYPES.includes(files?.[0]?.type);
    }, "Only .jpg, .jpeg, .png and .webp formats are supported."),
});

export type CreateNoticeFormValues = z.infer<typeof schema>;

export function CreateNoticeForm() {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    formState: { errors, dirtyFields },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const [tags, setTags] = React.useState<string[]>([]);
  const [image, setImage] = React.useState<File | null>(null);
  const [error, setError] = React.useState<string | null>("");

  console.log("errors ", errors);
  const createNotice = async (data: CreateNoticeFormValues) => {
    setError(null);
    const formData = new FormData();
    const payload = JSON.stringify({
      title: data.title,
      content: data.content,
      tags,
    });

    formData.append("payload", payload);
    formData.append("image", image as Blob);
    const response = await createNote(formData);
    if (!response?.success) {
      setError(response?.message || "An error occurred");
      return;
    }
    router.push("/dashboard");
  };
  return (
    <form
      onSubmit={handleSubmit(createNotice as SubmitHandler<FieldValues>)}
      className="h-fit min-w-[400px] space-y-6 py-5"
    >
      <h1 className="text-3xl text-left">Create New Notice</h1>
      <AlertBanner error={error} success={null} />
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
        {image && (
          <div className="w-40 h-40 relative">
            <Image
              src={URL.createObjectURL(image)}
              className="rounded-md absolute inset-0 w-full h-full object-cover"
              alt="image preview"
              width={400}
              height={400}
            />
          </div>
        )}
      </div>
      <button
        type="submit"
        className="px-3 py-2 rounded bg-green-500 text-white flex items-center space-x-1"
      >
        <FaSave />
        <span className="">Create Note</span>
      </button>
    </form>
  );
}
