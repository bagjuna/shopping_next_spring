'use client'

import Link from "next/link";
import {useAuthCheck} from "@/hooks/useAuthCheck";
import MenuCartCount from "@/components/menu/menuCartCount";

export default function MainHeader() {
    // 클라이언트 사이드에서 작동해야 하는 훅 사용
    const {session} = useAuthCheck()

    return (
        <header className="fixed top-0 left-0 w-full bg-white shadow-md z-50">
            <div className="container mx-auto flex justify-between items-center py-4 px-6">
                <div className="text-xl font-bold">
                    <Link href="/">로고</Link>
                </div>
                <nav className="hidden md:flex items-center space-x-6">
                    <Link href="/account/modify" className="text-gray-700 hover:text-blue-600 font-medium">마이페이지</Link>
                    <Link href="/product" className="text-gray-700 hover:text-blue-600 font-medium">상품 카탈로그</Link>
                    {!session &&
                        <Link href="/account/signin" className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition duration-300">로그인</Link>
                    }
                    {session &&
                        <>
                            <MenuCartCount/>
                            <Link href="/account/signout" className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition duration-300">로그아웃</Link>
                        </>
                    }
                </nav>
            </div>
        </header>
    )
}