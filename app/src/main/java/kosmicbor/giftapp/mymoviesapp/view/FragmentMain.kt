package kosmicbor.giftapp.mymoviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.giftapp.mymoviesapp.MainActivity
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMainBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.viewmodel.MainViewModel

class FragmentMain : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = FragmentMain()
    }

    private var moviesList: MutableList<Movie> = mutableListOf()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private  val mainAdapter = MainRVAdapter(this)
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getMoviesList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mainAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMoviesListLiveData().observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
            binding.apply {
                retryBtn.visibility = View.GONE
                progressbar.visibility = View.GONE
            }
        }

        viewModel.getErrorListLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            binding.progressbar.visibility = View.GONE
            binding.retryBtn.visibility = View.VISIBLE
            binding.retryBtn.setOnClickListener {
                binding.progressbar.visibility = View.VISIBLE
                binding.retryBtn.visibility = View.GONE
                viewModel.getMoviesList()
            }

        }

        viewModel.getLoadingListLiveData().observe(viewLifecycleOwner) {
            binding.progressbar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}