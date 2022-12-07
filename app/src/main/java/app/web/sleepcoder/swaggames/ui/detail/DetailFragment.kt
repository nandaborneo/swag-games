package app.web.sleepcoder.swaggames.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.web.sleepcoder.core.data.Resource
import app.web.sleepcoder.core.utils.showSnackbar
import app.web.sleepcoder.swaggames.R
import app.web.sleepcoder.swaggames.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var viewBinding: FragmentDetailBinding
    private val fragmentDetailViewModel: DetailFragmentViewModel by viewModels()
    val args: DetailFragmentArgs by navArgs()
    var savedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedView == null) {
            viewBinding = DataBindingUtil.inflate<FragmentDetailBinding?>(
                inflater,
                R.layout.fragment_detail,
                container,
                false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                fragmentDetailViewModel.slug.value = args.slug
                vm = fragmentDetailViewModel
            }
            savedView = viewBinding.root
            setupObserver()
        }
        return savedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObserver() {

        fragmentDetailViewModel.apply {
            detailGameResource.observe(viewLifecycleOwner) {
                viewBinding.swipeLayout.isRefreshing = false
                when (it) {
                    is Resource.Loading -> {
                        viewBinding.swipeLayout.isRefreshing = true
                        if (it.data != null) {
                            fragmentDetailViewModel.game.value = it.data
                        }
                    }
                    is Resource.Success -> {
                        fragmentDetailViewModel.game.value = it.data
                    }
                    is Resource.Error -> {
                        fragmentDetailViewModel.message.value = it.message
                    }
                }
            }
            message.observe(viewLifecycleOwner) {
                if (it.isNullOrBlank()) return@observe
                savedView?.let { it1 -> it.showSnackbar(it1) }
            }

            viewBinding.apply {
                swipeLayout.setOnRefreshListener {
                    fragmentDetailViewModel.slug.value = "${fragmentDetailViewModel.slug.value}"
                }
                ibBack.setOnClickListener {
                    findNavController().popBackStack()
                }
                fabFavorite.setOnClickListener {
                    fragmentDetailViewModel.setFavorite()
                }
            }

        }
    }
}