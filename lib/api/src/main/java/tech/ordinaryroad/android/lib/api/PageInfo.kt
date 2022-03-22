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

/**
 *
 *
 * @author mjz
 * @date 2022/1/26
 */
class PageInfo<T : Any> {
    var total: Int? = 0
    var list: ArrayList<T>? = arrayListOf()
    var pageNum: Int? = 0
    var pageSize: Int? = 0
    var size: Int? = 0
    var startRow: Int? = 0
    var endRow: Int? = 0
    var pages: Int? = 0
    var prePage: Int? = 0
    var nextPage: Int? = 0
    var isFirstPage: Boolean? = false
    var isLastPage: Boolean? = false
    var hasPreviousPage: Boolean? = false
    var hasNextPage: Boolean? = false
    var navigatePages: Int? = 0
    var navigatepageNums: List<Int>? = emptyList()
    var navigateFirstPage: Int? = 0
    var navigateLastPage: Int? = 0

    constructor()
}