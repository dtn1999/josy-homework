"use client";
import { useNotes } from "@/hooks/use-notes";
import { FilterForm } from "./filter-form";
import { NoteGrid } from "@/components/note-grid";

export default function Home() {
  const { notes, setNotes, loading } = useNotes();
  console.log(notes);
  return (
    <main className="px-10 pt-20">
      <div className="grid grid-cols-4 gap-5">
        <div className="w-full">
          <FilterForm onFilter={setNotes} />
        </div>
        <div className="w-full col-span-3">
          <NoteGrid notes={notes} loading={loading} />
        </div>
      </div>
    </main>
  );
}
