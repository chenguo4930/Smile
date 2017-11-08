package com.yhao.commen.util

import java.io.File


class FileUtil {
    companion object {

        @Throws(Exception::class)
        fun getFolderSize(file: File): Long {
            var size: Long = 0
            try {
                val fileList = file.listFiles()
                for (aFileList in fileList) {
                    if (aFileList.isDirectory) {
                        size += getFolderSize(aFileList)
                    } else {
                        size += aFileList.length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return size
        }

        interface DeleteListener {
            fun onDelete(size: Long)
            fun onDone()
        }

        /**
         * 删除缓冲文件
         */
        fun deleteFolderFile(deleteListener: DeleteListener, vararg dirs: File) {
            for (dir in dirs) {
                try {
                    val fileList = dir.listFiles()
                    for (aFileList in fileList!!) {
                        if (aFileList.isDirectory) {
                            deleteFolderFile(deleteListener, aFileList)
                        } else {
                            deleteListener.onDelete(deleteFileS(aFileList))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            deleteListener.onDone()
        }

        private fun deleteFileS(file: File): Long {
            val size = file.length()
            file.delete()
            return size
        }

        fun getPrintSize(s: Long): String {
            var size = s.toFloat()
            if (size < 1024) {
                return save2float(size) + "B" + ")"
            } else {
                size /= 1024
            }
            if (size < 1024) {
                return save2float(size) + "K" + ")"
            } else {
                size /= 1024
            }
            if (size < 1024) {
                return save2float(size) + "M" + ")"
            } else {
                size /= 1024
            }
            return save2float(size) + "G" + ")"
        }

        private fun save2float(n: Float): String {
            return "(" + (Math.round(n * 100).toFloat() / 100).toString()
        }

    }
}