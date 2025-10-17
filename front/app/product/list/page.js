import Link from "next/link";

export default async function ProductListPage() {


    return (
        <div>

            <div>
                <div>Product List Page</div>
            </div>
            <ul>
                <li>
                    <Link href={'/product/view/2'}>2번 상품</Link>
                </li>
                <li>
                    <Link href={'/product/view/3'}>3번 상품</Link>
                </li>
            </ul>
        </div>
    )
}