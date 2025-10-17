import ProductViewCP from "@/components/product/productViewCP";

export async function generateStaticParams() {
    const arr = [{pno: '1'}, {pno: '2'}, {pno: '3'}];

    return arr;
}

export default async function ProductViewPage({params, searchParams}) {
    const param = await params;

    const pno = param.pno;
    const query = await searchParams


    const res = await fetch(`http://localhost:8080/api/products/${pno}`, {next: {revalidate: 120}})

    // ISR(Incremental Static Regeneration) : 0초마다 새로고침
    const product = await res.json();

    console.log(product);

    const from = query.from ? decodeURIComponent(query.from) : '/product/catalog/1'
    console.log("from:", from);
    return (
        <div>
            <div>Product View Page</div>
            <ProductViewCP product={product} from={from}></ProductViewCP>

        </div>
    )
};