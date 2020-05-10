package com.github.nasrat_v.yaba_demo.Services.Provider.Book

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton

class EBookProvider(
    private var context: Context,
    private var bookPath: String
) {

    companion object {
        private const val DESC_REQUEST = "The book is downloading..."
    }

    fun startDownloading() {
        val url = (ServerRoutesSingleton.ROUTE_SRV_EBOOKS + bookPath)
        val request = DownloadManager.Request(Uri.parse(url))
        val location = Environment.DIRECTORY_DOWNLOADS

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(bookPath)
        request.setDescription(DESC_REQUEST)
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(location, bookPath)

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}