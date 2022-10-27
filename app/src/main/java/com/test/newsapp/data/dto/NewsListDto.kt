package com.test.newsapp.data.dto

import com.google.gson.annotations.SerializedName
import com.test.newsapp.data.models.News
import com.test.newsapp.domain.utils.formatTo
import com.test.newsapp.domain.utils.toDate

data class NewsListDto(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>,

	@field:SerializedName("status")
	val status: String
) {
	fun toNewsList(): List<News> {
		return articles.map { article ->
			News(
				author = article.author ?: article.source.name,
				source = article.source.name,
				title = article.title,
				url = article.url,
				content = article.description ?: article.content ?: "N/A",
				banner = article.urlToImage ?: "",
				publishDate = article.publishedAt.replace("T", " ").replace("Z", "").toDate().formatTo("dd MMM yyyy")
			)
		}
	}
}

data class Source(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String? = null
)

data class ArticlesItem(

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("content")
	val content: String? = null
)
