package app.web.sleepcoder.swaggames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.ui.ListGameAdapterPaging
import app.web.sleepcoder.core.ui.OnItemClicked
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

    lateinit var viewBinding: FragmentHomeBinding

    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()

    lateinit var adapter: ListGameAdapterPaging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = DataBindingUtil.inflate<FragmentHomeBinding?>(
            inflater,
            R.layout.fragment_home,
            container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = fragmentHomeViewModel
        }

        setupRecycler()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun setupRecycler() {
        adapter = ListGameAdapterPaging(object : OnItemClicked<Game> {
            override fun itemClick(position: Game) {
            }
        })
        viewBinding.rvListGame.adapter = adapter
        adapter.withLoadStateFooter(
            LoadingStateAdapter({
                adapter.retry()
            }, { fragmentHomeViewModel.message.value = it })
        )
    }

    private fun setupObserver() {
        var fetchJob: Job? = null
        fragmentHomeViewModel.apply {
            listGames.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
            search.observe(viewLifecycleOwner) {
                fetchJob?.cancel()
                fetchJob = viewModelScope.launch {
                    delay(300)
                    query.postValue(it)
                }
            }
            message.observe(requireActivity()) {
                it.showSnackbar(viewBinding.root)
            }

        }
    }

}