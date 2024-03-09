import { UserDetails, getMyProfile } from "@/utils/lib";
import React from "react";

export function useSession() {
  const [session, setSession] = React.useState<UserDetails | null>(null);
  const fetchSession = async () => {
    const response = await getMyProfile();
    if (response.success) {
      setSession(response?.data || null);
    } else {
      setSession(null);
    }
  };

  React.useEffect(() => {
    fetchSession();
  }, []);

  return session;
}
