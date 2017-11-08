package com.yhao.commen.download


interface ProgressListener {
    fun onProgress(readByte: Long, totalByte: Long, done: Boolean)

    fun onSave(filePath: String)
}
