package com.example.trendingrepo.model

import com.example.trendingrepo.base.common.Cell
import com.squareup.moshi.Json

/**
 * {
"author": "seaswalker",
"name": "spring-analysis",
"avatar": "https://github.com/seaswalker.png",
"url": "https://github.com/seaswalker/spring-analysis",
"description": "Spring源码阅读",
"language": "Java",
"languageColor": "#b07219",
"stars": 4595,
"forks": 1722,
"currentPeriodStars": 39,
"builtBy": [
{
"username": "seaswalker",
"href": "https://github.com/seaswalker",
"avatar": "https://avatars3.githubusercontent.com/u/12082227"
},
{
"username": "qqq19923",
"href": "https://github.com/qqq19923",
"avatar": "https://avatars0.githubusercontent.com/u/39252366"
}
]
}
 */
data class TrendingResponse(
    @Json(name = "author") val author: String,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: String,
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String,
    @Json(name = "language") val language: String,
    @Json(name = "languageColor") val languageColor: String,
    @Json(name = "stars") val stars: Int,
    @Json(name = "forks") val forks: Int,
    @Json(name = "currentPeriodStars") val currentPeriodStars: Int,
    @Json(name = "builtBy") val builtBy: List<BuiltBy>
):Cell

data class BuiltBy(
    @Json(name = "username") val username: String,
    @Json(name = "href") val href: String,
    @Json(name = "avatar") val avatar: String
)