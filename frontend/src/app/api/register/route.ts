import { NextResponse, NextRequest } from "next/server";

type ResponseData = {
  message: string;
  success?: boolean;
};

export async function POST(req: NextRequest) {
  // Do whatever you want
  const body = await req.json();
  console.log("login", "email", body.email, "password", body.password);
  return NextResponse.json({ message: "Hello World" }, { status: 200 });
}
