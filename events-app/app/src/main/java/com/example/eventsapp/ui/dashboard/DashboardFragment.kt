package com.example.eventsapp.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.eventsapp.EventFilterActivity
import com.example.eventsapp.R
import com.example.eventsapp.databinding.FragmentDashboardBinding
import com.example.eventsapp.ui.home.CustomAdapter
import com.example.eventsapp.ui.home.Event
import okhttp3.*
import java.io.IOException

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var ctx: Context? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        linearLayoutManager = LinearLayoutManager(getContext())

        ctx = getContext();

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventsUrl = "https://my-json-server.typicode.com/alexandra-luca/events-app/favorites2"

        val client = OkHttpClient()
        val request = Request.Builder().url(eventsUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body()?.string()!!;
                val events = Klaxon().parseArray<Event>(responseString)!!
                editText(view, events)
            }
        })
    }

    fun editText(view: View, events: List<Event>) {
        requireActivity()!!.runOnUiThread(object : Runnable {
            override fun run() {

//                val eventsTextView = view.findViewById<TextView>(R.id.eventsTextView)
//                eventsTextView.text = events.joinToString(", ") { event -> event.name }

                val recyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerview)
                recyclerView.layoutManager = linearLayoutManager

                val adapter = CustomAdapter(events, ctx)
                recyclerView.adapter = adapter;
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}