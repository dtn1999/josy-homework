import type { NextApiRequest, NextApiResponse } from "next";

type ResponseData = {
  message: string;
  success?: boolean;
};

export default function handler(
  req: NextApiRequest,
  res: NextApiResponse<ResponseData>
) {
  const { email, password } = req.body;
  console.log("password forgotten", "email", email, "password", password);
  res.status(200).json({ message: "Hello from Next.js!" });
}
