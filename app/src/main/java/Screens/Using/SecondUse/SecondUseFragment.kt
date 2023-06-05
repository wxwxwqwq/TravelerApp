package Screens.Using.SecondUse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelapp.R

class SecondUseFragment : Fragment() {

    companion object {
        fun newInstance() = SecondUseFragment()
    }

    private lateinit var viewModel: SecondUseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_use, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondUseViewModel::class.java)
        // TODO: Use the ViewModel
    }

}