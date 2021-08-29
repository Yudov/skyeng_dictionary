package tk.skeptick.skyeng_dictionary.ui.meaning

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import tk.skeptick.skyeng_dictionary.domain.DictionaryRepository
import tk.skeptick.skyeng_dictionary.domain.models.Meaning

class MeaningPresenter @AssistedInject constructor(
    private val dictionaryRepository: DictionaryRepository,
    @Assisted private val meaningId: Int
) : MvpPresenter<MeaningView>() {

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        if (!dictionaryRepository.hasCachedMeaning(meaningId)) viewState.showProgress()
        loadMeaning()
    }

    fun onReloadClick() {
        viewState.hideError()
        viewState.showProgress()
        loadMeaning()
    }

    private fun loadMeaning() {
        disposable = dictionaryRepository
            .getMeaning(meaningId)
            .subscribe(::handleLoadingSuccess, ::handleLoadingError)
    }

    private fun handleLoadingSuccess(meaning: Meaning) {
        viewState.hideProgress()
        viewState.showMeaning(
            MeaningViewModel(
                word = meaning.text,
                transcription = meaning.transcription,
                translation = meaning.translation.text,
                description = meaning.definition?.text,
                imageUrl = meaning.imageUrls.firstOrNull()?.substringAfter("//")?.let("https://"::plus)
            )
        )
    }

    private fun handleLoadingError(throwable: Throwable) {
        viewState.hideProgress()
        viewState.showError()
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

}