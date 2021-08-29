package tk.skeptick.skyeng_dictionary.ui.main

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface MainView : MvpView {

    fun showProgress()

    fun hideProgress()

    fun showError(type: ErrorType, text: String)

    fun hideError()

    fun showItems(items: List<WordItemViewModel>)

    fun hideItems()

    @OneExecution
    fun navigateToMeaning(id: Int)

}