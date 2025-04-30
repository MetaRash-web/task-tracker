import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    eslint: {
        ignoreDuringBuilds: true,
    },
    async rewrites() {
        return [
            {
                source: '/api/:path*',
                destination: 'http://backend:8080/:path*',
            },
            {
                source: '/ws/:path*',
                destination: 'http://backend:8080/ws/:path*',
            },
        ];
    }
    
};


export default nextConfig;
