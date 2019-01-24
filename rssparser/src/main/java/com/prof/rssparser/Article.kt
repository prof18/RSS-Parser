/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof.rssparser

data class Article(
        var title: String? = null,
        var author: String? = null,
        var link: String? = null,
        var pubDate: String? = null,
        var description: String? = null,
        var content: String? = null,
        var image: String? = null,
        private var _categories: MutableList<String> = mutableListOf()
) {

    val categories: MutableList<String>
        get() = _categories

    fun addCategory(category: String) {
        _categories.add(category)
    }
}