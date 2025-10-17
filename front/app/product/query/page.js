import ProductSearchCP from "@/components/product/productSearchFormCP";
import ProductQueryListCP from "@/components/product/productQueryListCP";

export default async function ProductQueryPage({params, searchParams}){

    const queryObj = await searchParams;

    const pageStr = queryObj.page ?? '1';
    const sizeStr = queryObj.size ?? '10';
    const sortStr = queryObj.sort ?? '';
    const keyWordStr = queryObj.keyword ?? '';

    const condition = new URLSearchParams({page: pageStr, size: sizeStr});

    if(sortStr) { condition.append('sort', sortStr) }
    if(keyWordStr) { condition.append('keyword', keyWordStr) }

    const res = await fetch(`http://localhost:8080/api/products/list?${condition.toString()}`,{
        method: 'GET',
        cache: 'no-cache',
    })

    const result = await res.json();

    console.log(result);

    return(
        <div>
            <div>Product Query Page</div>
            <ProductSearchCP />
            <ProductQueryListCP
            list={result.list}
            total={result.total}
            requestParam={result.pageRequestDTO}
            />
        </div>
    )
}