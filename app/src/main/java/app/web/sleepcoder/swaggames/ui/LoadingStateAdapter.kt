package app.web.sleepcoder.swaggames.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import app.web.sleepcoder.swaggames.R
import app.web.sleepcoder.swaggames.databinding.ItemLoadStateBinding

class LoadingStateAdapter(
    private val retry: () -> Unit,
    private val onError: (message: String) -> Unit
) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry, onError)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadingStateViewHolder(
        private val binding: ItemLoadStateBinding,
        retry: () -> Unit,
        private val onError: (message: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnReload.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.tvMessage.text = ""
            if (loadState is LoadState.Error) {
                onError(loadState.error.localizedMessage ?: "")
                binding.tvMessage.text = buildString {
                    append("Oops there is an error.\n message: }")
                    append(loadState.error.localizedMessage ?: "")
                }
            }
            if (loadState.endOfPaginationReached) {
                binding.tvMessage.text = binding.tvMessage.context.getString(R.string.end_of_page)
            }
            binding.linearLoading.isVisible = loadState is LoadState.Loading
            binding.llMessageArea.isVisible = loadState is LoadState.Error
        }
    }
}