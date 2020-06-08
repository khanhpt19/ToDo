package com.khanhpt.todo.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khanhpt.todo.BR
import com.khanhpt.todo.R
import com.khanhpt.todo.databinding.FragmentHomeBinding
import com.khanhpt.todo.screen.dialog.AddNewTaskDialog
import com.khanhpt.todo.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var viewDataBinding by autoCleared<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var navController: NavController
    private lateinit var taskAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.apply {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
            root.isClickable = true
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            taskAdapter = HomeAdapter(
                callBack = {
                    viewModel.removeTask(it)
                }
            )

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
            recyclerViewTasks.apply {
                adapter = taskAdapter
                addItemDecoration(dividerItemDecoration)
            }

            tasks.observe(viewLifecycleOwner, Observer { tasks ->
                taskAdapter.submitList(tasks)
            })

            weatherData.observe(viewLifecycleOwner, Observer {
                textviewWeather.text =
                    String.format(
                        getString(R.string.text_weather),
                        it.name,
                        (it.main.temp - 273.15).toString()
                    )
            })

            addNewTaskSuccess.observe(viewLifecycleOwner, Observer {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.add_new_task_success),
                    Toast.LENGTH_SHORT
                ).show()
                getTasks()
            })

            removeTaskSuccess.observe(viewLifecycleOwner, Observer {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.remove_task_success),
                    Toast.LENGTH_SHORT
                ).show()
                getTasks()
            })

            errorMessage.observe(viewLifecycleOwner, Observer {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_message),
                    Toast.LENGTH_SHORT
                ).show()
            })

            errorWeather.observe(viewLifecycleOwner, Observer {
                Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            })

            getWeather()
            getTasks()
        }

        fabAddNew.setOnClickListener {
            AddNewTaskDialog.newInstance(
                context = requireContext(),
                taskListener = { newTask ->
                    viewModel.insertTask(newTask)
                }).show()
        }
        buttonRefreshWeather.setOnClickListener {
            viewModel.getWeather()
        }
    }
}
