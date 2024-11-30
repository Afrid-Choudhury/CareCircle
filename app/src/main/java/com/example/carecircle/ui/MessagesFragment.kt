//MessagesFragment.kt - instantiates SmsAdapter, navigated to by DashboardFragment - Esther

package com.example.carecircle.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import com.example.carecircle.R
import com.example.carecircle.adapter.SmsAdapter
import com.example.carecircle.data.MockData

class MessagesFragment : Fragment() {

    private val args: MessagesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("ViewHierarchy", "Root View: ${requireActivity().findViewById<View>(android.R.id.content)}")

        Log.e("MessagesFragment", "Fragment Loaded. isParent=${args.isParent}") // get a log that this step is reached successfully

        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        val smsRecyclerView = view.findViewById<RecyclerView>(R.id.smsRecyclerView)
        smsRecyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = SmsAdapter(MockData.messages, args.isParent)
        smsRecyclerView.adapter = adapter

        return view
    }
}
