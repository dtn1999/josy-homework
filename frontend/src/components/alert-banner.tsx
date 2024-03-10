interface Props {
  error: string | null;
  success: string | null;
}
export function AlertBanner({ error, success }: Props) {
  if (!error && !success) return null;
  if (error && success) return null;
  if (error && !success) {
    return (
      <span className="py-2 px-3 flex justify-center items-center bg-red-500">
        {error}
      </span>
    );
  }
  if (success && !error) {
    return (
      <span className="py-2 px-3 flex justify-center items-center bg-green-500">
        {success}
      </span>
    );
  }
}
