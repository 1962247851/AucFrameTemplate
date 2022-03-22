/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.android.lib.util

import android.app.Activity
import android.net.Uri
import com.blankj.utilcode.util.NetworkUtils
import tech.ordinaryroad.android.lib.api.OrCallback
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern


/**
 *
 *
 * @author mjz
 * @date 2022/2/10
 */
object OrUtils {

    const val REGEX_URL = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"

    /**
     * 验证并获取所有URL地址
     *
     *
     * IP地址、去掉前后汉字、带参数
     *
     * @param string 要验证的文本
     * @return urlList
     */
    fun getURLs(string: String?): List<String> {
        string == null && return arrayListOf()
        val urlList = ArrayList<String>()
        val matcher = Pattern.compile(REGEX_URL).matcher(string)
        while (matcher.find()) {
            urlList.add(matcher.group())
        }
        return urlList
    }

    /**
     * 根据payload解析出网络类型，并转化为subtitle的string
     */
    fun parseNetWorkTypeInfo(payload: String): String {
        return payload.substringAfter("我正在使用的网络为：") + " - 在线"
    }

    /**
     * 发送ONLINE、PING、PONG在线消息时加上正在使用的网络类型
     */
    fun addNetWordTypeInfo(): String {
        return "我正在使用的网络为：" + when (NetworkUtils.getNetworkType()) {
            NetworkUtils.NetworkType.NETWORK_2G -> "2G"
            NetworkUtils.NetworkType.NETWORK_3G -> "3G"
            NetworkUtils.NetworkType.NETWORK_4G -> "4G"
            NetworkUtils.NetworkType.NETWORK_5G -> "5G"
            NetworkUtils.NetworkType.NETWORK_WIFI -> "WIFI"
            NetworkUtils.NetworkType.NETWORK_ETHERNET -> "以太网"
            else -> "未知网络"
        }
    }

}