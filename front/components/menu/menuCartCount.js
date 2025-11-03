'use client'

import {cartListFetcher} from "@/lib/cartListUtil";
import useSWR from "swr";
import Link from "next/link";
import {useAuthCheck} from "@/hooks/useAuthCheck";




export default function MenuCartCount() {

    // auth
    const {session} = useAuthCheck()


    const {data: cartItems, isLoading} = useSWR(
        ['/api/cart/list',session],
        cartListFetcher,
        {revalidateIfStale: false});


    if (isLoading) {

        return (
            <div>Loading....</div>
        )

    }

    return (
        <Link
            href="/mypage"
            className="cursor-pointer hover:text-blue-600 transition duration-300 p-2" // 스타일 추가 (선택 사항)
        >
            <div>
                나의 상품: {cartItems?.length}
            </div>
        </Link>
    )
}
