package com.github.nasrat_v.yaba_android.Services.Provider

class ServerRoutesSingleton {
    companion object {
        private const val URL_SRV = "https://yaba-backend-heroku.herokuapp.com"
        //private const val URL_SRV = "http://192.168.1.101:8080"
        private const val URL_EBOOKS = "/ebooks/"
        private const val URL_IMAGES = "/ebooks/images/"
        private const val URL_AUTHORS = "/ebooks/authors/"

        const val ROUTE_BOOK = ("$URL_SRV/book/")
        const val ROUTE_BOOKS = ("$URL_SRV/books/")
        const val ROUTE_AUTHOR = ("$URL_SRV/author/")
        const val ROUTE_AUTHORS = ("$URL_SRV/authors/")
        const val ROUTE_SRV_EBOOKS = ("$URL_SRV$URL_EBOOKS")
        const val ROUTE_SRV_IMAGES = ("$URL_SRV$URL_IMAGES")
        const val ROUTE_SRV_AUTHORS = ("$URL_SRV$URL_AUTHORS")
    }
}