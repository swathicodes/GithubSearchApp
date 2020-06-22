package com.swathi.githubapp.models

data class UserReposItem(
        val stargazers_count: Int,
        val name: String,
        val forks_count: Int,
        val updated_at: String,
        val description: String

)