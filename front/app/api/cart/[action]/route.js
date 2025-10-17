import {getServerSession} from "next-auth";
import {authOptions} from "@/app/api/auth/[...nextauth]/route";
import {NextResponse} from "next/server";


export async function POST(request, {params}) {

    const session = await getServerSession(authOptions);

    const paramObj = await params;

    const {action} = paramObj;

    if (!session) {
        return new NextResponse(JSON.stringify({message: "Authentication required"}), {
            status: 401,
            headers: {'Content-Type': 'application/json'},
        });
    }

    const {account, pno, quantity} = await request.json();

    if (action === 'change') {
        const res = await fetch("http://localhost:8080/api/carts/change", {
            method: 'POST',
            body: JSON.stringify({account, pno, quantity}),
            headers: {"Content-Type": "application/json"}
        })

        const result = await res.json();

        return NextResponse.json(result);
    }

}

export async function GET(request, {params}) {


    const paramObj = await params;

    const { action } = paramObj;


    if (action === 'list') {

        const session = await getServerSession(authOptions);

        if (!session) {
            return new NextResponse(JSON.stringify({message: "Authentication required"}), {
                status: 401,
                headers: {'Content-Type': 'application/json'},
            });
        }

        const account = session.user.email;
        console.log('account:', account);

        const res = await fetch(`http://localhost:8080/api/carts/list?account=${account}`, {
            method: 'GET',
        })

        const result = await res.json();
        console.log(result);
        return NextResponse.json(result);
    }


}