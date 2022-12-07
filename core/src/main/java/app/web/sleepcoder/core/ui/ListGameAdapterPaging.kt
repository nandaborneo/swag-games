package app.web.sleepcoder.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.web.sleepcoder.core.databinding.ItemGameBinding
import app.web.sleepcoder.core.domain.model.Game

class ListGameAdapterPaging(
    private val onitemClick: (game:Game) -> Unit
) : PagingDataAdapter<Game, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onitemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as ViewHolder).bind(it) }
    }

    class ViewHolder(
        private val binding: ItemGameBinding,
        private val onitemClick: (game: Game)-> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            with(binding){
                this.game = game
                containerItemGame.clipToOutline = true
                containerItemGame.setOnClickListener { onitemClick(game) }

                executePendingBindings()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.gameId == newItem.gameId
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }
    }
}