'use client'

import { putAccount } from "@/actions/accountActions";
import { useSession, signOut } from "next-auth/react";
import {useActionState, useEffect, useState} from "react";
import {useRouter} from "next/navigation";


export default function AccountModifyCP() {
    const { data, status, update } = useSession();
    const [state, action, isPending] = useActionState(putAccount, { message: '', result: '' });

    const router = useRouter()



    useEffect(() => {
        if (state.result === 'success') {
            alert('정보가 성공적으로 변경되었습니다. 보안을 위해 다시 로그인해주세요.');
            // update() 함수로 세션을 갱신할 수도 있지만,
            // 비밀번호 등 민감 정보 변경 시에는 재로그인이 더 안전합니다.
            signOut({ callbackUrl: '/' });
        } else if (state.message) {
            // 실패 메시지가 있을 경우 사용자에게 보여줍니다.
            alert(state.message);
        }
    }, [state]);

    if (status === 'loading') {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <div>Loading...</div>
            </div>
        );
    }

    // 세션 데이터가 없을 경우를 대비한 방어 코드
    if (!data?.user) {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <div>세션 정보를 불러올 수 없습니다. 다시 로그인해주세요.</div>
            </div>
        )
    }

    const { email, name } = data.user;

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-50 p-4">
            <div className="w-full max-w-md bg-white p-8 rounded-xl shadow-lg">
                <h1 className="text-2xl font-bold text-center text-gray-800 mb-2">회원 정보 수정</h1>
                <p className="text-center text-gray-500 mb-8">닉네임 또는 비밀번호를 변경할 수 있습니다.</p>

                <form action={action} className="space-y-6">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                            이메일
                        </label>
                        <input
                            id="email"
                            type="text"
                            name="email"
                            defaultValue={email}
                            readOnly
                            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm bg-gray-100 cursor-not-allowed text-gray-500 focus:outline-none"
                        />
                    </div>

                    <div>
                        <label htmlFor="nickname" className="block text-sm font-medium text-gray-700 mb-1">
                            닉네임
                        </label>
                        <input
                            id="nickname"
                            type="text"
                            name="nickname"
                            defaultValue={name}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>

                    {/* 파일 업로드 입력 필드 */}
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Files:</label>
                        <input
                            type="file"
                            name='files'
                            multiple
                            className="mt-1 block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={isPending}
                        className="w-full flex justify-center py-3 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:bg-gray-400 disabled:cursor-not-allowed"
                    >
                        {isPending ? '저장 중...' : '변경 내용 저장'}
                    </button>
                </form>
            </div>
        </div>
    );
}