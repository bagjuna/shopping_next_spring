import {useSession} from "next-auth/react";
import {useRouter} from "next/navigation";
import {useEffect} from "react";


export const useAuthCheck = (move) => {
    const {data: session, status} = useSession();
    const router = useRouter();

    useEffect(() => {
        if (status === 'unauthenticated') {
            if (move) {
                router.replace('/account/signin');
            }

        }

    }, [status, router]);

    return {session, router};

}