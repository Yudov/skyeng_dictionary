package tk.skeptick.skyeng_dictionary.ui.meaning

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MeaningView : MvpView {

    fun showProgress()

    fun hideProgress()

    fun showError()

    fun hideError()

    fun showMeaning(meaning: MeaningViewModel)

}