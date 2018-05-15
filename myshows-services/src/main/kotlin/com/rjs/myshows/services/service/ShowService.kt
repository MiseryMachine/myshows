package com.rjs.myshows.services.service

import com.rjs.myshows.common.domain.ShowFilterDto
import com.rjs.myshows.common.util.createThumbImage
import com.rjs.myshows.common.util.jpegImage
import com.rjs.myshows.common.util.pngImage
import com.rjs.myshows.services.config.AppConfig
import com.rjs.myshows.services.domain.ShowEntity
import com.rjs.myshows.services.domain.UserShowFilterEntity
import com.rjs.myshows.services.repository.ShowRepository
import com.rjs.myshows.services.repository.filter.*
import com.rjs.myshows.services.repository.specification.DataSpecification
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

@Service("showService")
@Transactional
class ShowService(
    private val appConfig: AppConfig,
    private val showRepository: ShowRepository,
    private val defaultBoxArt: File?,
    private val defaultBoxArtThumb: File?
): BaseService<ShowEntity, ShowRepository>(showRepository) {
    private val defaultSort = Sort.by(
        Sort.Order(Sort.Direction.DESC, "starRating"),
        Sort.Order(Sort.Direction.ASC, "title")
    )

    fun findByMdbId(mdbId: String): ShowEntity? = showRepository.findByMdbId(mdbId)
    fun findByTitleExact(title: String): MutableList<ShowEntity> = showRepository.findByTitle(title)

    fun searchShows(showFilter: ShowFilterDto): List<ShowEntity> {
        val paramMap: MutableMap<String, Any> = hashMapOf()
//        paramMap["showTypeName"] = showFilter.showTypeName
        paramMap["title"] = showFilter.title
        paramMap["starRating"] = showFilter.starRating
        paramMap["format"] = showFilter.format
        paramMap["genres"] = showFilter.genres

        return searchShows(showFilter.showTypeName, paramMap, defaultSort)
    }

    fun searchShows(showTypeName: String, filter: Map<String, Any?>?): List<ShowEntity> = searchShows(showTypeName, filter, null)

    fun searchShows(showTypeName: String, filter: Map<String, Any?>?, sort: Sort?): List<ShowEntity> {
        if (StringUtils.isNoneBlank(showTypeName)) {
            val spec: Specification<ShowEntity>? = buildShowSpecification(showTypeName, filter)

            if (spec != null) {
                return if (sort != null) showRepository.findAll(spec, sort) else showRepository.findAll(spec)
            }
        }

        return emptyList()
    }

    fun getShowDto(userShowFilter: UserShowFilterEntity): ShowFilterDto {
        return ShowFilterDto(
            userShowFilter.showTypeName,
            userShowFilter.title,
            userShowFilter.starRating,
            userShowFilter.format,
            userShowFilter.genres
        )
    }

    fun getShowPosterData(showId: Long?, thumb: Boolean): ByteArray {
        if (showId == null) {
            return ByteArray(0)
        }

        val localPosterPath = appConfig.localImagePath(showId.toString())
        var ext = ".png"
        var posterFile = File(localPosterPath, "poster$ext")
        var imageType = pngImage

        if (!posterFile.exists()) {
            ext = ".jpg"
            posterFile = File(localPosterPath, "poster$ext")
            imageType = jpegImage
        }

        if (!posterFile.exists()) {
            ext = ".jpeg"
            posterFile = File(localPosterPath, "poster$ext")
            imageType = jpegImage
        }

        var boxArtFile: File? = null

        if (thumb) {
            if (posterFile.exists()) {
                boxArtFile = File(localPosterPath, "poster_thumb$ext")

                if (!boxArtFile.exists()) {
                    val thumbImage = createThumbImage(ImageIO.read(posterFile))
                    ImageIO.write(thumbImage, imageType, FileOutputStream(boxArtFile))
                }
            }
            else if (defaultBoxArtThumb != null) {
                boxArtFile = defaultBoxArtThumb
            }
        }
        else {
            if (posterFile.exists()) {
                boxArtFile = posterFile
            }
            else if (defaultBoxArt != null) {
                boxArtFile = defaultBoxArt
            }
        }

        if (boxArtFile != null) {
            val fis = FileInputStream(boxArtFile)

            try {
                return IOUtils.toByteArray(fis)
            }
            catch (e: Exception) {
            }
            finally {
                fis.close()
            }
        }

        return ByteArray(0)
    }

    private fun buildShowSpecification(showTypeName: String, params: Map<String, Any?>?): Specification<ShowEntity>? {
        if (StringUtils.isBlank(showTypeName)) {
            return null
        }

        var spec: Specification<ShowEntity> = DataSpecification(AtomicDataFilter("showType", eqOperator, showTypeName))

        if (params != null && params.isNotEmpty()) {
            val paramNames: Set<String> = params.keys

            for (paramName in paramNames) {
                when (paramName) {
                    "title" -> {
                        val titleValue: String? = params[paramName] as String

                        if (StringUtils.isNotEmpty(titleValue)) {
                            spec = Specification.where(spec).and(DataSpecification(StringDataFilter("title", likeOperator, titleValue)))
                        }
                    }

                    "genres" -> {
                        val genreValues: Set<String>? = params.get(paramName) as Set<String>

                        if (genreValues != null && genreValues.isNotEmpty()) {
                            spec = Specification.where(spec).and(DataSpecification(CollectionDataFilter("genres", containsOperator, genreValues)))
                        }
                    }

                    "mediaFormat" -> {
                        val formatValue: String? = params[paramName] as String

                        if (StringUtils.isNotEmpty(formatValue) && !formatValue.equals("any", true)) {
                            spec = Specification.where(spec).and(DataSpecification(StringDataFilter("mediaFormat", eqOperator, formatValue)))
                        }
                    }

                    "starRating" -> {
                        val ratingValue: Int? = params[paramName] as Int

                        if (ratingValue != null) {
                            spec = Specification.where(spec).and(DataSpecification(StringDataFilter("starRating", gteOperator, ratingValue)))
                        }
                    }
                }
            }
        }

        return spec
    }
}