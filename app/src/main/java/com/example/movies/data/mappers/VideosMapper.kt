package com.example.movies.data.mappers

import com.example.movies.data.network.model.VideosDto
import com.example.movies.domain.Videos

object VideosMapper {
    fun mapDtoToEntity(videosDto: VideosDto): Videos {
        return Videos(
            iso6391 = videosDto.iso6391,
            iso31661 = videosDto.iso31661,
            name = videosDto.name,
            key = videosDto.key,
            site = videosDto.site,
            size = videosDto.size,
            type = videosDto.type,
            official = videosDto.official,
            publishedAt = videosDto.publishedAt,
            id = videosDto.id
        )
    }
}