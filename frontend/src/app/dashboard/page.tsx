"use client";
import { NoticeTable } from "@/components/notice-table";

export default function Home() {
  return (
    <main className="">
      <div className="px-10 flex mt-20">
        <div className="flex items-center space-x-2">
          <input
            placeholder="search by title or tags"
            className="w-[300px] px-1 py-2"
          />
          <button className="px-3 py-2 rounded bg-sky-500 font-light capitalize">
            search
          </button>
        </div>
      </div>
      <div className="mt-4 w-full px-10">
        <NoticeTable />
      </div>
    </main>
  );
}
