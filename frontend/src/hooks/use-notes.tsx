import { Note, getAllNotes, getNoteById } from "@/utils/lib";
import React from "react";

export function useNotes() {
  const [notes, setNotes] = React.useState<Note[]>([]);
  const [loading, setLoading] = React.useState<boolean>(false);

  const fetchNotes = async () => {
    setLoading(true);
    const response = await getAllNotes();
    if (response.success) {
      setNotes(response?.data as Note[]);
    } else {
      console.error(response.message);
      setNotes([]);
    }
    setLoading(false);
  };

  React.useEffect(() => {
    fetchNotes();
  }, []);

  return { notes, setNotes, loading };
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
