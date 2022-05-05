package com.example.eventsapp.ui.home

import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.beust.klaxon.Klaxon
import com.example.eventsapp.EventFilterActivity
import com.example.eventsapp.R
import com.example.eventsapp.databinding.FragmentHomeBinding
import okhttp3.*
import java.io.IOException


data class Event(val id: Int, val name: String, val type: String, val pricing: String);

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var homeViewModel: HomeViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel!!.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_filter).setOnClickListener {
            val intent = Intent(context, EventFilterActivity::class.java);
            startActivity(intent);
        }

        view.findViewById<Button>(R.id.button_refresh).setOnClickListener {
            val eventsUrl = "https://my-json-server.typicode.com/alexandra-luca/events-app/events"

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
    }

    fun editText(view: View, events: List<Event>) {
        requireActivity()!!.runOnUiThread(object : Runnable {
            override fun run() {

                val eventsTextView = view.findViewById<TextView>(R.id.eventsTextView)
                eventsTextView.text = events.joinToString(", ") { event -> event.name }

            }
        })
    }
}