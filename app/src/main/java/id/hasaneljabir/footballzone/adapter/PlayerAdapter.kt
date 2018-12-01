package id.hasaneljabir.footballzone.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.entity.player.Player
import id.hasaneljabir.footballzone.activity.playerDetail.PlayerDetailActivity

import kotlinx.android.synthetic.main.item_player.view.*
import org.jetbrains.anko.startActivity

class PlayerAdapter(val listPlayer: List<Player>, val context: Context?) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))
    }

    override fun getItemCount() = listPlayer.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(listPlayer[position])
    }


    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(player: Player) {
            itemView.tvPlayer.text = player.strPlayer
            Glide.with(itemView.context)
                .load(player.strCutout)
                .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
                .apply(RequestOptions().override(120, 140))
                .into(itemView.imgPlayer)

            itemView.setOnClickListener {
                itemView.context.startActivity<PlayerDetailActivity>("player" to player)
            }
        }
    }
}