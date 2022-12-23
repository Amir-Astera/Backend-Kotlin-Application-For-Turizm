package dev.december.jeterbackend.shared.features.articles.domain.models

import org.springframework.data.domain.Sort

enum class ArticleSortField(private val sortField: String) {
    PRIORITY("priority"),
    CREATED_AT("createdAt");

    fun getSortField(sortDirection: ArticleSortDirection): Sort {
        return if (sortDirection == ArticleSortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }

}