import {getServerSession} from "next-auth";
import {authOptions} from "@/app/api/auth/[...nextauth]/route";
import MyPageCartListCP from "@/components/mypage/mypageCartListCP";

export default async function MyPage() {
    const session = await getServerSession(authOptions)

    console.log(session);
    console.log(session?.user);

    return (
        <div>
            <div>My Page</div>
            <div>

           <MyPageCartListCP />
            </div>
        </div>
    )
}