package com.githubapp.network.model

import com.githubapp.domain.util.DomainMapper
import com.githubapp.domain.model.GithubRepo

class GithubDtoMapper : DomainMapper<GithubDto, GithubRepo> {
    override fun mapToDomainModel(model: GithubDto): GithubRepo {
        return GithubRepo(
            name = model.name,
            html_url = model.html_url,
            description = model.description,
            language = model.language,
            stars = model.stars,
        )
    }

    override fun mapFromDomainModel(domainModel: GithubRepo): GithubDto {
        return GithubDto(
            name = domainModel.name,
            html_url = domainModel.html_url,
            description = domainModel.description,
            language = domainModel.language,
            stars = domainModel.stars
        )
    }

    fun toDomainList(initial: List<GithubDto>): List<GithubRepo>{
        return initial.map {mapToDomainModel(it)}
    }
}