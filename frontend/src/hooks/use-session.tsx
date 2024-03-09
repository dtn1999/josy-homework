import { UserDetails, getMyProfile } from "@/utils/lib";
import { useRouter } from "next/navigation";
import React from "react";

export function useSession() {
  const router = useRouter();
  const [session, setSession] = React.useState<UserDetails | null>(null);
  const fetchSession = async () => {
    const response = await getMyProfile();
    if (response.success) {
      setSession(response?.data || null);
    } else {
      setSession(null);
      router.push("/auth/login");
    }
  };

  React.useEffect(() => {
    fetchSession();
  }, []);

  return session;
}
