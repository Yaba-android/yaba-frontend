package com.github.nasrat_v.yaba_demo.Services.Provider.Book

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.github.nasrat_v.yaba_demo.ICallback.IBModelProviderCallback
import com.github.nasrat_v.yaba_demo.Listable.Author.AModel
import com.github.nasrat_v.yaba_demo.Services.Factory.Book.BModelFactory
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Factory.Author.AModelFactory
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton
import com.github.nasrat_v.yaba_demo.Services.Provider.VolleySingleton
import org.json.JSONArray
import org.json.JSONObject

class BModelProvider(private var context: Context, private var languageCode: String) {

    fun getAllBooksFromResource(): ArrayList<BModel> {
        val allBooks = arrayListOf<BModel>()

        for (index in 0 until getResourceSize()) {
            allBooks.add(
                BModelFactory(context, languageCode)
                    .getInstance(
                        index
                    )
            )
        }
        return allBooks
    }

    fun getAllBooksFromDatabase(callback: IBModelProviderCallback) {
        val url = (ServerRoutesSingleton.ROUTE_BOOKS)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                callback.onGetAllBooksRequestSuccess(getInstancesFromJsonArray(response))
            },
            Response.ErrorListener { error ->
                Log.d("errorhttprequest", error.toString())
            }
        )
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest)
    }

    fun getBookFromDatabase(remoteId: String, callback: IBModelProviderCallback) {
        val url = (ServerRoutesSingleton.ROUTE_BOOK + remoteId)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                callback.onGetBookRequestSuccess(getInstanceFromJsonObject(response))
            },
            Response.ErrorListener { error ->
                Log.d("errorhttprequest", error.toString())
            }
        )
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

    private fun getInstancesFromJsonArray(jsonArray: JSONArray): ArrayList<BModel> {
        val allBooks = arrayListOf<BModel>()

        for (i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)
            allBooks.add(
                getInstanceFromJsonObject(jsonObj)
            )
        }
        return allBooks
    }

    private fun getInstanceFromJsonObject(jsonObject: JSONObject): BModel {
        return BModelFactory(context, languageCode)
            .getInstanceFromJsonObject(
                jsonObject
            )
    }

    private fun getResourceSize(): Int {
        return context.resources.getIntArray(R.array.db_size).first()
    }
}