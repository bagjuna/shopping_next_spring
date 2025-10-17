'use client'

import {useAuthCheck} from "@/hooks/useAuthCheck";
import {useState} from "react";
import AddCartModal from "@/components/product/view/addCardModal";
import {mutate} from "swr";

export default function AddCartButton({pno}) {

    const {session, router} = useAuthCheck();

    const [show, setShow] = useState(false);

    const handleClickAdd = async (e) => {
        const param = {
            account: session?.user?.email,
            pno: pno,
            quantity: 1
        }

        const res = await fetch(`/api/cart/change`, {
            method: 'POST',
            body: JSON.stringify(param),
            headers: {"Content-Type": "application/json"}
        })

        const result = await res.json();

        setShow(()=>true);

        mutate('/api/cart/list');
    }


    const closeModal = (value) => {
        setShow(() => false)
        if(value === 'c') {
            router.back()
        }else if(value === 'm'){
            router.push(`/mypage`)
        }
    }



    return (
        <div className="pt-4">
            {show && <AddCartModal closeModal={closeModal} />}
            {session?.user &&
                <button
                    onClick={handleClickAdd}
                    className="w-full px-8 py-3 bg-blue-600 text-white font-semibold rounded-lg shadow-md hover:bg-blue-700 transition-colors duration-200">
                    구매 하기
                </button>
            }
        </div>

    )

};