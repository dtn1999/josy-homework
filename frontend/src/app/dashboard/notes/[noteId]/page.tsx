"use client";

import { NoteImage } from "@/components/note-grid";
import { useNoteById } from "@/hooks/use-notes";

export default function SingleNotePage({
  params,
}: {
  params: { noteId: string };
}) {
  const { noteId } = params;
  const note = useNoteById(noteId as string);
  if (!note) {
    return <div>Loading...</div>;
  }
  console.log("note", note);
  return (
    <main className="px-10 py-20">
      <div>
        <h3 className="text-4xl font-semibold py-1">{note.title}</h3>
        <span className="text-xs text-gray-500">
          created at: {new Date(note.createdAt).toLocaleDateString()}
        </span>
      </div>
      <div className="flex space-x-2 flex-wrap items-center py-2 px-1">
        {note.tags.map((tag) => (
          <span
            key={tag}
            className="bg-gray-200 text-gray-700 px-2 py-1 rounded-md"
          >
            {tag}
          </span>
        ))}
      </div>
      {renderImage(note.imageUrl)}
      <p className="py-4">{note.content}</p>
    </main>
  );
}

function renderImage(url?: string) {
  if (url) {
    return (
      <img
        src={url}
        alt="note image"
        width={400}
        height={400}
        className="w-full h-[500px] object-cover rounded-md"
      />
    );
  }
  return (
    <img
      src="/images/image-placeholder.jpeg"
      alt="note image"
      width={400}
      height={400}
      className="w-full h-40 object-cover rounded-md"
    />
  );
}
