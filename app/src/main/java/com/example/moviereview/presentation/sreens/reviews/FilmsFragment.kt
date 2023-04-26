package com.example.moviereview.presentation.sreens.reviews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereview.R
import com.example.moviereview.data.api.ApiService
import com.example.moviereview.databinding.FragmentFilmsBinding
import com.example.moviereview.domain.reviews.Reviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FilmsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilmsAdapter
    private  var _binding: FragmentFilmsBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
        /*return inflater.inflate(R.layout.fragment_films, container, false)*/
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listReview= arrayListOf<com.example.moviereview.domain.reviews.Result>()
        val retrofitInfo = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val infoFilmsApi = retrofitInfo.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val infoReviews = infoFilmsApi.getReviews()
            withContext(Dispatchers.Main) {

                for (review in infoReviews.results){
                    listReview.add(review)
                }

                recyclerView=view.findViewById(R.id.rv_films)
                recyclerView.layoutManager=LinearLayoutManager(requireContext())
                adapter= FilmsAdapter(listReview)
                recyclerView.adapter=adapter

            }
        }
    }





}