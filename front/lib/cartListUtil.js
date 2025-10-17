export const cartListFetcher = async () => {

    const res = await fetch('/api/cart/list')

    const result = await res.json();

    return result;
}
