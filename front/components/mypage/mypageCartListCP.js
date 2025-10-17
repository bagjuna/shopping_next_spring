'use client'

import { useAuthCheck } from "@/hooks/useAuthCheck";
import { cartListFetcher } from "@/lib/cartListUtil";
import useSWR from "swr";
import CartItem from "./item/cartItem";

export default function MyPageCartListCP() {

    const {session} = useAuthCheck(true)
    const { data: cartItems, mutate } = useSWR(
        '/api/cart/list',
        cartListFetcher,
        { revalidateIfStale: false }
    );


    // Add the loading state
    const isLoading = !cartItems && session?.user?.email;
    if (isLoading) {
        return <div className="flex justify-center items-center h-screen">Loading cart items...</div>;
    }
    // Add the empty cart state
    if (cartItems && cartItems.length === 0) {
        return (
            <div className="flex justify-center items-center h-screen flex-col text-center p-4">
                <h2 className="text-2xl font-semibold mb-4">Your cart is empty.</h2>
                <p className="text-gray-600">Start shopping to add items to your cart!</p>
            </div>
        );
    }
    return (
        <div className="container mx-auto p-4">
            <div className="bg-white rounded-lg shadow-md p-6">
                <h2 className="text-2xl font-semibold mb-4">Cart ({cartItems?.length})</h2>
                <ul className="space-y-4">
                    {cartItems?.map(cartItem =>
                        <CartItem cartItem={cartItem} key={cartItem.cno} />
                    )}
                </ul>
            </div>
        </div>
    );
}
