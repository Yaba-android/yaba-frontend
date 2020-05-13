package com.github.nasrat_v.yaba_android.Language

import com.github.nasrat_v.yaba_android.R

class StringLocaleResolver(private var languageCode: String) {

    companion object {
        const val LANGUAGE_CODE = "LanguageCode"
        const val FRENCH_LANGUAGE_CODE = "fr"
        const val ENGLISH_LANGUAGE_CODE = "en"
        const val DEFAULT_LANGUAGE_CODE = ENGLISH_LANGUAGE_CODE
        val FRENCH_LANGUAGE_STRING = mapOf(
            R.string.results to R.string.results_french,
            R.string.find_the_books_you_love to R.string.find_the_books_you_love_french,
            R.string.search_by to R.string.search_by_french,
            R.string.profile to R.string.profile_french,
            R.string.settings to R.string.settings_french,
            R.string.help to R.string.help_french,
            R.string.wish_list to R.string.wish_list_french,
            R.string.sign_out to R.string.sign_out_french,
            R.string.downloading to R.string.downloading_french,
            R.string.download to R.string.download_french,
            R.string.opening to R.string.opening_french,
            R.string.open_it to R.string.open_it_french,
            R.string.sections to R.string.sections_french,
            R.string.view_all to R.string.view_all_french,
            R.string.carousel_title_first to R.string.carousel_title_first_french,
            R.string.carousel_title_second to R.string.carousel_title_second_french,
            R.string.book_store to R.string.book_store_french,
            R.string.not_found_what_you_re_looking_for to R.string.not_found_what_you_re_looking_for_french,
            R.string.search_or_browse_categories to R.string.search_or_browse_categories_french,
            R.string.browse_sections to R.string.browse_sections_french,
            R.string.sort to R.string.sort_french,
            R.string.add_to_wish_list to R.string.add_to_wish_list_french,
            R.string.try_the_sample to R.string.try_the_sample_french,
            R.string.buy to R.string.buy_french,
            R.string.read_more to R.string.read_more_french,
            R.string.view_all_reviews to R.string.view_all_reviews_french,
            R.string.title_first_recyclerview to R.string.title_first_recyclerview_french,
            R.string.title_second_recyclerview to R.string.title_second_recyclerview_french,
            R.string.title_small_recyclerview to R.string.title_small_recyclerview_french,
            R.string.title_popular_species_recyclerview to R.string.title_popular_species_recyclerview_french,
            R.string.title_review_overview_recyclerview to R.string.title_review_overview_recyclerview_french,
            R.string.download_tab to R.string.download_tab_french,
            R.string.groups_tab to R.string.groups_tab_french,
            R.string.allbooks_tab to R.string.allbooks_tab_french,
            R.string.review_tab to R.string.review_tab_french,
            R.string.overview_tab to R.string.overview_tab_french,
            R.string.length to R.string.length_french,
            R.string.file_size to R.string.file_size_french,
            R.string.date_publication to R.string.date_publication_french,
            R.string.genre to R.string.genre_french,
            R.string.country to R.string.country_french,
            R.string.publisher to R.string.publisher_french
        )
    }

    fun getRes(resId: Int): Int {
        return if (languageCode == FRENCH_LANGUAGE_CODE) {
            FRENCH_LANGUAGE_STRING.getValue(resId)
        } else {
            resId
        }
    }
}