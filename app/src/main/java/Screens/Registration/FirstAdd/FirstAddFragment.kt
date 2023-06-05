package Screens.Registration.FirstAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ViewModels.FirstAdd.FirstAddViewModel
import com.example.travelapp.FirstAddFragment_context
import com.example.travelapp.FirstAddFragment_view
import com.example.travelapp.R


class FirstAddFragment : Fragment() {

    lateinit var viewModel: FirstAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        FirstAddFragment_context = this
        FirstAddFragment_view = view

        viewModel = ViewModelProvider(this).get(FirstAddViewModel::class.java)
    }
}