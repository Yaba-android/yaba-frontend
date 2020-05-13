package com.github.nasrat_v.yaba_android.Services.Provider.Author

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.github.nasrat_v.yaba_android.ICallback.IAModelProviderCallback
import com.github.nasrat_v.yaba_android.Listable.Author.AModel
import com.github.nasrat_v.yaba_android.Services.Factory.Author.AModelFactory
import com.github.nasrat_v.yaba_android.Services.Provider.ServerRoutesSingleton
import com.github.nasrat_v.yaba_android.Services.Provider.VolleySingleton
import org.json.JSONArray
import org.json.JSONObject

class AModelProvider(private var context: Context, private var languageCode: String) {

    fun getAllAuthorsFromDatabase(callback: IAModelProviderCallback) {
        val url = (ServerRoutesSingleton.ROUTE_AUTHORS)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                callback.onGetAllAuthorsRequestSuccess(getInstancesFromJsonArray(response))
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

    fun getAuthorFromDatabase(remoteId: String, callback: IAModelProviderCallback) {
        val url = (ServerRoutesSingleton.ROUTE_AUTHOR + remoteId)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                callback.onGetAuthorRequestSuccess(getInstanceFromJsonObject(response))
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

    private fun getInstancesFromJsonArray(jsonArray: JSONArray): ArrayList<AModel> {
        val allAuthors = arrayListOf<AModel>()

        for (i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)
            allAuthors.add(
                getInstanceFromJsonObject(jsonObj)
            )
        }
        return allAuthors
    }

    private fun getInstanceFromJsonObject(jsonObject: JSONObject): AModel {
        return AModelFactory(context, languageCode)
            .getInstanceFromJsonObject(
                jsonObject
            )
    }
}