package Screens.Using.FirstUse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelapp.R

class FirstUseFragment : Fragment() {

    companion object {
        fun newInstance() = FirstUseFragment()
    }

    private lateinit var viewModel: FirstUseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_use, container, false)
    }
}