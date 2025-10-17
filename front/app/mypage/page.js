import {authOptions} from "@/app/api/auth/[...nextauth]/route";
import MyPageCartListCP from "@/components/mypage/mypageCartListCP";

export default async function MyPage() {


    return (
        <div>
            <div>My Page</div>
            <div>
                <MyPageCartListCP/>
            </div>
        </div>
    )
}