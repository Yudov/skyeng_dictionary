package tk.skeptick.skyeng_dictionary.ui.meaning

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import tk.skeptick.skyeng_dictionary.R
import tk.skeptick.skyeng_dictionary.databinding.FragmentMeaningBinding
import tk.skeptick.skyeng_dictionary.di.MeaningPresenterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MeaningFragment : MvpAppCompatFragment(R.layout.fragment_meaning), MeaningView {

    private var _binding: FragmentMeaningBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenterFactory: MeaningPresenterFactory

    private val presenter: MeaningPresenter by moxyPresenter {
        presenterFactory.create(requireArguments().getInt(MEANING_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMeaningBinding.bind(view)
        binding.errorButton.setOnClickListener { presenter.onReloadClick() }
    }

    override fun showProgress() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    override fun showError() {
        binding.errorGroup.isVisible = true
    }

    override fun hideError() {
        binding.errorGroup.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    override fun showMeaning(meaning: MeaningViewModel) {
        with(binding) {
            meaningGroup.isVisible = true
            wordTextView.text = meaning.word
            transcriptionTextView.text = "[${meaning.transcription}]"
            translationTextView.text = meaning.translation
            descriptionTextView.text = meaning.description
            imageView.load(meaning.imageUrl)
        }
    }

    companion object {

        private const val MEANING_ID = "meaning_id"

        fun newInstance(id: Int) = MeaningFragment().apply {
            arguments = bundleOf(MEANING_ID to id)
        }

    }

}