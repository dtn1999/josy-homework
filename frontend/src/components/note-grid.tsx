import React from "react";
import { Note, deleteNoteById } from "@/utils/lib";
import Image from "next/image";
import Link from "next/link";
import { FaEye } from "react-icons/fa";
import { MdDeleteForever } from "react-icons/md";

interface Props {
  notes: Note[];
  loading: boolean;
}

export function NoteGrid({ notes, loading }: Props) {
  // just a dummy state to force remount of the component
  const [refresh, setRefresh] = React.useState<boolean>(false);
  const deleteNote = async (note: Note) => {
    const deletionConfirmed = confirm(
      "Are you sure you want to delete this note?"
    );
    if (deletionConfirmed) {
      // delete note
      await deleteNoteById(`${note.id}`);
      // force remount of the component
      setRefresh(!refresh);
    }
  };

  // a dummy useEffect to force remount of the component
  React.useEffect(() => {}, [refresh]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="grid grid-cols-3 gap-5">
      {notes.length == 0 ? (
        <div className="text-center">No notes </div>
      ) : (
        notes?.map((note) => (
          <div
            key={note.id}
            className="px-5 py-1 shadow-md rounded border h-[350px]"
          >
            <div>
              <h3 className="text-lg font-semibold py-1">{note.title}</h3>
              <span className="text-xs text-gray-500">
                created at: {new Date(note.createdAt).toLocaleDateString()}
              </span>
            </div>
            {/* ellipse text with 100 characters  in a p-tag */}
            <p className="py-2">{note.content.slice(0, 35)}...</p>
            {/* if imageUrl is present, show the image */}
            <div>
              <NotImage url={note.imageUrl} />
              <div className="py-1 self-end">
                <div className="flex w-full justify-between py-2 text-white">
                  <Link
                    href={`/dashboard/notes/${note.id}`}
                    className="flex items-center border px-2 rounded bg-green-500 py-2"
                  >
                    <FaEye />
                    <span className="pl-2">View</span>
                  </Link>
                  <button
                    onClick={() => deleteNote(note)}
                    className="flex items-center border px-2 rounded bg-red-500 py-2"
                  >
                    <MdDeleteForever />
                    <span className="pl-2">Delete</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export function NotImage({ url }: { url?: string }) {
  if (!url) {
    <Image
      src="/images/image-placeholder.jpeg"
      alt="note image"
      width={40}
      height={40}
      className="w-full h-40 object-cover rounded-md"
    />;
  }

  return (
    <img
      src={url as string}
      alt="note image"
      width={40}
      height={40}
      className="w-full h-40 object-cover rounded-md"
    />
  );
}
