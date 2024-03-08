import { NavBar } from "@/components/nav-bar";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <NavBar />
        <>{children}</>
      </body>
    </html>
  );
}
