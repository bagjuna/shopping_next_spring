'use client'

import {useActionState, useEffect, useState} from "react"
import {createAccount, putAccount} from "@/actions/accountActions";
import {useRouter} from "next/navigation";

export default function AccountSignupCP() {

    const [password, setPassword] = useState('')
    const [nickname, setNickname] = useState('')
    const [email, setEmail] = useState('')

    const [state, action, isPending] = useActionState(createAccount, { message: '', result: '' });


    const router = useRouter()


    useEffect(() => {
        if (state.result === 'success') {
            alert('회원가입이 성공적으로 완료되었습니다. 로그인 페이지로 이동합니다.');
            router.push('/account/signin');
        }
        else if (state.message) {
            // 실패 메시지가 있을 경우 사용자에게 보여줍니다.
            setError(state.message);
        }
    }, [state]);

    if (status === 'loading') {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <div>Loading...</div>
            </div>
        );
    }



    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
            <div className="w-full max-w-sm bg-white p-8 rounded-lg shadow-md">
                <h1 className="text-2xl font-bold text-center text-gray-800 mb-6">회원 가입</h1>
                <form action={action} className="space-y-4">
                    <div className="mb-4">
                        <label htmlFor="nickname" className="block text-sm font-medium text-gray-700 mb-1">이름</label>
                        <input
                            type="text"
                            id="nickname"
                            name="nickname"
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="이름"
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">이메일</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="email"
                        />
                    </div>
                    <div className="mb-6">
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">비밀번호</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="********"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white font-semibold py-2 rounded-md hover:bg-blue-700 transition-colors"
                    >
                        회원 가입
                    </button>
                </form>
            </div>
        </div>
    )
};