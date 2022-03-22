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

import com.google.gson.Gson

/**
 * OR响应体
 *
 * @author mjz
 * @date 2022/1/24
 */
class OrResponse<T : Any> {

    var code: Int? = 200
    var data: T? = null
    var msg: String? = "成功"
    var details: String? = null
    var success: Boolean? = true

    constructor()
    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }

    companion object {
        fun <T : Any> success(data: T?) = OrResponse<T>().apply {
            this.success = true
            this.data = data
        }

        fun <T : Any> error() = OrResponse<T>().apply {
            this.success = false
        }

        fun parse(json: String?): OrResponse<*> {
            return if (json == null) {
                OrResponse<Nothing>(code = 999, msg = "失败")
            } else {
                Gson().fromJson(json, OrResponse::class.java)
            }
        }
    }
}