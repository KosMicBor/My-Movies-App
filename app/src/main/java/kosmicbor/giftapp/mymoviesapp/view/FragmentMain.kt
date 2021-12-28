package kosmicbor.giftapp.mymoviesapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMainBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.domain.MovieDTO
import kosmicbor.giftapp.mymoviesapp.domain.viewHide
import kosmicbor.giftapp.mymoviesapp.domain.viewShow
import kosmicbor.giftapp.mymoviesapp.viewmodel.MainViewModel

class FragmentMain : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = FragmentMain()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val mainAdapter = MainRVAdapter()
    private val binding: FragmentMainBinding by viewBinding (FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getMoviesList()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
            .apply { setDrawable(resources.getDrawable(R.drawable.item_divider, activity?.theme)) }

        recyclerView = binding.recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mainAdapter
            addItemDecoration(dividerItemDecoration)
        }

        viewModel.getMoviesListLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    mainAdapter.submitList(it.value as HashMap<String, List<MovieDTO>>)
                    binding.apply {
                        retryBtn.viewHide()
                        progressbar.viewHide()
                    }
                }
                is Error -> {
                    Toast.makeText(view.context, it.error.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                    binding.apply {
                        progressbar.viewHide()
                        retryBtn.viewShow()
                        retryBtn.setOnClickListener {
                            viewModel.getMoviesList()
                            binding.apply {
                                progressbar.viewShow()
                                retryBtn.viewHide()
                                progressbar.viewShow()
                            }
                        }
                    }
                }
                is LoadingState -> {
                    binding.apply {
                        progressbar.viewShow()
                        retryBtn.viewHide()
                    }
                }
            }
        }
    }
}