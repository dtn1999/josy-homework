import Link from "next/link";
import { FaPlus } from "react-icons/fa";
import ProfileMenu from "./profile-menu";

export function NavBar() {
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
