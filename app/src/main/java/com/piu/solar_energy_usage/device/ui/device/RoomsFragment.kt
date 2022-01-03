package com.piu.solar_energy_usage.device.ui.device

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.ui.adapter.StatisticsRoomAdapter

class RoomsFragment : Fragment() {

    private lateinit var roomsView: View
    private lateinit var roomsContext: Context

    private lateinit var roomAdapter: StatisticsRoomAdapter
    private lateinit var roomsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        roomsView = inflater.inflate(R.layout.fragment_rooms, container, false)
        roomsContext = roomsView.context

        return roomsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRecyclerViewAdapter()
        insertRooms()
    }

    private fun addRecyclerViewAdapter() {
        roomsRecyclerView = roomsView.findViewById(R.id.statisticsRoomsRecyclerView)
        roomAdapter = StatisticsRoomAdapter()
        roomsRecyclerView.apply {
            layoutManager = GridLayoutManager(
                roomsContext,
                2,
                GridLayoutManager.VERTICAL,
                false)
            adapter = roomAdapter
        }
    }

    private fun insertRooms() {
        roomAdapter.setDataSource(DeviceDataSource.rooms!!)
    }


}