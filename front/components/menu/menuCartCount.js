'use client'

import { cartListFetcher } from "@/lib/cartListUtil";
import useSWR from "swr";


export default function MenuCartCount() {

    const { data: cartItems, isLoading } = useSWR(
        '/api/cart/list',
        cartListFetcher,
        { revalidateIfStale: false });


    if(isLoading) {

        return (
            <div>Loading....</div>
        )

    }

    return(
        <div>
            CART ITEMS: {cartItems.length}
        </div>
    )
}
