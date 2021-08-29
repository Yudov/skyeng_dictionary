package tk.skeptick.skyeng_dictionary.ui.main

import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import tk.skeptick.skyeng_dictionary.domain.DictionaryRepository
import tk.skeptick.skyeng_dictionary.domain.models.Word
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : MvpPresenter<MainView>() {

    private var searchDisposable: Disposable? = null
    private var searchText: String = ""
    private var canLoadMore: Boolean = false
    private var loadingOffset: Int = 0
    private var items: List<WordItemViewModel> = emptyList()

    fun onSearchClick(text: String) {
        if (text.isBlank()) return
        searchDisposable?.dispose()
        viewState.hideError()
        viewState.hideItems()
        viewState.showProgress()

        searchDisposable = dictionaryRepository
            .search(text, 0, PAGE_SIZE)
            .subscribe({ handleLoadingSuccess(text, it) }, ::handleLoadingError)
    }

    fun onListBottomReached() {
        if (searchDisposable?.isDisposed == false || !canLoadMore) return
        loadMore()
    }

    fun onReloadClicked() {
        if (searchDisposable?.isDisposed == false) return
        loadMore()
    }

    fun onMeaningClick(item: WordItemViewModel.Meaning) {
        viewState.navigateToMeaning(item.id)
    }

    private fun loadMore() {
        viewState.showItems(items + WordItemViewModel.Progress)
        searchDisposable = dictionaryRepository
            .search(searchText, loadingOffset / PAGE_SIZE, PAGE_SIZE)
            .subscribe(::handleAddLoadingSuccess, ::handleAddLoadingError)
    }

    private fun handleLoadingSuccess(text: String, words: List<Word>) {
        searchText = text
        loadingOffset = words.size
        canLoadMore = words.size == PAGE_SIZE
        items = words.flatMap(mapWordToItems)
        viewState.hideProgress()
        viewState.showItems(items)
    }

    private fun handleLoadingError(throwable: Throwable) {
        val isNotFound = throwable is NoSuchElementException
        viewState.hideProgress()
        viewState.showError(
            type = if (isNotFound) ErrorType.NotFound else ErrorType.Error,
            text = if (isNotFound) "Увы, ничего не найдено" else "Ошибка поиска"
        )
    }

    private fun handleAddLoadingSuccess(words: List<Word>) {
        loadingOffset += words.size
        canLoadMore = words.size == PAGE_SIZE
        items = items + words.flatMap(mapWordToItems)
        viewState.showItems(items)
    }

    private fun handleAddLoadingError(throwable: Throwable) {
        canLoadMore = false
        viewState.showItems(items + WordItemViewModel.Error)
    }

    override fun onDestroy() {
        searchDisposable?.dispose()
    }

    companion object {

        private const val PAGE_SIZE = 20

        private val mapWordToItems: Word.() -> List<WordItemViewModel> = mapper@ {
            val result = mutableListOf<WordItemViewModel>()
            val transcription = meanings.firstNotNullOfOrNull(Word.Meaning::transcription)
            result.add(WordItemViewModel.Word(text, transcription))
            meanings.forEachIndexed { index, value ->
                val isLast = index == meanings.lastIndex
                result.add(WordItemViewModel.Meaning(value.id, value.partOfSpeech, value.translation.text, isLast))
            }
            return@mapper result
        }

    }

}