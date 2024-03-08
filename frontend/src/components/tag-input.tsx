/* eslint-disable @typescript-eslint/no-unused-vars */
import React from "react";
interface Props {
  tags: string[];
  setTags: (tags: string[]) => void;
  label: string;
  placeholder: string;
  className?: string;
}

export function TagInput({ tags, setTags, placeholder, label }: Props) {
  const [tag, setTag] = React.useState("");

  const handleAddTag = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.preventDefault();
    const newTags = tags.filter((t) => t !== tag);
    newTags.push(tag);
    setTags(newTags);
    setTag("");
  };

  const handleRemoveTag = (tag: string) => {
    const newTags = tags.filter((t) => t !== tag);
    setTags(newTags);
  };

  return (
    <div className="flex flex-col">
      <label htmlFor="title">{label}</label>
      <div>
        <input
          type="text"
          value={tag}
          placeholder={placeholder}
          onChange={(e) => {
            e.preventDefault();
            setTag(e.target.value);
          }}
          className="border border-black text-black px-0 py-1"
        />
        <button
          disabled={!tag}
          onClick={handleAddTag}
          className="px-3 py-1 bg-blue-500 text-white"
        >
          Add Tag
        </button>
      </div>
      <div className="flex flex-col">
        <ul className="flex flex-col">
          {tags &&
            tags.map((tag) => (
              <li
                key={tag}
                className="flex items-center space-x-1 bg-slate-400 w-[188px] justify-between px-2"
              >
                <span>{tag}</span>
                <button
                  onClick={() => handleRemoveTag(tag)}
                  className="text-red-500"
                >
                  x
                </button>
              </li>
            ))}
        </ul>
      </div>
    </div>
  );
}
