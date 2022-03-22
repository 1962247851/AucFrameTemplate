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

package tech.ordinaryroad.android.lib.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * OR Api响应回调
 * @param T data字段的类型
 * @author mjz
 * @date 2022/1/24
 */
open class OrHttpCallback<T : Any>(private val callback: OrCallback<T>) : Callback<OrResponse<T>> {

    override fun onResponse(call: Call<OrResponse<T>>, response: Response<OrResponse<T>>) {
        if (response.body() == null) {
            callback.onFailure("response.body() == null")
        } else {
            response.body()!!.apply {
                if (success == true) {
                    callback.onResponseWithNullableData(data)
                    if (data != null) {
                        callback.onResponse(data!!)
                    }
                } else {
                    callback.onFailure(msg ?: "失败")
                }
            }
        }
    }

    override fun onFailure(call: Call<OrResponse<T>>, t: Throwable) {
        t.printStackTrace()
        callback.onFailure(t.localizedMessage ?: "失败")
    }
}