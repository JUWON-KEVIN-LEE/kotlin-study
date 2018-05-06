package com.kevin.navmedia.ui.main.fragment


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevin.navmedia.R
import com.kevin.navmedia.data.entity.Video
import com.kevin.navmedia.databinding.FragmentTop100Binding
import com.kevin.navmedia.ui.main.TopViewModel
import com.kevin.navmedia.ui.main.adapter.TopRecyclerViewAdapter
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4
 */
class Top100Fragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private lateinit var binding : FragmentTop100Binding

    private lateinit var viewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                                    .get(TopViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_top100, container, false)

        binding = DataBindingUtil.bind(rootView)

        init()

        return binding.root
    }

    private fun init() {
        binding.recyclerView.adapter = TopRecyclerViewAdapter(listOf(
                Video(id = "T01",
                        thumbnailUrl = "https://i.ytimg.com/vi/aqz-KE-bpKQ/maxresdefault.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        time = 10000L,
                        title = "Big Buck Bunny",
                        playCount = 183421,
                        loveCount = 496,
                        order = 0,
                        rank = 1,
                        rankVar = 0,
                        program = "테스트"
                ),
                Video(id = "T02",
                        thumbnailUrl = "http://payload190.cargocollective.com/1/12/404388/6105939/ed_title.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                        time = 10000L,
                        title = "Elephant Dream",
                        playCount = 12345,
                        loveCount = 5192,
                        order = 0,
                        rank = 2,
                        rankVar = 0,
                        program = "테스트"
                ),
                Video(id = "T01",
                        thumbnailUrl = "https://i.ytimg.com/vi/aqz-KE-bpKQ/maxresdefault.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        time = 10000L,
                        title = "Big Buck Bunny",
                        playCount = 183421,
                        loveCount = 496,
                        order = 0,
                        rank = 3,
                        rankVar = 0,
                        program = "테스트"
                ),
                Video(id = "T02",
                        thumbnailUrl = "http://payload190.cargocollective.com/1/12/404388/6105939/ed_title.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                        time = 10000L,
                        title = "Elephant Dream",
                        playCount = 12345,
                        loveCount = 5192,
                        order = 0,
                        rank = 4,
                        rankVar = 3,
                        program = "테스트"
                ),
                Video(id = "T01",
                        thumbnailUrl = "https://i.ytimg.com/vi/aqz-KE-bpKQ/maxresdefault.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        time = 10000L,
                        title = "Big Buck Bunny",
                        playCount = 183421,
                        loveCount = 496,
                        order = 0,
                        rank = 5,
                        rankVar = -2,
                        program = "테스트"
                ),
                Video(id = "T02",
                        thumbnailUrl = "http://payload190.cargocollective.com/1/12/404388/6105939/ed_title.jpg",
                        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                        time = 10000L,
                        title = "Elephant Dream",
                        playCount = 12345,
                        loveCount = 5192,
                        order = 0,
                        rank = 6,
                        rankVar = 0,
                        program = "테스트"
                )
        ))
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
    }
}
