/** @type {import('next').NextConfig} */
const nextConfig = {
    images:{
        remotePatterns:[
            {
                protocol: 'http', // 이미지 프로토콜(http, https)
                hostname: 'localhost', // 이미지 호스트 이름 (IP 주소 또는 도메인)
                port: '8080', // 포트 번호 (필요한 경우)
                pathname: '/**' // 허용할 경로 패턴 (모든 경로 허용시 /**)
            }
        ]
    },
    experimental: {
        serverActions:{
            bodySizeLimit: '40mb' // 기본값은 4mb 원하는 크기로 설정(예: '10mb', '50mb' 등)
        }
    },
    async redirects(){
        return [
            {
                source: '/product',
                destination: '/product/catalog/1',
                permanent: true
            }
        ]
    },
    reactStrictMode: true,
};

export default nextConfig;
