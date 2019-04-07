package com.github.nasrat_v.maktaba_android_frontend_mvp.Language

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class StringLocaleResolver(private var languageCode: String) {

    companion object {
        const val LANGUAGE_CODE = "LanguageCode"
        const val ARABIC_LANGUAGE_CODE = "ar"
        const val ENGLISH_LANGUAGE_CODE = "en"
        const val DEFAULT_LANGUAGE_CODE = ENGLISH_LANGUAGE_CODE
        val ARABIC_STRINGS = mapOf(
            R.string.sections to R.string.sections_arabic,
            R.string.view_all to R.string.view_all_arabic,
            R.string.carousel_title_first to R.string.carousel_title_first_arabic,
            R.string.carousel_title_second to R.string.carousel_title_second_arabic,
            R.string.book_store to R.string.book_store_arabic,
            R.string.not_found_what_you_re_looking_for to R.string.not_found_what_you_re_looking_for_arabic,
            R.string.search_or_browse_categories to R.string.search_or_browse_categories_arabic,
            R.string.browse_sections to R.string.browse_sections_arabic,
            R.string.sort to R.string.sort_arabic,
            R.string.add_to_wish_list to R.string.add_to_wish_list_arabic,
            R.string.try_the_sample to R.string.try_the_sample_arabic,
            R.string.buy to R.string.buy_arabic,
            R.string.read_more to R.string.read_more_arabic,
            R.string.view_all_reviews to R.string.view_all_reviews_arabic,
            R.string.title_first_recyclerview to R.string.title_first_recyclerview_arabic,
            R.string.title_second_recyclerview to R.string.title_second_recyclerview_arabic,
            R.string.title_small_recyclerview to R.string.title_small_recyclerview_arabic,
            R.string.title_popular_species_recyclerview to R.string.title_popular_species_recyclerview_arabic,
            R.string.title_review_overview_recyclerview to R.string.title_review_overview_recyclerview_arabic,
            R.string.download_tab to R.string.download_tab_arabic,
            R.string.groups_tab to R.string.groups_tab_arabic,
            R.string.allbooks_tab to R.string.allbooks_tab_arabic,
            R.string.review_tab to R.string.review_tab_arabic,
            R.string.overview_tab to R.string.overview_tab_arabic,
            R.string.length to R.string.length_arabic,
            R.string.file_size to R.string.file_size_arabic,
            R.string.date_publication to R.string.date_publication_arabic,
            R.string.genre to R.string.genre_arabic,
            R.string.country to R.string.country_arabic,
            R.string.publisher to R.string.publisher_arabic
        )
    }

    fun getRes(resId: Int): Int {
        return if (languageCode == ARABIC_LANGUAGE_CODE) {
            ARABIC_STRINGS.getValue(resId)
        } else {
            resId
        }
    }
}