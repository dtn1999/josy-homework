"use client";
import React from "react";
import Link from "next/link";
import { FaPlus } from "react-icons/fa";
import ProfileMenu from "./profile-menu";
import { useRouter } from "next/navigation";
import { getAuthenticatedUser } from "@/utils/lib";

export function NavBar() {
  const router = useRouter();

  React.useEffect(() => {
    const { token } = getAuthenticatedUser();
    if (token === null) {
      router.push("/auth/login");
    }
  }, []);

  return (
    <nav className="flex w-full items-center px-10 py-2 border-b">
      <ul className="w-full flex justify-between items-center ">
        <li className="">
          <Link
            className="flex items-center space-x-2 py-2 px-3 bg-green-500 rounded"
            href="/notice/create"
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
