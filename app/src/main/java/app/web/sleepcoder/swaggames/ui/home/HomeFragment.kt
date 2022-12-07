package app.web.sleepcoder.swaggames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import app.web.sleepcoder.core.ui.ListGameAdapterPaging
import app.web.sleepcoder.core.utils.showSnackbar
import app.web.sleepcoder.swaggames.R
import app.web.sleepcoder.swaggames.databinding.FragmentHomeBinding
import app.web.sleepcoder.swaggames.ui.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var viewBinding: FragmentHomeBinding

    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()

    lateinit var adapter: ListGameAdapterPaging

    var savedView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedView == null){
            viewBinding = DataBindingUtil.inflate<FragmentHomeBinding?>(
                inflater,
                R.layout.fragment_home,
                container, false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                vm = fragmentHomeViewModel
            }
            savedView = viewBinding.root
        }
        setupRecycler()
        setupObserver()

        return savedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupRecycler() {
        adapter = ListGameAdapterPaging {
            fragmentHomeViewModel.skipFirstFetchLoading.value = true
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it.slug
                )
            )
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error)
                fragmentHomeViewModel.message.value =
                    (it.refresh as LoadState.Error).error.localizedMessage
            if (fragmentHomeViewModel.skipFirstFetchLoading.value == true) return@addLoadStateListener
            viewBinding.swipeLayout.isRefreshing =
                adapter.itemCount == 0 && it.refresh is LoadState.Loading && fragmentHomeViewModel.skipFirstFetchLoading.value != true
        }

        viewBinding.rvListGame.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter(
            {
                adapter.retry()
            }, { fragmentHomeViewModel.message.value = it })
        )
        viewBinding.swipeLayout.setOnRefreshListener {
            adapter.refresh()
            fragmentHomeViewModel.skipFirstFetchLoading.value = false
        }
    }

    private fun setupObserver() {
        var fetchJob: Job? = null
        fragmentHomeViewModel.apply {
            listGames.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
                viewBinding.swipeLayout.isRefreshing = false
            }
            search.observe(viewLifecycleOwner) {
                fetchJob?.cancel()
                fetchJob = viewModelScope.launch {
                    delay(300)
                    query.postValue(it)
                    skipFirstFetchLoading.value = false
                }
            }

            fragmentHomeViewModel.message.observe(requireActivity()) {
                it.showSnackbar(requireActivity().findViewById<View?>(android.R.id.content).rootView)
            }
        }
    }

}