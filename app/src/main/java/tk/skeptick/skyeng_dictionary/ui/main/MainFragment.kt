package tk.skeptick.skyeng_dictionary.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import tk.skeptick.skyeng_dictionary.R
import tk.skeptick.skyeng_dictionary.databinding.*
import tk.skeptick.skyeng_dictionary.extensions.BottomRoundedMaterialShape
import tk.skeptick.skyeng_dictionary.extensions.addOnScrollToBottomListener
import tk.skeptick.skyeng_dictionary.extensions.color
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainFragment : MvpAppCompatFragment(R.layout.fragment_main), MainView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    private val presenter: MainPresenter by moxyPresenter { presenterProvider.get() }

    private val adapter = WordsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            searchTextInputEditText.setOnFocusChangeListener { _, isFocused -> titleTextView.isVisible = !isFocused }
            searchTextInputLayout.setEndIconOnClickListener { onSearchClick() }
            searchTextInputEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == IME_ACTION_SEARCH) onSearchClick(); true
            }

            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addOnScrollToBottomListener(linearLayoutManager, visibleThreshold = 5) {
                presenter.onListBottomReached()
            }
        }
    }

    private fun onSearchClick() {
        presenter.onSearchClick(binding.searchTextInputEditText.text?.toString() ?: "")
    }

    override fun showProgress() {
        binding.progressBar.isVisible = true
        binding.searchInputsLayout.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
        }
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    override fun showError(type: ErrorType, text: String) {
        binding.errorIcon.setImageResource(type.imageRes)
        binding.errorText.text = text
        binding.errorLayout.isVisible = true
        binding.searchInputsLayout.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
        }
    }

    override fun hideError() {
        binding.errorLayout.isVisible = false
    }

    override fun showItems(items: List<WordItemViewModel>) {
        adapter.submitList(items)
        binding.recyclerView.isVisible = true
        binding.searchInputsLayout.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags = SCROLL_FLAG_ENTER_ALWAYS or SCROLL_FLAG_SNAP or SCROLL_FLAG_SCROLL
        }
    }

    override fun hideItems() {
        binding.recyclerView.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class WordItemsDiffUtil : DiffUtil.ItemCallback<WordItemViewModel>() {
        override fun areItemsTheSame(oldItem: WordItemViewModel, newItem: WordItemViewModel) = oldItem == newItem
        override fun areContentsTheSame(oldItem: WordItemViewModel, newItem: WordItemViewModel) = oldItem == newItem
    }

    class WordItemViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: WordItemViewModel.Word) {
            binding.wordTextView.text = item.text
            binding.transcriptionTextView.isVisible = !item.transcription.isNullOrBlank()
            binding.transcriptionTextView.text = "[${item.transcription}]"
        }
    }

    class MeaningItemViewHolder(private val binding: ItemMeaningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordItemViewModel.Meaning) {
            binding.root.setOnClickListener {  }
            binding.wordTextView.text = item.translation
            binding.partOfSpeechColorView.setBackgroundColor(item.partOfSpeech.color)
            binding.root.shapeAppearanceModel = when (item.isLast) {
                true -> BottomRoundedMaterialShape(binding.root.context)
                false -> ShapeAppearanceModel()
            }
        }
    }

    class ErrorItemViewHolder(private val binding: ItemErrorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onRetryClick: () -> Unit) {
            binding.retryButton.setOnClickListener { onRetryClick() }
        }
    }

    class ProgressItemViewHolder(binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root)

    inner class WordsAdapter : ListAdapter<WordItemViewModel, RecyclerView.ViewHolder>(WordItemsDiffUtil()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                ViewType.Word -> WordItemViewHolder(ItemWordBinding.inflate(layoutInflater, parent, false))
                ViewType.Meaning -> MeaningItemViewHolder(ItemMeaningBinding.inflate(layoutInflater, parent, false))
                ViewType.Error -> ErrorItemViewHolder(ItemErrorBinding.inflate(layoutInflater, parent, false))
                ViewType.Progress -> ProgressItemViewHolder(ItemProgressBinding.inflate(layoutInflater, parent, false))
                else -> throw IllegalStateException()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (val item = currentList[position]) {
                is WordItemViewModel.Word -> (holder as WordItemViewHolder).bind(item)
                is WordItemViewModel.Meaning -> (holder as MeaningItemViewHolder).bind(item)
                is WordItemViewModel.Error -> (holder as ErrorItemViewHolder).bind(presenter::onListBottomReached)
                is WordItemViewModel.Progress -> return
            }
        }

        override fun getItemViewType(position: Int): Int = when (currentList[position]) {
            is WordItemViewModel.Word -> ViewType.Word
            is WordItemViewModel.Meaning -> ViewType.Meaning
            is WordItemViewModel.Progress -> ViewType.Progress
            is WordItemViewModel.Error -> ViewType.Error
        }

    }

    object ViewType {
        const val Word = 0
        const val Meaning = 1
        const val Progress = 2
        const val Error = 3
    }

}