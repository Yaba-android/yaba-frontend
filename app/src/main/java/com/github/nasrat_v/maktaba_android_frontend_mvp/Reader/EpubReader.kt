package com.github.nasrat_v.maktaba_android_frontend_mvp.Reader

import com.folioreader.FolioReader

class EpubReader {

    fun launchReader() {
        val folioReader = FolioReader.get()
        folioReader.openBook("/storage/emulated/0/Download/pg58892-images.epub")
    }
}