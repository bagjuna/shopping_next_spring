'use client'

import { useAuthCheck } from "@/hooks/useAuthCheck"
import Image from "next/image"
import { mutate } from "swr"

export default function CartItem({cartItem}) {

    const {session} = useAuthCheck()

    const handleClickQty = async (amount) => {

        const param = {
            account: session.user.email,
            pno: cartItem.pno,
            quantity: amount
        }
        const res = await fetch("/api/cart/change", {
            method: 'POST',
            body: JSON.stringify(param),
            headers: { "Content-Type": "application/json" }
        })

        const result = await res.json()

        console.log(result)

        mutate('/api/cart/list')

    }



    return(
        <li className="flex flex-col sm:flex-row items-center p-4 bg-white border border-gray-200 rounded-lg shadow-sm m-2">
            <div className="flex-shrink-0 mr-4">
                <Image
                    src={`http://localhost:8080/s_${cartItem.fileName}`}
                    alt={cartItem.pname}
                    width={100}
                    height={100}
                    className="rounded-md object-cover"
                    priority
                />
            </div>
            <div className="flex-grow text-center sm:text-left">
                <h3 className="text-lg font-semibold text-gray-800">{cartItem.pname}</h3>
                <p className="text-sm text-gray-600">Price: {cartItem.price.toLocaleString()}원</p>
                <p className="text-sm text-gray-600">Quantity: {cartItem.quantity}</p>
                <p className="text-sm text-gray-400">PNO: {cartItem.pno}</p>
            </div>
            <div className="flex items-center space-x-2 mt-4 sm:mt-0">
                <button
                    onClick={() => handleClickQty(1)}
                    className="w-8 h-8 flex items-center justify-center text-white bg-blue-500 rounded-full hover:bg-blue-600 transition-colors duration-200"
                >
                    <span className="text-xl leading-none">+</span>
                </button>
                <span className="text-lg font-medium">{cartItem.quantity}</span>
                <button
                    onClick={() => handleClickQty(-1)}
                    className="w-8 h-8 flex items-center justify-center text-white bg-red-500 rounded-full hover:bg-red-600 transition-colors duration-200"
                >
                    <span className="text-xl leading-none">-</span>
                </button>
            </div>
        </li>

    )
}
