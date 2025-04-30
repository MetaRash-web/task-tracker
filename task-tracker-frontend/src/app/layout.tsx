import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { AuthProvider } from "@/features/auth/AuthProvider";
import { Toaster } from 'react-hot-toast';
import { TasksProvider } from "@/features/tasks/TasksProvider";
import { useNotificationSocket } from "@/features/websocket/hooks/useNotificationSocket";
import { NotificationProvider } from "@/features/websocket/NotificationProvider";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Task tracker",
  description: "Sidim s bobrom za stolom",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
      <TasksProvider>
        <AuthProvider>
          <NotificationProvider>
            <Toaster position="top-right" />
            {children}
          </NotificationProvider>
        </AuthProvider>
      </TasksProvider>
      </body>
    </html>
  );
}
