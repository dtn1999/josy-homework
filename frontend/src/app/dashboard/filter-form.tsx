export function FilterForm() {
  return (
    <form className="border py-2 px-1 h-fit bg-sky-900">
      <h3 className="font-bold">Filter</h3>
      <div className="pt-4 space-y-3">
        <div className="flex flex-col">
          <label htmlFor="search">Title</label>
          <input type="text" id="search" name="search" />
        </div>
        <div className="flex flex-col">
          <label htmlFor="from">From</label>
          <input type="date" id="from" name="from" />
        </div>
        <div className="flex flex-col">
          <label htmlFor="to">To</label>
          <input type="date" id="to" name="to" />
        </div>
        <div className="flex flex-col">
          <label htmlFor="tag">Tag</label>
          <input type="text" id="tag" name="tag" />
        </div>
        <div className="pt-4">
          <button
            className="py-2 px-3 border rounded bg-blue-950 text-center w-full"
            type="submit"
          >
            Search
          </button>
        </div>
      </div>
    </form>
  );
}
