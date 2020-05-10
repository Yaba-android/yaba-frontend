package com.github.nasrat_v.yaba_demo.Services.Provider

class ServerRoutesSingleton {
    companion object {
        private const val URL_SRV = "http://192.168.1.101:8080"
        private const val URL_IMAGES = "/ebooks/images/"
        private const val URL_EBOOKS = "/ebooks/"

        const val ROUTE_GET_ALL_BOOKS = ("$URL_SRV/getAllBooks")
        const val ROUTE_SRV_IMAGES = ("$URL_SRV$URL_IMAGES")
        const val ROUTE_SRV_EBOOKS = ("$URL_SRV$URL_EBOOKS")
    }
}