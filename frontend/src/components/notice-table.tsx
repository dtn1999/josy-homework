import { FaEdit } from "react-icons/fa";
import { MdDeleteForever } from "react-icons/md";

export function NoticeTable() {
  return (
    <table className="w-full border">
      <thead className="border">
        <tr className="border">
          <th className="border">Title</th>
          <th className="border">Created</th>
          <th className="border">Actions</th>
        </tr>
      </thead>
      <tbody className="border">
        <tr className="border">
          <td className="border p-2">Notice 1</td>
          <td className="border p-2">2022-01-01</td>
          <td className="border p-2 flex items-center space-x-10">
            <button className="flex items-center space-x-2">
              <span>edit</span>
              <FaEdit className="text-blue-500 text-md" />
            </button>
            <button className="flex items-center space-x-2">
              <span>delete</span>
              <MdDeleteForever className="text-red-500 text-md" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  );
}
