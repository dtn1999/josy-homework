import { Note } from "@/utils/lib";

interface Props {
  notes: Note[];
}

export function NoteGrid({ notes }: Props) {
  return (
    <div className="grid grid-cols-2 gap-5">
      {notes.length == 0 ? (
        <div className="text-center">No notes </div>
      ) : (
        notes.map((note) => (
          <div key={note.id} className="bg-white p-5 shadow-md rounded-md">
            <h3 className="text-lg font-semibold">{note.title}</h3>
          </div>
        ))
      )}
    </div>
  );
}
