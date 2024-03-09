import { Note, getAllNotes, getNoteById } from "@/utils/lib";
import React from "react";

export function useNotes() {
  const [notes, setNotes] = React.useState<Note[]>([]);
  const fetchNotes = async () => {
    const response = await getAllNotes();
    if (response.success) {
      setNotes(response.data);
    } else {
      console.error(response.message);
      setNotes([]);
    }
  };

  React.useEffect(() => {
    fetchNotes();
  }, []);

  return notes;
}

export function useNoteById(id: string) {
  const [note, setNote] = React.useState<Note | null>(null);
  const fetchNote = async () => {
    const response = await getNoteById(id);
    if (response.success) {
      setNote(response.data);
    } else {
      console.error(response.message);
      setNote(null);
    }
  };

  React.useEffect(() => {
    fetchNote();
  }, [id]);

  return note;
}
