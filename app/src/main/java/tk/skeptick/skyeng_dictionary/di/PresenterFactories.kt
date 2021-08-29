package tk.skeptick.skyeng_dictionary.di

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import tk.skeptick.skyeng_dictionary.ui.meaning.MeaningPresenter

@AssistedFactory
interface MeaningPresenterFactory {
    fun create(@Assisted meaningId: Int): MeaningPresenter
}