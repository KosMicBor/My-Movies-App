package kosmicbor.giftapp.mymoviesapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMainBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.viewmodel.MainViewModel

class FragmentMain : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = FragmentMain()
    }



    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val mainAdapter = MainRVAdapter()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getMoviesList()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
            .apply { setDrawable(resources.getDrawable(R.drawable.item_divider, activity?.theme)) }

        recyclerView = binding.recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mainAdapter
            addItemDecoration(dividerItemDecoration)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMoviesListLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    mainAdapter.submitList(it.value as HashMap<String, List<Movie>>)
                    binding.apply {
                        retryBtn.visibility = View.GONE
                        progressbar.visibility = View.GONE
                    }
                }
                is Error -> {
                    Toast.makeText(view.context, it.error.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                    binding.progressbar.visibility = View.GONE
                    binding.retryBtn.visibility = View.VISIBLE
                    binding.retryBtn.setOnClickListener {
                        viewModel.getMoviesList()
                        binding.apply {
                            progressbar.visibility = View.VISIBLE
                            retryBtn.visibility = View.GONE
                            progressbar.visibility = View.VISIBLE
                        }

                    }
                }
                is LoadingState -> {
                    binding.apply {
                        progressbar.visibility = View.VISIBLE
                        retryBtn.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}