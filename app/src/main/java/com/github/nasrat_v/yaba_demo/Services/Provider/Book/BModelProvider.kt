package com.github.nasrat_v.yaba_demo.Services.Provider.Book

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.github.nasrat_v.yaba_demo.ICallback.IBModelProviderCallback
import com.github.nasrat_v.yaba_demo.Services.Factory.Book.BModelFactory
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.VolleySingleton
import org.json.JSONArray

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
        val url = "http://192.168.1.101:8080/getAllBooks"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("httpgetrequest", response.toString())
                callback.onGetAllBooksRequestSuccess(getInstancesFromJsonArray(response))
            },
            Response.ErrorListener { error ->
                Log.d("errorhttpgetrequest", error.toString())
            }
        )
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest)
    }

    private fun getInstancesFromJsonArray(jsonArray: JSONArray): ArrayList<BModel> {
        val allBooks = arrayListOf<BModel>()

        for (i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)
            allBooks.add(
                BModelFactory(context, languageCode)
                    .getInstanceFromJsonObject(
                        jsonObj
                    )
            )
        }
        return allBooks
    }

    private fun getResourceSize(): Int {
        return context.resources.getIntArray(R.array.db_size).first()
    }
}