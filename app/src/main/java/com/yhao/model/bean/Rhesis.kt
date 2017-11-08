package com.yhao.model.bean

data class RhesisResult(val showapi_res_code: String,
                      val showapi_res_error: String,
                      val showapi_res_body: RhesisBody)

data class RhesisBody(val ret_code: String,
                    val ret_message: String,
                    val data: List<Rhesis>)

data class Rhesis(val english: String,
                val chinese: String)
