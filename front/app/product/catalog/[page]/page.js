import ProductCatalogCP from "@/components/product/productCatalogCP";

export default async function ProductCatalogPage({params, searchParams}) {
    const param = await params;

    const pageStr = param.page || '1'
    const sizeStr = '4'

    const res = await fetch(`http://localhost:8080/api/products/list?page=${pageStr}&size=${sizeStr}`,
        {next:
                {revalidate: 60}
        }
    )

    const result = await res.json();

    console.log(result);

    const {list, total, pageRequestDTO} = result

    return (
        <div>
            <div>Product Catalog Page {pageStr}</div>
            <ProductCatalogCP
                products={list}
                total={total}
                current={pageRequestDTO.page}
                size={pageRequestDTO.size}
            />

        </div>
    )

}