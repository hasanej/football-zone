package id.hasaneljabir.footballzone.activity.playerDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.entity.player.Player
import id.hasaneljabir.footballzone.entity.repository.player.PlayerRepositoryImplementation
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.player_layout_detail.*

class PlayerDetailActivity : AppCompatActivity(), PayerDetailContract.View {

    lateinit var player: Player
    lateinit var mPresenter: PayerDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        setSupportActionBar(toolbar)
        player = intent.getParcelableExtra("player")
        supportActionBar?.title = player.strPlayer
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = PlayerRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PlayerDetailPresenter(this, request, scheduler)
        mPresenter.getPlayerData(player.idPlayer)
    }

    override fun displayPlayerDetail(player: Player) {
        initView(player)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun initView(player: Player) {

        loadBanner(player)
        Glide.with(applicationContext)
            .load(player.strCutout)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
            .into(imgPlayer)


        playerName.text = player.strPlayer
        tvPosition.text = player.strPosition
        tvDate.text = player.dateBorn
        playerOverview.text = player.strDescriptionEN
    }

    private fun loadBanner(player: Player) {
        if (!player.strFanart1.equals(null)) {
            Glide.with(applicationContext)
                .load(player.strFanart1)
                .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
                .into(imageBannerPlayer)
        } else {
            Glide.with(applicationContext)
                .load(player.strThumb)
                .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
                .into(imageBannerPlayer)
        }
    }
}
