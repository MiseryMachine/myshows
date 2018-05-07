package com.rjs.myshows.services.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.rjs.myshows.common.domain.showTypeNames
import com.rjs.myshows.services.config.security.WebSecurityConfig
import com.rjs.myshows.services.domain.ShowEntity
import com.rjs.myshows.services.domain.ShowTypeEntity
import com.rjs.myshows.services.domain.UserEntity
import com.rjs.myshows.services.domain.UserShowFilterEntity
import com.rjs.myshows.services.service.ShowService
import com.rjs.myshows.services.service.ShowTypeService
import com.rjs.myshows.services.service.UserService
import com.rjs.myshows.services.service.UserShowFilterService
import org.apache.commons.lang3.StringUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.FileSystemResourceLoader
import java.util.logging.Logger

@Profile("development", "test")
@Configuration
class InitDataConfig(
    val webSecurityConfig: WebSecurityConfig,
    val objectMapper: ObjectMapper,
    val showTypeService: ShowTypeService,
    val showService: ShowService,
    val userService: UserService,
    val userShowFilterService: UserShowFilterService
) {
    private val logger = Logger.getLogger(InitDataConfig::class.java.name)

    @Bean
    fun initData(): String {
        val resourceLoader = FileSystemResourceLoader()
        val dataResource = resourceLoader.getResource("classpath:show-data.json")

        val jsonNode = objectMapper.readTree(dataResource.inputStream)

        if (jsonNode.has("users")) {
            var jsonString = jsonNode.get("users").toString()
            val users = objectMapper.readValue(jsonString, Array<UserEntity>::class.java)
            val rootUser = initUsers(users)

            if (rootUser != null && jsonNode.has("userFilters")) {
                jsonString = jsonNode.get("userFilters").toString()
                val filters = objectMapper.readValue(jsonString, Array<UserShowFilterEntity>::class.java)
                filters.forEach { f -> f.user = rootUser }

                userShowFilterService.saveAll(filters.toList())
            }
        }

        if (jsonNode.has("showTypes")) {
            val jsonString = jsonNode.get("showTypes").toString()
            initShowTypes(objectMapper.readValue(jsonString, Array<ShowTypeEntity>::class.java))
        }

        if (jsonNode.has("shows")) {
            val jsonString = jsonNode.get("shows").toString()
            initShows(objectMapper.readValue(jsonString, Array<ShowEntity>::class.java))
        }

        return "data initialized"
    }

    private fun initUsers(users: Array<UserEntity>): UserEntity? {
        for (user in users) {
            if (userService.findByUsername(user.username) == null) {
                val pw = user.password

                if (StringUtils.isNotBlank(pw)) {
                    user.password = webSecurityConfig.encode(pw)
                }

                userService.save(user)
                logger.info("User created [${user.username}].")
            }
            else {
                logger.info("User found [${user.username}].")
            }
        }

        return userService.findById(1)
    }

    private fun initShowTypes(showTypes: Array<ShowTypeEntity>) {
        for (showTypeName in showTypeNames) {
            val showType = ShowTypeEntity()
            showType.name = showTypeName
            showTypeService.save(showType)
            logger.info("Show type created [${showType.name}].")
        }

        for (showType in showTypes) {
            val existingShowType = showTypeService.findByName(showType.name)
            val curShowType = existingShowType ?: showTypeService.save(showType)

            if (existingShowType == null) {
                logger.info("Show type created [${showType.name}].")
            }
            else {
                logger.info("Show type found [${showType.name}].")
            }

            var genreAdded = false

            for (genre in showType.genres) {
                if (!curShowType.genres.contains(genre)) {
                    curShowType.genres.add(genre)
                    genreAdded = true
                }
            }

            if (genreAdded) {
                showTypeService.save(curShowType)
            }
        }
    }

    private fun initShows(shows: Array<ShowEntity>) {
        for (show in shows) {
            val curShow = showService.findByTitleExact(show.title)

            if (curShow.isEmpty()) {
                showService.save(show)
                logger.info("Show created [${show.title}].")
            }
            else {
                logger.info("At least one show with title found [${show.title}].")
            }
        }
    }
}