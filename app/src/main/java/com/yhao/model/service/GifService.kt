package com.yhao.model.service

import com.google.gson.Gson
import com.yhao.commen.Const.Companion.buildUrl
import com.yhao.model.bean.Gif
import com.yhao.model.bean.GifResult
import java.net.URL



class GifService {
    companion object {
        private val baseUrl = "http://route.showapi.com/341-3"
        private fun buildBaseUrl(page: Int, maxResult: Int): String {
            return buildUrl("$baseUrl?page=$page&maxResult=$maxResult")
        }

        fun getData(page: Int, maxResult: Int = 5): List<Gif>? {
            var forecastJsonStr: String? = null
            try {
                forecastJsonStr = URL(buildBaseUrl(page, maxResult)).readText()
            } catch (e: Exception) {
                return null
            }
            val data = Gson().fromJson(forecastJsonStr, GifResult::class.java)
            val gifs: List<Gif> = data.showapi_res_body.contentlist
            return if (gifs.isNotEmpty()) gifs else null
        }
    }
}
