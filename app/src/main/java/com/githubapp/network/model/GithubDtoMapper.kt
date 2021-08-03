package com.githubapp.network.model

import com.githubapp.domain.util.DomainMapper
import com.githubapp.domain.model.GithubRepo

class GithubDtoMapper : DomainMapper<GithubDto, GithubRepo> {
    override fun mapToDomainModel(model: GithubDto): GithubRepo {
        return GithubRepo(
            name = model.name,
            full_name = model.full_name,
            private = model.private,
            html_url = model.html_url,
            description = model.description,
            download_url = model.download_url,
            language = model.language,
            stars = model.stars,
        )
    }

    override fun mapFromDomainModel(domainModel: GithubRepo): GithubDto {
        return GithubDto(
            name = domainModel.name,
            full_name = domainModel.full_name,
            private = domainModel.private,
            html_url = domainModel.html_url,
            description = domainModel.description,
            download_url = domainModel.download_url,
            language = domainModel.language,
            stars = domainModel.stars
        )
    }

    fun toDomainList(initial: List<GithubDto>): List<GithubRepo>{
        return initial.map {mapToDomainModel(it)}
    }
    fun fromDomainList(initial: List<GithubRepo>): List<GithubDto>{
        return initial.map {mapFromDomainModel(it)}
    }
}