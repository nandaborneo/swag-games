package app.web.sleepcoder.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import app.web.sleepcoder.core.ui.ListGameAdapterPaging
import app.web.sleepcoder.favorite.databinding.FragmentFavoriteBinding
import app.web.sleepcoder.swaggames.di.FavoritesModuleDependencies
import app.web.sleepcoder.swaggames.ui.LoadingStateAdapter
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var fragmentFavoriteViewModel: FragmentFavoriteViewModel

    lateinit var viewBinding: FragmentFavoriteBinding

    lateinit var adapter: ListGameAdapterPaging

    var savedView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerFavoriteComponent.builder().context(requireActivity().applicationContext)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext, FavoritesModuleDependencies::class.java
                )
            ).build().inject(this)
        fragmentFavoriteViewModel =
            ViewModelProvider(this, factory)[FragmentFavoriteViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (savedView == null) {
            viewBinding = DataBindingUtil.inflate<FragmentFavoriteBinding?>(
                inflater, R.layout.fragment_favorite, container, false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
            }
            savedView = viewBinding.root
        }
        setupRecycler()
        setupObserver()

        return viewBinding.root
    }


    private fun setupRecycler() {
        adapter = ListGameAdapterPaging {
            findNavController().navigate(
                NavDeepLinkRequest.Builder.fromUri("swag-games://detailFragment/${it.slug}".toUri())
                    .build()
            )
        }

        viewBinding.rvListGame.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter({
                adapter.retry()
            }, { Log.e(FavoriteFragment::class.simpleName, it) })
        )
    }

    private fun setupObserver() {
        fragmentFavoriteViewModel.apply {
            listGames.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

}