package app.web.sleepcoder.swaggames.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding

    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()

    private var adapter = ListGameAdapterPaging {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                it.slug
            )
        )
    }

    override fun onPause() {
        super.onPause()
        fragmentHomeViewModel.skipFetching = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = DataBindingUtil.inflate<FragmentHomeBinding?>(
            inflater,
            R.layout.fragment_home,
            container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = fragmentHomeViewModel
        }
        return _viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity == null) return

        setupRecycler()
        setupObserver()
    }

    private fun setupRecycler() {

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error)
                fragmentHomeViewModel.message.value =
                    (it.refresh as LoadState.Error).error.localizedMessage
            viewBinding?.swipeLayout?.isRefreshing =
                adapter.itemCount == 0 && it.refresh is LoadState.Loading
        }

        viewBinding?.rvListGame?.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter(
            {
                adapter.retry()
            }, { fragmentHomeViewModel.message.value = it })
        )
        viewBinding?.swipeLayout?.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun setupObserver() {
        var fetchJob: Job? = null
        fragmentHomeViewModel.apply {
            listGames.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
                viewBinding?.swipeLayout?.isRefreshing = false
            }
            search.observe(viewLifecycleOwner) {
                if (skipFetching){
                    skipFetching = false
                    return@observe
                }
                fetchJob?.cancel()
                fetchJob = viewModelScope.launch {
                    delay(300)
                    query.postValue(it)
                }
            }

            fragmentHomeViewModel.message.observe(requireActivity()) {
                it.showSnackbar(requireActivity().findViewById<View?>(android.R.id.content).rootView)
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding?.rvListGame?.adapter = null
        _viewBinding = null
        super.onDestroyView()
    }
}