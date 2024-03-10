"use client";
import React from "react";
import Link from "next/link";
import { FaPlus } from "react-icons/fa";
import ProfileMenu from "./profile-menu";
import { useRouter } from "next/navigation";
import { getAuthenticatedUser } from "@/utils/lib";
import { FaHome } from "react-icons/fa";

export function NavBar() {
  const router = useRouter();

  React.useEffect(() => {
    const { token } = getAuthenticatedUser();
    if (token === null) {
      router.push("/auth/login");
    }
  }, []);

  return (
    <nav className="flex w-full items-center px-10 py-4 border-b">
      <ul className="w-full flex justify-between items-center ">
        <li className="flex items-center space-x-3">
          <Link
            className="flex text-xl space-x-2 items-center font-bold py-2 px-3 bg-sky-800 rounded text-white hover:bg-sky-700 transition-colors duration-300 ease-in-out"
            href="/dashboard"
          >
            <FaHome className="w-8 h-8 text-white" title="Dashboard" />
            <span>Dashboard</span>
          </Link>
          <Link
            className="flex items-center space-x-2 py-2 px-3 bg-green-500 rounded text-white hover:bg-green-400 transition-colors duration-300 ease-in-out"
            href="/dashboard/notes/create"
          >
            <FaPlus className="w-8 h-8 text-white" title="Create Notice" />
            <span>Create Note</span>
          </Link>
        </li>
        <li className="">
          <ProfileMenu />
        </li>
      </ul>
    </nav>
  );
}
