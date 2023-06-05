package Screens.Using.ThirdUse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelapp.R

class ThirdUseFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdUseFragment()
    }

    private lateinit var viewModel: ThirdUseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third_use, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ThirdUseViewModel::class.java)
        // TODO: Use the ViewModel
    }

}